
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.mashape.unirest.http.{HttpResponse}

import com.mashape.unirest.http.Unirest

class BysykkelTjenesteHjelper(val sykkeloppsett: Sykkeloppsett) {

  val host = sykkeloppsett.host
  val port = sykkeloppsett.port

  Unirest.setDefaultHeader("Client-Identifier", sykkeloppsett.clientIdentifier)

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  /** Påkrevd deserliasiseringsegenskap */
  mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)


  def getAvailability: BysykkelStatus = {
    val httpResponse = Unirest.get(s"http://$host:$port/stations/availability").asString
    validate(httpResponse)
    mapper.readValue[BysykkelStatus](httpResponse.getBody, classOf[BysykkelStatus])
  }

  def getStations: SykkelstativStatus = {
    val httpResponse = Unirest.get(s"http://$host:$port/stations").asString
    validate(httpResponse)
    mapper.readValue[SykkelstativStatus](httpResponse.getBody, classOf[SykkelstativStatus])
  }

  def validate(httpResponse: HttpResponse[String]) {
    if (httpResponse.getStatus >= 500)
      throw new WebServerException(httpResponse.getStatus, httpResponse.getStatusText, httpResponse.getBody)
    else if (httpResponse.getStatus >= 400)
      throw new WebClientException(httpResponse.getStatus, httpResponse.getStatusText, httpResponse.getBody)
    /** Redirection er ikke støttet i denne versjonen */
    else if (httpResponse.getStatus >= 300)
      throw new WebServiceRedirectionException(httpResponse.getStatus, httpResponse.getStatusText, httpResponse.getBody)
  }

  def shutdown = Unirest.shutdown
}

case class WebServerException(httpStatusCode: Int, httpStatusMessage: String, extenedMessage: String)
  extends Exception(s"Web server feil: $httpStatusCode - $httpStatusMessage\n$extenedMessage")

case class WebClientException(httpStatusCode: Int, httpStatusMessage: String, extenedMessage: String)
extends Exception(s"Klient feil: $httpStatusCode - $httpStatusMessage\n$extenedMessage")

case class WebServiceRedirectionException(httpStatusCode: Int, httpStatusMessage: String, extenedMessage: String)
  extends Exception(s"Tjenesteomdirigering: $httpStatusCode - $httpStatusMessage\n$extenedMessage")

