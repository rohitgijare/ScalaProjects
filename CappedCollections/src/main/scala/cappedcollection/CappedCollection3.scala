package cappedcollection

import scala.collection.{IndexedSeqView, IterableFactory, IterableOps, StrictOptimizedIterableOps, immutable, mutable}

final class CappedCollection3[A] private (val capacity: Int, val length: Int,
                                          offset: Int, elems: Array[Any])
  extends immutable.Iterable[A]
  with IterableOps[A, CappedCollection3, CappedCollection3[A]]
  with StrictOptimizedIterableOps[A, CappedCollection3, CappedCollection3[A]] { self =>

  def this(capacity: Int) = this(capacity, length=0, offset=0, elems=Array.ofDim(capacity))

  def appended[B >: A](elem: B): CappedCollection3[B] = {
    val newElems = Array.ofDim[Any](capacity)
    Array.copy(elems, 0, newElems, 0, capacity)
    val (newOffset, newLength) =
      if (length == capacity) {
        newElems(offset) = elem
        ((offset + 1) % capacity, length)
      } else {
        newElems(length) = elem
        (offset, length + 1)
      }

    new CappedCollection3[B](capacity, newLength, newOffset, newElems)
  }

  @inline def :+ [B >: A](elem: B): CappedCollection3[B] = appended(elem)

  def apply(i: Int): A =
    elems((i + offset) % capacity).asInstanceOf[A]

  def iterator: Iterator[A] = view.iterator

  override def view: IndexedSeqView[A] = new IndexedSeqView[A] {
    override def apply(i: Int): A = self(i)

    override def length: Int = self.length
  }

  override def knownSize: Int = length

  override def className: String = "CappedCollection3"

  override val iterableFactory: IterableFactory[CappedCollection3] =
    new CappedCollection3Factory(capacity)

  override protected def fromSpecific(coll: IterableOnce[A]): CappedCollection3[A] =
    iterableFactory.from(coll)

  override protected def newSpecificBuilder: mutable.Builder[A, CappedCollection3[A]] =
    iterableFactory.newBuilder

  override def empty: CappedCollection3[A] = iterableFactory.empty
}

class CappedCollection3Factory(capacity: Int) extends IterableFactory[CappedCollection3] {
  override def from[A](source: IterableOnce[A]): CappedCollection3[A] =
    source match {
      case capped: CappedCollection3[A] if capped.capacity == capacity => capped
      case _ => (newBuilder[A] ++= source).result()
    }

  override def empty[A]: CappedCollection3[A] = new CappedCollection3[A](capacity)

  override def newBuilder[A]: mutable.Builder[A, CappedCollection3[A]] =
    new mutable.ImmutableBuilder[A, CappedCollection3[A]](empty) {
      override def addOne(elem: A): this.type = {
        elems = elems :+ elem
        this
      }
    }
}
