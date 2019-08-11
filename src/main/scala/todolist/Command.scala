package todolist

import kaleidoscope._

trait Command
case class Add(task: String) extends Command
case class Remove(taskNumber: String) extends Command
case object View extends Command
case object Quit extends Command
case object Help extends Command
case object Unknown extends Command

object Command {

    /** command examples:
     * `add wake up and make coffee`
     * `rm 1`
     */
    def parse(s: String): Command = s match {
        case r"add ${task}@(.*)" => Add(task)
        case r"rm ${taskNum}@(.*)" => Remove(taskNum)
        case r"remove ${taskNum}@(.*)" => Remove(taskNum)
        case "h" | "help" => Help
        case "v" | "view" => View
        case "q" | "quit" => Quit
        case _ => Unknown
    }

}

