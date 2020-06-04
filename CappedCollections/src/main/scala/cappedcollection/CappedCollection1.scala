package cappedcollection

import scala.collection.{AbstractIterator, immutable}

class CappedCollection1[A] private (val capacity: Int, val length: Int, offset: Int, elems: Array[Any])
  extends immutable.Iterable[A] { self =>
  def this(capacity: Int) = this(capacity, length=0, offset=0, elems=Array.ofDim(capacity))

  def appended[B >: A](elem: B): CappedCollection1[B] = {
    val newElems = Array.ofDim[Any](capacity)
    Array.copy(elems, 0, newElems, 0, capacity)
    val (newOffset, newLength) =
      if (length == capacity) {
        newElems(offset) = elem
        ((offset+1) % capacity, length)
      } else {
        newElems(length) = elem
        (offset, length+1)
      }

    new CappedCollection1[B](capacity, newLength, newOffset, newElems)
  }

  @inline def :+ [B >: A](elem: B): CappedCollection1[B] = appended(elem)

  def apply(i: Int): A = elems((i+offset) % capacity).asInstanceOf[A]

  override def iterator: Iterator[A] = new AbstractIterator[A] {
    private var current = 0
    override def hasNext: Boolean = current < self.length

    override def next(): A = {
      val elem = self(current)
      current += 1
      elem
    }
  }

  override def className: String = "CappedCollection1"
}
