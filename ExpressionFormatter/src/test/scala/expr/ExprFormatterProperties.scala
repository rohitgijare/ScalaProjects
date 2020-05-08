package expr

import org.scalacheck.Properties
import org.scalacheck.Prop.{forAll, propBoolean}

object ExprFormatterProperties extends Properties("ExprFormatter") {
  val exprFormatter = new ExprFormatter

  property("Var expr is same as the variable name") = forAll { (a: String) => !a.isEmpty ==>
    (exprFormatter.format(Var(a)).contents(0) == a)
  }

  property("Number expression is the double value stringified") = forAll { (d: Double) =>
    exprFormatter.format(Number(d)).contents(0) == d.toString
  }
}
