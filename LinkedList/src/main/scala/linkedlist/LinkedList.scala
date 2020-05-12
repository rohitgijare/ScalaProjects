package linkedlist

sealed trait LinkedList {
  def isEmpty: Boolean = {
    this match {
      case Empty => true
      case _ => false
    }
  }

  def isEqualToList(other: List[Int]): Boolean = {
    this match {
      case Empty => other.isEmpty
      case Node(head, tail) => head == other.head && tail.isEqualToList(other.tail)
    }
  }

  def head: Int = {
    this match {
      case Empty => throw new NoSuchElementException("head of empty list")
      case Node(head, _) => head
    }
  }

  def tail: LinkedList = {
    this match {
      case Empty => throw new NoSuchElementException("tail of empty list")
      case Node(_, tail) => tail
    }
  }

  def ::(b: Int): LinkedList = {
    this match {
      case Empty => LinkedList(b)
      case xs@Node(_, _) => LinkedList(b, xs)
    }
  }

  def :::(other: LinkedList): LinkedList = {
    other match {
      case Empty => this
      case Node(x, xs) => x :: xs ::: this
    }
  }

  def length: Int = {
    this match {
      case Empty => 0
      case Node(_, t) => 1 + t.length
    }
  }

  def last: Int = {
    this match {
      case Empty => throw new NoSuchElementException("Nil.last")
      case Node(_, t) => if (t.length == 1) t.head else t.last
    }
  }

  def init: LinkedList = {
    def constructListWOTail(consumedList: LinkedList, listSoFar: LinkedList): LinkedList = {
      consumedList match {
        case Empty => throw new NotImplementedError("Cannot happen!")
        case Node(x, xs) => {
          val newListSoFar = x :: listSoFar
          if (xs.length == 1) newListSoFar else constructListWOTail(xs, newListSoFar)
        }
      }
    }
    this match {
      case Empty => throw new NoSuchElementException("Nil.init")
      case x@Node(_, _) => constructListWOTail(x, Empty)
    }
  }

  def reverse: LinkedList = {
    this match {
      case Empty => Empty
      case Node(x, xs) => xs.reverse ::: LinkedList(x)
    }
  }

  def flatMap(f: Int => LinkedList): LinkedList = {
    this match {
      case Empty => Empty
      case Node(headVal, tailVal) => f(headVal) ::: (tailVal flatMap f)
    }
  }

  def map(f: Int => Int): LinkedList = {
    this match {
      case Empty => Empty
      case Node(headVal, tailVal) => f(headVal) :: (tailVal map f)
    }
  }

  def foldLeft(z: Int)(op: (Int, Int) => Int): Int = {
    this match {
      case Empty => z
      case Node(x, xs) => op(xs.foldLeft(z)(op), x)
    }
  }

  def foldRight(z: Int)(op: (Int, Int) => Int): Int = {
    this match {
      case Empty => z
      case Node(x, xs) => op(x, xs.foldRight(z)(op))
    }
  }
}

case class Node(headVal: Int, tailVal: LinkedList) extends LinkedList

case object Empty extends LinkedList

object LinkedList {
  def apply(items: Int*): LinkedList = {
    if (items.isEmpty) {
      Empty
    } else {
      Node(items.head, apply(items.tail: _*))
    }
  }

  def apply(other: LinkedList): LinkedList = {
    other match {
      case Empty => Empty
      case Node(headVal, tailVal) => Node(headVal, apply(tailVal))
    }
  }

  def apply(x: Int, other: LinkedList): LinkedList = {
    Node(x, apply(other))
  }
}