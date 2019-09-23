import Dependencies._

scalaVersion := "2.13.1"
version := "0.1.0-SNAPSHOT"
organization := "de.codecentric.wittig"
organizationName := "codecentric"

lazy val root = (project in file("."))
  .settings(
    name := "fs2-cheatsheet",
    libraryDependencies ++= Seq(
      circeCore,
      circeGeneric,
      circeFs2,
      fs2Core,
      fs2Io,
      fs2ReactiveStreams,
      fs2Experimental,
      scalaTest % Test
    )
  )

turbo := true
