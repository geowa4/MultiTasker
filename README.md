This project is meant to be both an experimentation with Scala and Akka as well as a demonstration of their capability to run tasks on multiple nodes.

Currently, this project will run with three nodes. In order to run this, start up geowa4.executor.Master; then run geowa4.executor.Worker and geowa4.interface.CLI. The order for the last two does not matter. The CLI will ask you for a command to be queued up in the Master. The Worker will poll the Master for things to do. When it gets a Job, it will respond to the CLI with the exit code.

You can run as many instances of the CLI and Worker as you want. 

The command is any process that the system on the Worker can execute. Currently, there is no support for Windows or Unix only commands. Also, output is not collected. Both of those are planned.