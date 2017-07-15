package com.github.shokohara.subscribe.routes

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport.defaultNodeSeqUnmarshaller
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.shokohara.subscribe.subscribe.routes.SimpleRoutes
import org.scalatest.{ Matchers, WordSpec }

import scala.xml.NodeSeq

class SimpleRoutesSpec extends WordSpec with Matchers with ScalatestRouteTest with SimpleRoutes {

  "SimpleRoute" should {
    "answer to GET requests to `/status`" in {
      Get("/status") ~> simpleRoutes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "status"
      }
    }
    "not handle a POST request to `/status`" in {
      Post("/status") ~> simpleRoutes ~> check {
        handled shouldBe false
      }
    }
    "respond with 405 when not issuing a GET to `/status` and route is sealed" in {
      Put("/status") ~> Route.seal(simpleRoutes) ~> check {
        status shouldBe StatusCodes.MethodNotAllowed
      }
    }
  }

}
