package geowa4.messages

import akka.actor._

sealed trait DistributedMessage

case class Job(process: String, replyTo: ReplyTo) extends DistributedMessage
case object JobRequest extends DistributedMessage
case class ReplyTo(actorRef: ActorRef)
case object Empty extends DistributedMessage
