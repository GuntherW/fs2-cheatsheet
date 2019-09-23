package de.codecentric.wittig.fs2

import cats.effect.{ExitCode, IO, IOApp}
import fs2._

object Tk extends App {

  def tk[F[_], O](n: Long): Pipe[F, O, O] = { in =>
    in.scanChunksOpt(n) { n =>
      if (n <= 0) {
        None
      } else
        Some { c =>
          println("n: " + n)
          println("c: " + c)
          c.size match {
            case m if m < n => (n - m, c)
            case m          => (0, c.take(n.toInt))
          }
        }
    }
  }

  val stream = Stream(1, 2, 3, 4)
  val erg    = stream.through(tk(3)).toList

  println(erg)
}
