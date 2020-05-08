package expr

import layout.Element.elem
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExprFormatterSpec extends AnyFlatSpec with Matchers {
  val exprFormatter = new ExprFormatter

  "A Var expr" should
    "generate a Line element with width = length and height = 1." in {
    val element = exprFormatter.format(Var("rohit"))
    val expected = elem("rohit")

    element.contents should be (expected.contents)
    element.height should be (expected.height)
    element.width should be (expected.width)
  }

  "A Fractional number" should
    "with decimal matches expectation." in {
    val element = exprFormatter.format(Number(2.22))
    val expected = elem("2.22")

    element.contents should be (expected.contents)
    element.height should be (expected.height)
    element.width should be (expected.width)
  }

  it should "trim if trailing is \".0\"." in {
    val element = exprFormatter.format(Number(2.0))
    val expected = elem("2")

    element.contents should be (expected.contents)
    element.height should be (expected.height)
    element.width should be (expected.width)
  }
}
