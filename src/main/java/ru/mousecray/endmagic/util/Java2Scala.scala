package ru.mousecray.endmagic.util

import java.util.function

import scala.language.implicitConversions

object Java2Scala {

  implicit def consumer2Java[A](f: A => Unit): function.Consumer[A] = new function.Consumer[A] {
    override def accept(t: A): Unit = f(t)
  }

  implicit def biConsumer2Java[A, B](f: (A, B) => Unit): function.BiConsumer[A, B] = new function.BiConsumer[A, B] {
    override def accept(t: A, u: B): Unit = f(t, u)
  }

  implicit def function2Java[A, B](f: A => B): function.Function[A, B] = new function.Function[A, B] {
    override def apply(t: A): B = f(t)
  }

  implicit def predicate2Java[A](f: A => Boolean): function.Predicate[A] = new function.Predicate[A] {
    override def test(t: A): Boolean = f(t)
  }

}
