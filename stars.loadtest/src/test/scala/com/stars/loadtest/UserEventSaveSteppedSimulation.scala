package com.stars.loadtest

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class UserEventSaveSteppedSimulation extends Simulation {

  val baseUrl = System.getProperty("baseUrl", "http://localhost:8080")
  val startRps = System.getProperty("startRps", "100").toInt
  val rpsStep = System.getProperty("rpsStep", "100").toInt
  val stepSeconds = System.getProperty("stepSeconds", "120").toInt
  val levels = System.getProperty("levels", "5").toInt

  val httpProtocol = http
    .baseUrl(baseUrl)
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .maxConnectionsPerHost(System.getProperty("maxConnections", "1000").toInt)

  val feeder = Iterator.continually(Map(
    "email" -> s"${UUID.randomUUID()}@load.test",
    "fullName" -> "Load Test User",
    "status" -> "ACTIVE",
    "createdAt" -> java.time.Instant.now().toString
  ))

  val scn = scenario("Save User Event Staircase")
    .feed(feeder)
    .exec(http("POST /user/event-save")
      .post("/user/event-save")
      .body(StringBody("""{"email":"#{email}","fullName":"#{fullName}","status":"#{status}","createdAt":"#{createdAt}"}"""))
      .asJson
      .check(status.is(202)))

  val injectSteps =
    (0 until levels).flatMap { i =>
      val target = startRps + rpsStep * i
      val from = if (i == 0) 1 else startRps + rpsStep * (i - 1)
      Seq(
        rampUsersPerSec(from).to(target).during(30.seconds),
        constantUsersPerSec(target).during(stepSeconds.seconds)
      )
    }

  setUp(
    scn.inject(injectSteps).protocols(httpProtocol)
  )
}