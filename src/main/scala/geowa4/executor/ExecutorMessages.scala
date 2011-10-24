package geowa4.executor

sealed trait ExecutorMessage

case object Empty extends ExecutorMessage
case object Work extends ExecutorMessage
