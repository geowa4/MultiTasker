package geowa4.interface

import akka.actor._
import akka.actor.Actor._
import geowa4.executor.Master
import geowa4.messages._

object CLI {
  val serviceName = "cli"
  val host = "localhost"
  val port = 2552
  val kill = "kill"
  val cliActor = actorOf[CLI].start()

  def main(args: Array[String]) {
    remote.start(host, port)
    remote.register(serviceName, actorOf[CLI])
    cliActor ! ReadLine
  }

  def responseReceived {
    cliActor ! ReadLine
  }
}

class CLI extends Actor {
  val replyTo = ReplyTo(CLI.host, CLI.port, CLI.serviceName)

  def receive = {
    case ReadLine =>
      read
    case jc: JobCompleted =>
      Console.println("Job completed with exit code: %d".format(jc.code))
      CLI.responseReceived
    case _ =>
      println("Invalid message")
  }

  def read {
    Console print "Enter a command to be queued up: "
    val input = Console.readLine()
    if (input == CLI.kill) {
      println("Shutting down the CLI.")
      self ! PoisonPill
    } else {
      masterActor ! Job(input, replyTo)
    }
  }

  def masterActor = { 
    remote.actorFor(Master.serviceName, Master.host, Master.port)
  }
}

