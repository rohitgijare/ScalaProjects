package currencies

object US extends CurrencyZone {
  abstract class Dollar extends AbstractCurrency {
    def designation = "USD"
  }

  override type Currency = Dollar
  def make(cents: Long) = new Dollar {
    override val amount: Long = cents
  }

  val Cent = make(1)
  val Dollar = make(100)
  val CurrencyUnit = Dollar
}
