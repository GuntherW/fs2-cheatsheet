package de.codecentric.wittig.fs2

import java.nio.file.{Paths, StandardOpenOption}

import cats.effect.{Blocker, ExitCode, IO, IOApp}
import cats.implicits._
import fs2.io.file.{readAll, writeAll}
import fs2.{Stream, text}
import io.circe.fs2._
import io.circe.generic.auto._
import io.circe.syntax._

object JsonStream extends IOApp {

  private val resourceFolder = "src/main/resources"
  private val chunkSize      = 4096

  // liest ein Json einer Person ein, und erhöht das "alter" um eins
  private val personInkrementer: Stream[IO, Unit] = Stream.resource(Blocker[IO]).flatMap { blocker =>
    readAll[IO](Paths.get(s"$resourceFolder/person.json"), blocker, chunkSize)
      .through(byteStreamParser)
      .through(decoder[IO, Person])
      .map(person => person.copy(alter = person.alter + 1))
      .map(_.asJson.spaces2)
      .through(text.utf8Encode)
      .through(writeAll(Paths.get(s"$resourceFolder/personInkremented.json"), blocker))
  }

  // liest ein JsonArray vieler Personen ein, und erhöht das "alter" um eins
  private val personenInkrementer: Stream[IO, Unit] = Stream.resource(Blocker[IO]).flatMap { blocker =>
    val start = Stream.eval(IO("["))
    val end   = Stream.eval(IO("]"))

    val json = readAll[IO](Paths.get(s"$resourceFolder/persons.json"), blocker, chunkSize)
      .through(byteArrayParser)
      .through(decoder[IO, Person])
      .map(person => person.copy(alter = person.alter + 1))
      .map(_.asJson.spaces2)
      .intersperse(",")

    (start ++ json ++ end)
      .through(text.utf8Encode)
      .through(writeAll(Paths.get(s"$resourceFolder/personsInkremented.json"), blocker, List(StandardOpenOption.TRUNCATE_EXISTING)))
  }

  def run(args: List[String]): IO[ExitCode] =
    (personInkrementer ++ personenInkrementer).compile.drain
      .as(ExitCode.Success)
}

case class Person(name: String, alter: Int)
