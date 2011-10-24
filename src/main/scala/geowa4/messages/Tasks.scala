package geowa4.messages

import akka.actor._

sealed trait DistributedMessage

case class Job(process: String, replyTo: ReplyTo) extends DistributedMessage
case object JobRequest extends DistributedMessage
case class JobCompleted(code: Int)
case class ReplyTo(host: String, port: Int, serviceName: String) extends DistributedMessage
case object Shutdown extends DistributedMessage
