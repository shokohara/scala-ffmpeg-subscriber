package com.github.shokohara.subscribe.subscribe

import java.io.File

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport.defaultNodeSeqMarshaller
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, Route}

import scala.concurrent.Future
import sys.process._

/**
  * Server will be started calling `WebServerHttpApp.startServer("localhost", 8080)`
  * and it will be shutdown after pressing return.
  */
object WebServerHttpApp extends HttpApp with App {
  case class Option(port: Int, dir: String, bucket: String, segmentTime: Int)
  case class Attributes(version: String, key: String, url: String)
  case class Message(attributes: Attributes, data: String, messageId: String, publishTime: String)
  case class PubSubRequest(message: Message, subscription: String)
  def routes: Route =
    pathEndOrSingleSlash { // Listens to the top `/`
      complete("Server up and running") // Completes with some text
    } ~
      path("payload") { // Listens to paths that are exactly `/hello`
        post { // Listens only to GET requests
          status
        }
      } ~
      path("status") { // Listens to paths that are exactly `/hello`
        get { // Listens only to GET requests
          status
        }
      }

  // This will start the server until the return key is pressed
  startServer("localhost", 8080)

  var futures: List[Future[()]] = List.empty

  def ffmpeg(o: Option, psr: PubSubRequest) = {
    new File(o.dir + "/" + psr.message.attributes.key).mkdirs()
    "ffmpeg "
    futures = futures :+ Future.unit
    complete(StatusCodes.OK)
  }
  def status = {
    complete(futures.filter(_.isCompleted).length + "")
  }

  def ffmpegCommand(o: Option)(r: PubSubRequest): (String, List[String]) = {
    List("ffmpeg" , "-i", attributesUrl compose messageAttributes compose psrMessage $ r, "-acodec", "copy", "-vcodec", "copy", "-f", "segment", "-segment_time", show compose O.segmentTime o, "-segment_list", s"${O.dir $ o}/${unpack.unStorageKey.attributesKey.messageAttributes.psrMessage $ r}/playlist.m3u8", s"${O.dir $ o}/${unpack.unStorageKey.attributesKey.messageAttributes.psrMessage $ r}/%d.ts")
    Process(List("ffmpeg"), new File(o.dir + "/" + r.message.attributes.key))
  }
}
