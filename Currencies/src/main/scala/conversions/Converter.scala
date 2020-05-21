package conversions

object Converter {
  var exchangeRate = Map(
    "USD" -> Map("USD" -> 1.0, "EUR" -> 0.7596, "JPY" -> 1.211),
    "EUR" -> Map("USD" -> 1.316, "EUR" -> 1.0, "JPY" -> 1.594),
    "JPY" -> Map("USD" -> 0.8257, "EUR" -> 0.6272, "JPY" -> 1.0)
  )
}
