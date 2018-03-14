package klient

/*
import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.duration._

import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.ExecutionContext


class ThomasLærerOmBysykkel @Inject() (ws: WSClient) extends Controller {

  val pingStatus: WSRequest = ws.url("http://localhost:4567/hello")
  val response: Future[WSResponse] = pingStatus.get()

  println(s"<-- ${response.value}")

}
*/


import scala.concurrent.Future

object ScalaClient {
  import scala.concurrent.ExecutionContext.Implicits._

  def main(args: Array[String]): Unit = {
    // Create Akka system for thread and streaming management
    implicit val system = ActorSystem()
    system.registerOnTermination {
      System.exit(0)
    }
    implicit val materializer = ActorMaterializer()

    // Create the standalone WS client
    // no argument defaults to a AhcWSClientConfig created from
    // "AhcWSClientConfigFactory.forConfig(ConfigFactory.load, this.getClass.getClassLoader)"
    val wsClient = StandaloneAhcWSClient()

    call(wsClient)
      .andThen { case _ => wsClient.close() }
      .andThen { case _ => system.terminate() }
  }

  def call(wsClient: StandaloneWSClient): Future[Unit] = {
    wsClient.url("http://localhost:4567/ping").get().map { response ⇒
      val statusText: String = response.statusText
      val body = response.body[String]
      println(s"Got response $statusText with content:\n$body\n")
    }
  }
}