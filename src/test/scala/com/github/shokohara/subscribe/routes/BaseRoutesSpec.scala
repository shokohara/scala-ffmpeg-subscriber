package com.github.shokohara.subscribe.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.shokohara.subscribe.subscribe.routes.BaseRoutes
import org.scalatest.{ Matchers, WordSpec }

class BaseRoutesSpec extends WordSpec with Matchers with ScalatestRouteTest {

  "BaseRoute" should {
    "answer to any request to `/`" in {
      Get("/") ~> BaseRoutes.baseRoutes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Server up and running"
      }
      Post("/") ~> BaseRoutes.baseRoutes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Server up and running"
      }
    }
  }

}
