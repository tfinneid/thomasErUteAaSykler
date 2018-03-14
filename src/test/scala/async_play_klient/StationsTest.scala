package klient

import java.util.Date

case class BysykkelStatus2(val stations: List[Station2], val updated_at: Date, val refresh_rate: Double)

case class Station2(val id: Int, val availability: Availability2)

case class Availability2(val bikes: Int, locks: Int)


object BysykkelMain extends App {

  tabellFormat(new BysykkelTjeneste().availability())

  def tabellFormat(status: BysykkelStatus2): Unit = {

    println(s"Bysykkel status               (oppdatert ${status.updated_at}, oppdateringsintervall: ${status.refresh_rate}")

    println(s" Stationid        Bikes       Locks  ")  // TODO Formatert med faste kolonnestørrelser
    status.stations.foreach(
      station => println(s"       ${station.id}                ${station.availability.bikes}        ${station.availability.locks}")
    )
  }
}

class BysykkelTjeneste() {

  def availability(): BysykkelStatus2 = {

    BysykkelStatus2(
        List(
      Station2( 255, Availability2(3,  6) ),
      Station2( 98,  Availability2(1, 24) ),
      Station2( 141, Availability2(11,14) ),
      Station2( 27,  Availability2(18,20) )
      ),
      new Date(),
      15.0
    )
  }
}
