package cappedcollection

import scala.collection.{AbstractIterator, IterableFactory, IterableOps, immutable, mutable}

class CappedCollection2[A] private (val capacity: Int, val length: Int, offset: Int, elems: Array[Any])
  extends immutable.Iterable[A]
  with IterableOps[A, CappedCollection2, CappedCollection2[A]] { self =>
  def this(capacity: Int) = this(capacity, length=0, offset=0, elems=Array.ofDim(capacity))

  def appended[B >: A](elem: B): CappedCollection2[B] = {
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

    new CappedCollection2[B](capacity, newLength, newOffset, newElems)
  }

  @inline def :+ [B >: A](elem: B): CappedCollection2[B] = appended(elem)

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

  override def className: String = "CappedCollection2"

  override val iterableFactory: IterableFactory[CappedCollection2] =
    new CappedCollection2Factory(capacity)

  override protected def fromSpecific(coll: IterableOnce[A]): CappedCollection2[A] =
    iterableFactory.from(coll)

  override protected def newSpecificBuilder: mutable.Builder[A, CappedCollection2[A]] =
    iterableFactory.newBuilder

  override def empty: CappedCollection2[A] = iterableFactory.empty
}

class CappedCollection2Factory(capacity: Int) extends IterableFactory[CappedCollection2] {
  override def from[A](source: IterableOnce[A]): CappedCollection2[A] =
    (newBuilder[A] ++= source).result()

  override def empty[A]: CappedCollection2[A] = new CappedCollection2[A](capacity)

  override def newBuilder[A]: mutable.Builder[A, CappedCollection2[A]] =
    new mutable.ImmutableBuilder[A, CappedCollection2[A]](empty) {
      override def addOne(elem: A): this.type = {
        elems = elems :+ elem
        this
      }
    }
}
