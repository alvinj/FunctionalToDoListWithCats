To-Do List
==========

This is a little command-line “To-Do List” app, based on the
To-Do List app in the book,
[Learn You A Haskell For Great Good](https://kbhr.co/lyahfgg).
This version of the app is written with the
[Cats](https://github.com/typelevel/cats) and
[Cats-Effect](https://github.com/typelevel/cats-effect) libraries.

The purposes of this application are:

- To show how to write a small but complete functional programming (FP) application
  in Scala, in this case using Cats and Cats-Effect
- To show how to create a single, executable JAR file from a 
  Scala/SBT application with [sbt-assembly](https://github.com/sbt/sbt-assembly)
- To show how to create a native executable with GraalVM

The FP part is really the main purpose. Once I created the app I knew that
I wanted to use Graal to make it start up faster, so that led to the 
sbt-assembly need.


## Using the application

Once you create the native image, start the application like this:

````
$ todo
````

Then you’ll see this prompt:

````
Command ('h' for help, 'q' to quit)
==>
````

If you type `h` you’ll see this help text:

````
Possible commands
-----------------
add <task>       - add a to-do item
h                - show this help text
rm [task number] - remove a task by its number
v                - view the list of tasks
q                - quit
````

To add a task, use the `add` command:

````
add wake up
````

Hopefully everything after that is relatively easy to understand.


## The source code

If you read my book, [Functional Programming, Simplified](https://alvinalexander.com/scala/functional-programming-simplified-book), hopefully most of the source code
will be understandable. The big difference between what I show in the
book and this application is the use of the Scalaz library.

In short:

- `ToDoListFIO extends App` is where the action starts
- The `mainLoop` is the application’s main loop
- This line of code converts the user’s input into a `Command`:
```scala
cmd   <- getLine.map(Command.parse _)
```

If you read my book, I hope everything else makes sense.


## Creating a single, executable JAR file

To create a single, executable JAR file, using the `sbt assembly` command,
or use the `assembly` command at the SBT prompt.


## GraalVM

To create a native executable with GraalVM will take a little more work
on your part, but in short:

- Install GraalVM on your system
- `cd` into this project’s *Graal* directory
- I don’t use Graal all the time, so I source the 
  *1setup_graal* file to set the necessary GraalVM parameters
- Then I run the *2compile_graal.sh* script to create the 
  *todo* native image (executable).

The reason for using Graal is to create a native executable
image that starts up almost immediately, which is really nice
for a command-line application like this.


## More information

For more information, see [this web page](https://alvinalexander.com/scala/functional-programming-to-do-list-application-cats).


All the best,  
Alvin Alexander  
https://alvinalexander.com

