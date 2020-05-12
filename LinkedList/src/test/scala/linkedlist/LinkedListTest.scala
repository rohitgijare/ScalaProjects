package linkedlist

import org.scalacheck.Gen
import org.scalacheck.Prop.forAllNoShrink
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class LinkedListTest extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {
  val listGen = Gen.sized {size =>
    Gen.listOfN(size, Gen.posNum[Int])
  }
  var nonEmptyListGen = Gen.nonEmptyListOf(100)

  "A constructed linked-list" should
  "match the expectation." in {
    forAll(listGen) {
      (intList: List[Int]) => {
        val linkedList = LinkedList(intList: _*)
        linkedList.isEqualToList(intList)
        linkedList.isEmpty == intList.isEmpty
      }
    }
  }

  it should "match with usage of cons operator." in {
    forAll(listGen, Gen.size) {
      (intList: List[Int], x: Int) => {
        var linkedList = LinkedList(intList: _*)
        linkedList = x :: linkedList
        linkedList.isEqualToList(x :: intList)
      }
    }
  }

  it should "should match expected length." in {
    forAll(listGen) {
      (intList: List[Int]) => {
        val linkedList = LinkedList(intList: _*)
        linkedList.length == intList.length
      }
    }
  }

  it should "match head, tail, init last for non-empty lists." in {
    forAllNoShrink(nonEmptyListGen) {
      (intList: List[Int]) => {
        val linkedList = LinkedList(intList: _*)

        linkedList.head == intList.head
        linkedList.last == intList.last
        linkedList.tail.isEqualToList(intList.tail)
      }
    }
  }

  it should "work correctly with the append operator." in {
    forAll(listGen, listGen) {
      (xList: List[Int], yList: List[Int]) => {
        val xLinkedList = LinkedList(xList: _*)
        val yLinkedList = LinkedList(yList: _*)

        (xLinkedList ::: yLinkedList).isEqualToList(xList ::: yList)
      }
    }
  }

  it should "gets reversed correctly." in {
    forAll(listGen) {
      (xList: List[Int]) => {
        val xLinkedList = LinkedList(xList: _*)
        xLinkedList.reverse.isEqualToList(xList.reverse)
      }
    }
  }
}
