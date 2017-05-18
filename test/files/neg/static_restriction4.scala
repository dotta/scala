import scala.annotation.static

// test same fields in companion class
object static_restriction4 {
  @static def method = 1
  @static val field = 1
}

class static_restriction4 {
  def method = 1
  val field = 1
}

// test same fields in companion class
object static_restriction4bis {
  @static val field1 = 1
  @static val field2 = 1
  @static val field3 = 1
  @static def method1 = 1
  @static def method2(x: Int) = 1
}

class static_restriction4bis {
  def field1 = 1
  def field2() = 1
  def field3(x: Int) = 1
  val method1 = 1
  def method1(x: Int) = 1
  def method2(x: Int) = 1
}
