import simulation.CircuitSimulation

object MySimulation extends CircuitSimulation {
  override def InverterDelay: Int = 1

  override def AndGateDelay: Int = 3

  override def OrGateDelay: Int = 5
}

object MySimulationRunner extends App {
  val in1, in2, sum, carry = new MySimulation.Wire

  MySimulation.probe("sum", sum)
  MySimulation.probe("carry", carry)

  MySimulation.halfAdder(in1, in2, sum, carry)

  in1 setSignal true
  MySimulation.run()

  in2 setSignal true
  MySimulation.run()
}
