import akka.actor.{Actor, ActorRef, Cancellable}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Plane extends Actor {

  class Fly(name: String, sender: ActorRef) {
    println(s"$name flying")
    var time = 10
    val cancellable: Cancellable = context.system.scheduler.schedule(1 millisecond, 1 seconds) {
      time = time - 1
      if (time == 0) {
        cancellable.cancel()
        sender ! AirplaneLanding(name)
      } else {
//        println(s"$name approach to land in $time")
      }
    }
  }

  def receive: PartialFunction[Any, Unit] = {
    case TakeOffMessage(name) =>
      println(s"$name started")
      sender ! AirplaneTookOffMessage(name)
      new Fly(name, sender)
    case WelcomeMessage(name) =>
      println(Console.GREEN + "Hello!" + Console.WHITE)
    case GreetingsMessage(name) =>
      println(s"$name : Thanks!")
    case PleaseWait(name) =>
      println(s"Waiting!")
      new Fly(name, sender)
  }
}