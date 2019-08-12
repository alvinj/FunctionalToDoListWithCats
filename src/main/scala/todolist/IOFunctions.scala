package todolist

import cats.effect.{IO, Resource}
import java.io.{BufferedWriter, File, FileWriter}
import scala.io.{BufferedSource,Source}

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

    /**
     * TWO IMPLEMENTATIONS OF `writeFile` (`bracket` and `Resource`)
     * -------------------------------------------------------------
     */

    /**
     * Use Cats `bracket`:
     * https://typelevel.org/cats-effect/tutorial/tutorial.html#what-about-bracket
     */
    def writeFile(filename: String, text: String, append: Boolean): IO[Unit] = IO {
        // acquire
        IO(new BufferedWriter(new FileWriter(new File(filename), append))).bracket { bw =>
            // use
            IO(bw.write(text + "\n"))
        } { bw =>
            // release (note that you can put whatever logic you want here, such as logging)
            IO(bw.close())
        }.unsafeRunSync()
    }

    /**
     * Use cats `Resource`
     * Definitely need `unsafeRunSync()` with Resource:
     * https://typelevel.org/cats-effect/datatypes/resource.html
     */
    // def writeFile(filename: String, text: String, append: Boolean): IO[Unit] = IO {
    //     val acquire = IO {
    //         new BufferedWriter(new FileWriter(new File(filename), append))
    //     }  
    //     // BufferedWriter extends java.lang.AutoCloseable, so this works
    //     Resource.fromAutoCloseable(acquire)
    //         .use(bw => IO(bw.write(text + "\n")))
    //         .unsafeRunSync()
    // }


    /**
      * TWO IMPLEMENTATIONS OF `readFile` (`bracket` and `Resource`)
      * ------------------------------------------------------------
      */

    // `bracket`
    def readFile(filename: String): IO[Seq[String]] = {
        // acquire
        IO(Source.fromFile(filename)).bracket { source =>
            // use
            IO((for (line <- source.getLines) yield line).toVector)
        } { source =>
            // release
            IO(source.close())
        }
    }
    
    // `Resource`
    // def readFile(filename: String): IO[Seq[String]] = {
    //     val acquire: IO[BufferedSource] = IO(Source.fromFile(filename))
    //     Resource.fromAutoCloseable(acquire)
    //         .use { source => 
    //             IO {
    //                 val lines = (for (line <- source.getLines) yield line).toVector
    //                 IO(lines)
    //             }
    //         }
    //         .unsafeRunSync()
    // }
    
}






