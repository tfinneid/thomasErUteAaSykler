package play_klient

import java.util.Date

case class BysykkelStatus(stations: List[Station], updated_at: Date, refresh_rate: Double)
case class Station(id: Int, availability: Availability)
case class Availability(bikes: Int, locks: Int)

case class SykkelstativStatus(stations: List[SykkelStativ])
case class SykkelStativ(id: Int, title: String, number_of_locks: Int)

object ThomasErUteAaSykler extends App {

  val bysykkelTjeneste = new BysykkelTjenesteHjelper("localhost", 4567)

  val sykkelstativStatus = bysykkelTjeneste.getStationsStatus
  visTabellFormat(bysykkelTjeneste.getAvailabilityCall, sykkelstativStatus)

  bysykkelTjeneste.shutdown

  def visTabellFormat(status: BysykkelStatus, sykkelStativer: SykkelstativStatus): Unit = {

    println(s"\n\nBysykkelstatus  --  (oppdatert ${status.updated_at}, oppdateringsintervall: ${status.refresh_rate} sekunder)\n")

    println(s" Station title (Station-id)        Bikes       Locks") // TODO Formatert med faste kolonnestørrelser
    println(" ----------------------------------------------------")
    status.stations.foreach(
      station => {
        println(s" $stativNavn      (${station.id})              ${station.availability.bikes}           ${station.availability.locks}")

        def stativNavn = sykkelstativStatus.stations.find(stativ => stativ.id == station.id).get.title
      }
    )
  }
}
