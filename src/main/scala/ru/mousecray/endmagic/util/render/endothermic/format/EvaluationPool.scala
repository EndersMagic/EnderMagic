package ru.mousecray.endmagic.util.render.endothermic.format

import java.util.function

import scala.collection.mutable

object EvaluationPool {

  private val map = new mutable.OpenHashMap[() => Any, Any]()

  def evaluateAndMemoize[A](f: () => A): A =
    map.getOrElseUpdate(f, f()).asInstanceOf[A]

  def memoize[A, B](f: A => B): A => B = {
    val cache = new java.util.HashMap[A, B]()
    val value = new function.Function[A, B] {
      override def apply(t: A): B = f(t)
    }
    cache.computeIfAbsent(_, value)
  }

}
