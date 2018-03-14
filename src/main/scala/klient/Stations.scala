package klient

import java.util.Date

case class BysykkelStatus(val stations: List[Station], val updatedTime: Date, val refreshRate: Double)

case class Station(val id: Int, val availability: Availability)

case class Availability(val bikes: Int, locks: Int)


object Bysykkel extends App {

  tabellFormat(new BysykkelTjeneste().availability())

  def tabellFormat(status: BysykkelStatus): Unit = {

    println(s"Bysykkel status               (oppdatert ${status.updatedTime}, oppdateringsintervall: ${status.refreshRate}")

    println(s" Stationid        Bikes       Locks  ")  // TODO Formatert med faste kolonnestørrelser
    status.stations.foreach(
      station => println(s"       ${station.id}                ${station.availability.bikes}        ${station.availability.locks}")
    )
  }
}

class BysykkelTjeneste() {

  def availability(): BysykkelStatus = {

    BysykkelStatus(
        List(
      Station( 255, Availability(3,  6) ),
      Station( 98,  Availability(1, 24) ),
      Station( 141, Availability(11,14) ),
      Station( 27,  Availability(18,20) )
      ),
      new Date(),
      15.0
    )
  }
}
