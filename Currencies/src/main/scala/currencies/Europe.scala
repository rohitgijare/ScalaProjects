package currencies

object Europe extends CurrencyZone {
  abstract class Euro extends AbstractCurrency {
    override def designation: String = "EUR"
  }

  override type Currency = Euro
  def make(cents: Long) = new Euro {
    override val amount: Long = cents
  }

  val Cent = make(1)
  val Euro = make(100)
  val CurrencyUnit = Euro
}
