import scala.annotation.static

object static_restriction2 {
  @static def method = 1

  val field1 = 1

  @static val field2 = 1
}

class static_restriction2
