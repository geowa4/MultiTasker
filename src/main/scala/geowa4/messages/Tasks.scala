sealed trait DistributedMessage

case class Job(process: String) extends DistributedMessage
