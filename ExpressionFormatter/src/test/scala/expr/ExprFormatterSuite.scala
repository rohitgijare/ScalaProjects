package expr

import org.scalatest.funsuite.AnyFunSuite
import layout.Element.elem

class ExprFormatterSuite extends AnyFunSuite {
  val exprFormatter = new ExprFormatter

  test("Var expr should generate a Line element with width = length and height = 1.") {
    val element = exprFormatter.format(Var("rohit"))
    val expected = elem("rohit")

    assert(element.contents sameElements  expected.contents)
    assert(element.height == expected.height)
    assert(element.width == expected.width)
  }

  test("Fractional number with decimal matches expectation.") {
    val element = exprFormatter.format(Number(2.22))
    val expected = elem("2.22")

    assert(element.contents sameElements  expected.contents)
    assert(element.height == expected.height)
    assert(element.width == expected.width)
  }

  test("Fractional number with trailing \".0\" is trimmed.") {
    val element = exprFormatter.format(Number(2.0))
    val expected = elem("2")

    assert(element.contents sameElements  expected.contents)
    assert(element.height == expected.height)
    assert(element.width == expected.width)
  }
}
