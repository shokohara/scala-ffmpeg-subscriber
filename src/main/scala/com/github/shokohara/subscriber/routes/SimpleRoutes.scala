package com.github.shokohara.subscriber.routes

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport.defaultNodeSeqMarshaller
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.coding.Deflate
import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.model.StatusCodes.MovedPermanently
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout

/**
 * Routes can be defined in separated classes like shown in here
 */
trait SimpleRoutes {

  // This `val` holds one route (of possibly many more that will be part of your Web App)
  lazy val simpleRoutes =
    path("hello") { // Listens to paths that are exactly `/hello`
      get { // Listens only to GET requests
        complete(<html><body><h1>Say hello to akka-http</h1></body></html>) // Completes with some html page
      }
    }~ path("payload") {
      post {
        complete("payload")
      }
    }~ path("status") {
      get {
        complete(<html><body><h1>Say hello to akka-http</h1></body></html>) // Completes with some html page
      }
    }
}
