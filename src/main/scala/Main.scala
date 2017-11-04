

import akka.actor._

object Main extends App {
  val system = ActorSystem("AirportSystem")
  val plane = system.actorOf(Props[Plane], name = "plane")
  val airport = system.actorOf(Props(new Airport(plane)), name = "airport")
  val planes = Seq(
    "Lufthansa boeing",
    "Lufthansa jumboJet",
    "Lufthansa airbus",
    "AirFrance boeing",
    "AirFrance jumboJet",
    "AirFrance airbus",
    "Lufthansa boeing",
    "Lufthansa jumboJet",
    "Lufthansa airbus",
    "AirFrance boeing",
    "AirFrance jumboJet",
    "AirFrance airbus",
    "Lufthansa boeing",
    "Lufthansa jumboJet",
  )
  airport ! Planes(planes)
}