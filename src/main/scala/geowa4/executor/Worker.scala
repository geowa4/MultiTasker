package geowa4.executor

import scala.sys.process.Process
import akka.actor._
import akka.actor.Actor._
import geowa4.messages._

object Worker {
  def main(args: Array[String]) {
    actorOf[Worker].start() ! Work
  }
}

class Worker extends Actor {
  private val minSleepTime = 50
  private val growthRate = 2
  private val maxSleepTime = 1000

  private var sleepTime = minSleepTime

  def receive = {
    case Empty =>
      Console.println("The Master is empty!")
      
      Thread.sleep(sleepTime)
      sleepTime = sleepTime * growthRate
      self ! Work
    case Work =>
      Console.println("Asking Master for something to do.")
      masterActor ! JobRequest
    case j: Job =>
      Console.println("Job received: " + j)
      val replyTo = remote.actorFor(j.replyTo.serviceName,
        j.replyTo.host, j.replyTo.port)
      val proc = Process(j.process)
      replyTo ! JobCompleted(proc !)
      self ! Work
  }

  def masterActor = {
    remote.actorFor(Master.serviceName, Master.host, Master.port)
  }
}
