import sbt._

object Version {
  lazy val Circe     = "0.12.1"
  lazy val CirceFs2  = "0.12.0"
  lazy val Fs2       = "2.0.0"
  lazy val ScalaTest = "3.0.8"
}

object Dependencies {
  lazy val circeCore          = "io.circe"      %% "circe-core"           % Version.Circe
  lazy val circeGeneric       = "io.circe"      %% "circe-generic"        % Version.Circe
  lazy val circeFs2           = "io.circe"      %% "circe-fs2"            % Version.CirceFs2
  lazy val fs2Core            = "co.fs2"        %% "fs2-core"             % Version.Fs2
  lazy val fs2Io              = "co.fs2"        %% "fs2-io"               % Version.Fs2
  lazy val fs2ReactiveStreams = "co.fs2"        %% "fs2-reactive-streams" % Version.Fs2
  lazy val fs2Experimental    = "co.fs2"        %% "fs2-experimental"     % Version.Fs2
  lazy val scalaTest          = "org.scalatest" %% "scalatest"            % Version.ScalaTest
}
