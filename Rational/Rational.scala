class Rational(n: Int, d: Int) {
    require(d != 0)
    private val gcd: Int = gcd(n, d)
    val numer: Int = n / gcd
    val denom: Int = d / gcd

    def this(n: Int) = this(n, 1)

    override def toString(): String = s"$numer/$denom"

    def + (that: Rational) = 
    new Rational(
        numer * that.denom + that.numer * denom, 
        denom * that.denom)

    def + (i: Int) = new Rational(numer + denom, denom)

    private def gcd(n1: Int, n2: Int) : Int = if (n2 == 0) n1 else gcd(n2, n1 % n2)
}
