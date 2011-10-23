package geowa4.executor

import akka.actor._
import akka.actor.Actor._
import geowa4.messages._
import scala.collection.immutable.Queue

object Master {
  val masterActor = actorOf[Master].start()
}

class Master extends Actor {
  var q: Queue[Job] = Queue.empty[Job]
  def receive = {
    case j: Job =>
      Console.println("Received Job: " + j)
      q :+= j
    case JobRequest =>
      q.headOption match {
        case Some(j) =>
          self reply j
          q = q.tail
        case None =>
          self reply Empty
      }
  }
}
