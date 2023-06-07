val scala3Version = "3.3.0"

organization := "es.uma.lcc"

lazy val root = project
  .in(file("."))
  .settings(
    name := "inform.examples",
    version := "1.0.0",
    scalaVersion := scala3Version
  ) aggregate (informlib) dependsOn (informlib)

lazy val informlib = project
  .in(file("informlib"))
  .settings(
    name := "inform-lib",
    version := "1.0.0",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0" withSources (),
    libraryDependencies += "org.jfree" % "jfreechart" % "1.5.4" withSources (),
    libraryDependencies += "org.jfree" % "jcommon" % "1.0.24" withSources ()
  )

// sbt informlib/package
// sbt informlib/packageSrc
