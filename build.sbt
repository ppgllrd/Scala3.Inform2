ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "Scala3"
  )

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0" withSources ()

libraryDependencies += "org.jfree" % "jfreechart" % "1.5.3" withSources ()

libraryDependencies += "org.jfree" % "jcommon" % "1.0.24" withSources ()

// Compile / unmanagedResourceDirectories += baseDirectory.value / "src/main/scala/inform/robot/assets"
