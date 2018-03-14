package klient

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.{JsResult, JsValue, Json}
import play.api.libs.ws._
import play.api.libs.ws.ahc._

import scala.concurrent.Future
import play.api.libs.ws.JsonBodyReadables._
import play.api.libs.ws.JsonBodyWritables._

object TestBysykkel {

  val bysykkelTjeneste = new BysykkelTjenesteStubHelper

  def main(args: Array[String]): Unit = {

    //bysykkelTjeneste.execute(bysykkelTjeneste.checkPing)
    bysykkelTjeneste.exec
  }
}


class BysykkelTjenesteStubHelper {

//  val getAvailability = callAvailability
//  val checkPing = callAvailability

  import scala.concurrent.ExecutionContext.Implicits._
  implicit val system = ActorSystem()
  system.registerOnTermination {
    System.exit(0)
  }
  implicit val materializer = ActorMaterializer()
  val wsClient: StandaloneWSClient = StandaloneAhcWSClient()


//  def execute(f: () => Future[Unit] ): Unit = {
  def exec = {
    //f()
    callAvailability
      .andThen { case _ => wsClient.close() }
      .andThen { case _ => system.terminate() }
  }

  private def callPing: Future[Unit] = {
    wsClient.url("http://localhost:4567/ping").get().map { response ⇒

      val statusText: String = response.statusText
      println(s"PING $statusText\n") // with content:\n${body}\n")
    }
  }

//  private def callAvailability: Future[JsResult[Avail]] = {
//    implicit val personReads = Json.reads[Avail]
//    wsClient.url("http://localhost:4567/avail").get().map { response ⇒
//
//
//    }
//  }
//  private def callAvailability2: Future[JsResult[Avail]] = {
  private def callAvailability: Future[Unit] = {

    implicit val personReads = Json.reads[Avail]
    wsClient.url("http://localhost:4567/avail").get().map { response ⇒
      val b = response.body[Avail]
      //(response.js \ "avail").vali

      println(s"refresh_rate: ${b.refresh_rate}\nupdated_at: ${b.updated_at}\n")
    }
  }

}

case class Avail(stations: String, updated_at: String, refresh_rate: Long)

trait URLBodyReadables {
  implicit val urlBodyReadable = BodyReadable[Avail] { response =>
    import play.shaded.ahc.org.asynchttpclient.{ Response => AHCResponse }
    val ahcResponse = response.underlying[AHCResponse]
    val s = ahcResponse.getResponseBody
    s.
      .toURL
  }
}