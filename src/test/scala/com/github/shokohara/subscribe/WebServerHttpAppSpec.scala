package com.github.shokohara.subscribe

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport.defaultNodeSeqUnmarshaller
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.shokohara.subscribe.subscribe.WebServerHttpApp
import org.scalatest.{ Matchers, WordSpec }

import scala.xml.NodeSeq

class WebServerHttpAppSpec extends WordSpec with Matchers with ScalatestRouteTest {

  "WebServiceHttpApp" should {
    "answer to any request to `/`" in {
      Get("/") ~> WebServerHttpApp.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Server up and running"
      }
      Post("/") ~> WebServerHttpApp.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Server up and running"
      }
    }
    "answer to GET requests to `/status`" in {
      Get("/status") ~> WebServerHttpApp.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[NodeSeq] shouldBe <html><body><h1>Say hello to akka-http</h1></body></html>
      }
    }
    "not handle a POST request to `/status`" in {
      Post("/status") ~> WebServerHttpApp.routes ~> check {
        handled shouldBe false
      }
    }
    "respond with 405 when not issuing a GET to `/status` and route is sealed" in {
      Put("/status") ~> Route.seal(WebServerHttpApp.routes) ~> check {
        status shouldBe StatusCodes.MethodNotAllowed
      }
    }
  }

}
