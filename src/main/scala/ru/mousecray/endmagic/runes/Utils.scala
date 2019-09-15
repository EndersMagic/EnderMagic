package ru.mousecray.endmagic.runes

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable

object Utils {
  def cbfZipMaps[K, V1, V2] = new CanBuildFrom[Map[K, V1], ((K, V1), (K, V2)), Map[K, (V1, V2)]] {
    override def apply(from: Map[K, V1]): mutable.Builder[((K, V1), (K, V2)), Map[K, (V1, V2)]] =
      apply()

    override def apply(): mutable.Builder[((K, V1), (K, V2)), Map[K, (V1, V2)]] =
      new mutable.Builder[((K, V1), (K, V2)), Map[K, (V1, V2)]] {
        private val map = new mutable.OpenHashMap[K, (V1, V2)]()

        override def +=(elem: ((K, V1), (K, V2))): this.type = {
          map += elem._1._1 -> (elem._1._2, elem._2._2)
          this
        }

        override def clear(): Unit = map.clear()

        override def result(): Map[K, (V1, V2)] = map.toMap
      }
  }
}
