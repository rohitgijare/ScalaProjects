import cappedcollection.{CappedCollection1, CappedCollection2, CappedCollection2Factory, CappedCollection3Factory}

object CappedCollectionRunner extends App {
  /**
   * Capped Collection 1
   */
  val coll = new CappedCollection1(capacity = 4)

  val res1 = coll :+ 1 :+ 2 :+ 3
  println(res1)
  println(res1.length)
  println(res1.lastOption)
  val res2 = res1 :+ 4 :+ 5 :+ 6
  println(res2.take(4))

  /**
   * Capped Collection 2
   */
  object Capped2Obj extends CappedCollection2Factory(capacity = 4)
  val res0 = Capped2Obj(1, 2, 3)
  println(res0)
  println(res0.take(2))
  println(res0.filter(x => x % 2 == 1))
  println(res0.map(x => x*x))
  println(List(1, 2, 3, 4, 5).to(Capped2Obj))

  /**
   * Capped Collection 3
   */
  object Capped3Obj extends CappedCollection3Factory(capacity = 4)
  val res10 = Capped3Obj(1, 2, 3)
  println(res10)
  println(res10.take(2))
  println(res10.filter(x => x % 2 == 1))
  println(res10.map(x => x*x))
  println(List(1, 2, 3, 4, 5).to(Capped3Obj))
}
