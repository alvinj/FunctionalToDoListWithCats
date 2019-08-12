name := "FPToDoListWithCats"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
   "org.typelevel" %% "cats-core" % "2.0.0-RC1",
   "org.typelevel" %% "cats-effect" % "1.3.1",
   "com.propensive" %% "kaleidoscope" % "0.1.0"
)

scalacOptions ++= Seq(
    "-deprecation",     //emit warning and location for usages of deprecated APIs
    "-unchecked",       //enable additional warnings where generated code depends on assumptions
    "-explaintypes",    //explain type errors in more detail
    "-Ywarn-dead-code", //warn when dead code is identified
    "-Xfatal-warnings"  //fail the compilation if there are any warnings
)

