import queue.{Queue, StrangeIntQueue}

object QueueRunner extends App {
  val q = Queue(1.1, 2.2, 3.3, 4.4, 5.5)
  println(q.head)

  val intQ = new StrangeIntQueue(List(1, 2, 3))
  println(intQ.head)
}
