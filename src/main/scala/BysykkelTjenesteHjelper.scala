package play_klient

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.mashape.unirest.http.Unirest

class BysykkelTjenesteHjelper(val sykkeloppsett: Sykkeloppsett) {

  val host = sykkeloppsett.host
  val port = sykkeloppsett.port

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def getAvailability: BysykkelStatus = {
    val httpResponse = Unirest.get(s"http://$host:$port/stations/availability").asString
    mapper.readValue[BysykkelStatus](httpResponse.getBody, classOf[BysykkelStatus])
  }
  def getStations: SykkelstativStatus = {
    val httpResponse = Unirest.get(s"http://$host:$port/stations").asString
    mapper.readValue[SykkelstativStatus](httpResponse.getBody, classOf[SykkelstativStatus])
  }

  def shutdown = Unirest.shutdown
}
