package geowa4.interface

import akka.actor._
import akka.actor.Actor._
import geowa4.executor.Master
import geowa4.messages._

object CLI {
  val kill = "kill"
  val cliActor = actorOf[CLI].start()

  def main(args: Array[String]) {
    cliActor ! ReadLine
  }

  def responseReceived { 
    cliActor ! ReadLine
  }
}

class CLI extends Actor {
  def receive = {
    case ReadLine =>
      read
    case _ =>
      println("Invalid message")
  }

  def read {
    Console print "Enter a command to be queued up: "
    val input = Console.readLine()
    if(input == CLI.kill) { 
      println("Killing")
      Master.masterActor ! PoisonPill
      self ! PoisonPill
    } else { 
      Master.masterActor ! Job(input, ReplyTo(self))
      CLI.responseReceived
    }
  }
}
