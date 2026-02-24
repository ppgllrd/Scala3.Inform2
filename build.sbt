// Use early semantic versioning scheme for version compatibility checks
ThisBuild / versionScheme := Some("early-semver")

ThisBuild / scalaVersion := "3.3.0"

// Publish artifacts to local Coursier Maven cache
ThisBuild / publishTo := {
  val cache = csrCacheDirectory.value.getAbsolutePath
  Some(
    MavenCache(
      "local-maven",
      file(s"$cache/https/repo1.maven.org/maven2")
    )
  )
}

ThisBuild / organization := "es.uma.lcc"

// Root project aggregates and depends on informlib library
lazy val root = project
  .in(file("."))
  .settings(
    name := "inform.examples",
    version := "0.0.1"
  ) aggregate informlib dependsOn informlib

// Library project providing graphics, robot simulation, statistics and utility modules
lazy val informlib = project
  .in(file("informlib"))
  .settings(
    name := "inform-lib",
    version := "0.0.4",
    publishConfiguration := publishConfiguration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0" withSources (), // GUI components
    libraryDependencies += "org.jfree" % "jfreechart" % "1.5.4" withSources (), // chart plotting
    libraryDependencies += "org.jfree" % "jcommon" % "1.0.24" withSources () // common utilities for JFreeChart
  )