package todolist

import cats.effect.IO
import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source

/**
 * NOTE: Scalaz has some functions like `putStrLn` implemented, but at least when I first
 * wrote this code, they didn’t have functions for reading and writing files.
 * 
 * Per this url (http://eed3si9n.com/learning-scalaz/IO+Monad.html) you have to implement
 * your own, as they have done at the bottom of that page.
 * 
 */
object IOFunctions {

    def getLine: IO[String] = IO(scala.io.StdIn.readLine())
    def putStr(s: String): IO[Unit] = IO(print(s))
    def putStrLn(s: String): IO[Unit] = IO(println(s))

    def writeFile(filename: String, text: String, append: Boolean): IO[Unit] = IO {
        val bw = new BufferedWriter(new FileWriter(new File(filename), append))
        bw.write(text + "\n")
        bw.close
    }

    def readFile(filename: String): IO[Seq[String]] = IO {
        val source = Source.fromFile(filename)
        val lines = (for (line <- source.getLines) yield line).toVector
        source.close
        lines
    }
    
}






