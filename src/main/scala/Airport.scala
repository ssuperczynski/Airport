
import akka.actor.{Actor, ActorRef, Cancellable}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class Airport(plane: ActorRef) extends Actor {
  var isRunwayBusy = false

  class Landing(name: String, sender: ActorRef) {
    isRunwayBusy = true
    var time = 3
    val cancellable: Cancellable = context.system.scheduler.schedule(1 millisecond, 1 seconds) {
      time = time - 1
      if (time == 0) {
        cancellable.cancel()
        println(Console.GREEN + s"Airport2: Welcome $name!" + Console.WHITE)
        sender ! WelcomeMessage(name)
        isRunwayBusy = false
      } else {
        println(s"$name RUNWAY approach to land in $time")
      }
    }
  }

  def receive: PartialFunction[Any, Unit] = {
    case Planes(planes) =>
      planes.foreach((name) => {
        plane ! TakeOffMessage(name)
      })

    case AirplaneTookOffMessage(name) =>
      println(s"Airport: Have a nice flight $name!")
      sender ! GreetingsMessage(name)
    case AirplaneLanding(name) =>
      println(Console.CYAN + s"$name Would like to land" + Console.WHITE)
      if (isRunwayBusy) {
        println(Console.RED + "Runway Busy" + Console.WHITE)
        sender ! PleaseWait(name)
      } else {
        new Landing(name, sender)
      }
  }
}
