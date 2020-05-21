package queue

class StrangeIntQueue(private val internalList: List[Int]) extends Queue[Int] {
  override def head: Int =
    internalList.head

  override def tail: Queue[Int] =
    new StrangeIntQueue(internalList.tail)

  override def enqueue(x: Int): Queue[Int] = {
    val newInternalList = x :: internalList
    new StrangeIntQueue(newInternalList)
  }
}
