
import java.util.Date

import com.mashape.unirest.http.exceptions.UnirestException

case class BysykkelStatus(stations: List[Station], updated_at: Date, refresh_rate: Double)
case class Station(id: Int, availability: Availability)
case class Availability(bikes: Int, locks: Int)

case class SykkelstativStatus(stations: List[SykkelStativ], zz_time: Int)
case class SykkelStativ(id: Int, title: String, number_of_locks: Int)

object ThomasErUteAaSykler extends App {

  val sykkellås = new Sykkeloppsett("bysykkel.conf")
  val bysykkelTjeneste = new BysykkelTjenesteHjelper(sykkellås)

  lazy val sykkelstativStatus = bysykkelTjeneste.getStations

  try {
    val bysykkelStatus = bysykkelTjeneste.getAvailability

    visTabellFormat(bysykkelStatus, sykkelstativStatus)
  } catch {
    case se: WebServerException => println(s"FEIL: Noe er galt med serveren, prøv igjen senere.\n$se")
    case ce: WebClientException => println(s"FEIL: Noe er galt med oppsettet på/spørringen fra -klienten din.\n$ce")
    case ex: UnirestException => println(s"FEIL: Klarer ikke å fullføre forespøresel til Bysykkeltjeneren.\n$ex")
  } finally {
    bysykkelTjeneste.shutdown
  }

  def visTabellFormat(status: BysykkelStatus, sykkelStativer: SykkelstativStatus): Unit = {

    println(s"\n\nBysykkelstatus  --  (oppdatert ${status.updated_at}, oppdateringsintervall: ${status.refresh_rate} sekunder)\n")
    printf("%-31s %13s %8s %10s\n", "Station title", "Station-id", "Bikes", "Locks")
    println("-" * 68)

    status.stations.foreach(
      station => {
        println(f" $stativNavn%-30s ${station.id}%10d ${station.availability.bikes}%10d ${station.availability.locks}%10d")

        def stativNavn = {
          sykkelstativStatus.stations.find(stativ => stativ.id == station.id) match {
            case Some(s: SykkelStativ) => s.title
            case None => "Uten navn"
          }
        }
      }
    )
  }

  def exit(message: String) = {
    println(s"ALVORLIG FEIL: ${message}")
    System.exit(1)
  }
}

class Sykkeloppsett(private val konfigfil: String) {

  import java.io.FileInputStream
  import java.util.Properties

  import scala.collection.JavaConverters._
  import ThomasErUteAaSykler.exit

  val prop: Map[String, String] =
    try {
      val javaProp = new Properties
      javaProp.load(new FileInputStream(konfigfil))
      javaProp.asScala.toMap
    } catch {
      case ex: Exception => {
        exit(ex.getMessage)
        null
      }
    }

  val clientIdentifier: String = prop.getOrElse("client.identifier", "")
  if (clientIdentifier.trim.isEmpty) {
    exit("Client-Identifier parameter mangler i konfigurasjonsfilen.")
  }

  val host: String = prop.getOrElse("server.host", "")
  if (host.trim.isEmpty) {
    exit("Host parameter mangler i konfigurasjonsfilen.")
  }

  val port: Int = {
    if ( prop.getOrElse("server.port", "").trim.isEmpty )
      exit("Port parameter mangler i konfigurasjonsfilen")
    prop("server.port").toInt
  }
}
