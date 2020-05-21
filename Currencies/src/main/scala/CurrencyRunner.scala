import currencies.{Europe, Japan, US}

object CurrencyRunner extends App {
  val jpy = Japan.Yen from US.Dollar * 100
  println(jpy)

  val eur = Europe.Euro from jpy
  println(eur)

  val usd = US.Dollar from eur
  println(usd)

  println(US.Dollar*100 + usd)
}
