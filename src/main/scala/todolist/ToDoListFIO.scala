package todolist

import cats.effect.IO
/**
 * this enables `>>` after `IO`. found at the example at this url:
 * https://typelevel.org/cats-effect/tutorial/tutorial.html#copying-data
 */
import cats.implicits._
import IOFunctions._

/**
 * A “functional programming” (FP) style To-Do List application,
 * with some “functional I/O.”
 */
object ToDoListFIO extends App {

    /**
     * Note that `datafile` is used as a closured-thing. Not horrible in this situation.
     */
    val datafile = "/Users/al/Projects/Scala/FunctionalProgramming/ToDoListWithCats/todo.dat"
    val prompt = "Command ('h' for help, 'q' to quit)\n==> "

    /**
     * As its name implies, this is the main loop.
     * See the `help` function for a list of things the user can type in.
     */
    def mainLoop: IO[Unit] = for {
        _     <- IOFunctions.putStr(prompt)
        cmd   <- getLine.map(Command.parse _)
        _     <- if (cmd == Quit) {
                     IO.unit
                 } else {
                     processCommand(cmd) >> mainLoop  //note the recursion here
                 }
    } yield ()

    // start the loop (synchronously)
    mainLoop.unsafeRunSync()

    /**
     * got a command from the user, handle it
     */
    def processCommand(cmd: Command): IO[Unit] = cmd match {
        case Add(task) => {
            // `>>` is used to sequence two monadic actions
            add(task) >> view
        }
        case Remove(n) => {
            // TODO this will eventually fail if a number is not supplied after 'remove'
            remove(n.toInt) >> view
        }
        case View => {
            view
        }
        case Help => {
            help
        }
        case Unknown => {
            putStrLn("Unknown (type 'h' for help)\n")
        }
    }

    /**
     * append the task to the file
     */
    def add(task: String): IO[Unit] = writeFile(datafile, task, true)

    /**
     * list all of the items in the file, with the task number before each task.
     */
    def view: IO[Unit] = for {
        lines  <- readFile(datafile)
        result <- IO((for ((line,i) <- lines.zip(Stream from 1)) yield s"$i. $line").mkString("\n"))
        _      <- putStrLn(result + "\n")
    } yield ()

    def help: IO[Unit] = {
        val text = """
        |Possible commands
        |-----------------
        |add <task>       - add a to-do item
        |h                - show this help text
        |rm [task number] - remove a task by its number
        |v                - view the list of tasks
        |q                - quit
        """.stripMargin
        putStrLn(text)
    }

    /**
     * remove the given task number from the file.
     * `taskToRemove` will be based on 1,2,3.
     * however, the list will be zero-based.
     */
    def remove(taskNumToRemove: Int): IO[Unit] = for {
        currentTasksAsIO       <- readFile(datafile)
        currentTasks           <- IO(currentTasksAsIO.toVector)
        remainingTasks         <- IO(CollectionUtils.removeElementFromSequence(currentTasks, taskNumToRemove-1))
        remainingTasksAsString <- IO(remainingTasks.mkString("\n"))
        _                      <- writeFile(datafile, remainingTasksAsString, false)
    } yield ()

    private def getArgs: IO[Tuple2[String, String]] = IO(args(0), args(1))

}





