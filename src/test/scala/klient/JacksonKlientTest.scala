package klient

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import play.api.libs.ws._
import play.api.libs.ws.ahc._

import scala.concurrent.Future
import scala.util.{Failure, Success}

case class Avail(stations: String, updated_at: String, refresh_rate: Double)

object TestBysykkel {

  val bysykkelTjeneste = new BysykkelTjenesteHjelper

  def main(args: Array[String]): Unit = {
    bysykkelTjeneste.exec(printer)
  }

  def printer(avail: Avail): Unit = {
    println(s"FINAL SUCCESS: refresh_rate: ${avail.refresh_rate} updated_at: ${avail.updated_at}\n")
  }
}

class BysykkelTjenesteHjelper {

  import scala.concurrent.ExecutionContext.Implicits._

  val getAvail = getAvailability

  private def callPing: Future[Unit] = {
    wsClient.url("http://localhost:4567/ping").get().map { response ⇒

      val statusText: String = response.statusText
      println(s"PING $statusText\n") // with content:\n${body}\n")
    }
  }

  private def getAvailability[T]: Future[T] = {
    wsClient.url("http://localhost:4567/avail").get().map { response ⇒
      mapper.readValue[T](response.body, classOf[T])
    }
  }

  def exec(successFunc: Avail => Unit) = {
    getAvailability[Avail]
      .andThen { case _ => wsClient.close() }
      .andThen { case _ => system.terminate() }
      .onComplete({
        case Success(avail) => {
          successFunc(avail)
        }
        // TODO Hvordan fange exception
        //        case Failure(exception: Exception) => {
        //          println(s"FUTURE EXCEPTION\n $exception", exception.printStackTrace())
        //        }
      })
  }

  // Jackson mapper
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  // Scala REST klient system
  implicit val system = ActorSystem()
  system.registerOnTermination {
    System.exit(0)
  }
  implicit val materializer = ActorMaterializer()
  val wsClient: StandaloneWSClient = StandaloneAhcWSClient()

}


