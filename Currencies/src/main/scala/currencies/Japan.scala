package currencies

object Japan extends CurrencyZone {
  abstract class Yen extends AbstractCurrency {
    override def designation: String = "JPY"
  }

  override type Currency = Yen
  def make(yen: Long) = new Yen {
    override val amount: Long = yen
  }

  val Yen = make(1)
  val CurrencyUnit = Yen
}
