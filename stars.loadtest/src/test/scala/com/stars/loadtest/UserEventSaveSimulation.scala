package com.stars.loadtest

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class UserEventSaveSimulation extends Simulation {

  val baseUrl = System.getProperty("baseUrl", "http://localhost:8080")
  val rps = System.getProperty("rps", "50").toInt
  val rampSeconds = System.getProperty("rampSeconds", "60").toInt
  val holdSeconds = System.getProperty("holdSeconds", "120").toInt

  val httpProtocol = http
    .baseUrl(baseUrl)
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val feeder = Iterator.continually(Map(
    "email" -> s"${UUID.randomUUID()}@load.test",
    "fullName" -> "Load Test User",
    "status" -> "ACTIVE",
    "createdAt" -> java.time.Instant.now().toString
  ))

  val scn = scenario("Save User Event")
    .feed(feeder)
    .exec(http("POST /user/event-save")
      .post("/user/event-save")
      .body(StringBody("""{"email":"#{email}","fullName":"#{fullName}","status":"#{status}","createdAt":"#{createdAt}"}"""))
      .asJson
      .check(status.is(202)))

  setUp(
    scn.inject(
      rampUsersPerSec(1).to(rps).during(rampSeconds.seconds),
      constantUsersPerSec(rps).during(holdSeconds.seconds)
    ).protocols(httpProtocol)
  )
}