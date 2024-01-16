ThisBuild / versionScheme := Some("early-semver")

ThisBuild / scalaVersion := "3.3.0"

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

lazy val root = project
  .in(file("."))
  .settings(
    name := "inform.examples",
    version := "0.0.1"
  ) aggregate informlib dependsOn informlib


lazy val informlib = project
  .in(file("informlib"))
  .settings(
    name := "inform-lib",
    version := "0.0.2",
    publishConfiguration := publishConfiguration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0" withSources (),
    libraryDependencies += "org.jfree" % "jfreechart" % "1.5.4" withSources (),
    libraryDependencies += "org.jfree" % "jcommon" % "1.0.24" withSources ()
  )