package klient


import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws._
import play.api.libs.ws.ahc._

import scala.concurrent.Future

object ThomasErUteÅSykler {

  val bysykkelTjeneste = new BysykkelTjenesteStub

  def main(args: Array[String]): Unit = {

    bysykkelTjeneste.call
  }
}


class BysykkelTjenesteStub {

  import scala.concurrent.ExecutionContext.Implicits._
  implicit val system = ActorSystem()
  system.registerOnTermination {
    System.exit(0)
  }
  implicit val materializer = ActorMaterializer()
  val wsClient: StandaloneWSClient = StandaloneAhcWSClient()


  def call: Unit = {
    callIntern()
      .andThen { case _ => wsClient.close() }
      .andThen { case _ => system.terminate() }
  }

  private def callIntern(): Future[Unit] = {
    wsClient.url("http://localhost:4567/ping").get().map { response ⇒
      val statusText: String = response.statusText
      //val body: BodyReadable[String] = response.body[String]
      println(s"PING $statusText\n") // with content:\n${body}\n")
    }
  }

}