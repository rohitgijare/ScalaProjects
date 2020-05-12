import linkedlist.LinkedList

object LinkedListRunner extends App {
  val linkedList = LinkedList(1, 2, 3)
  println(linkedList)

  println(5 :: linkedList)
  println(linkedList.init)

  val numbers = LinkedList(1, 2, 3, 4)
  println(numbers flatMap (x => LinkedList(x, x*x, x*x*x)))
  println(numbers map (x => x+x))

  println(numbers.foldLeft(0)(_+_))
  println(numbers.foldLeft(1)(_*_))
}
