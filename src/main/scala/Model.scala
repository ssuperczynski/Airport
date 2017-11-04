case class TakeOffMessage(name: String)

case class GreetingsMessage(name: String)

case class AirplaneLanding(name: String)

case class AirplaneTookOffMessage(name: String)

case class PleaseWait(name: String)

case class WelcomeMessage(name: String)

case class Planes(planes: Seq[String])

case object StartMessage

case object StopMessage
