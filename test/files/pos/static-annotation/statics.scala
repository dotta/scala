import scala.annotation.static

// @static in object with companion class (synthetic companion class is generated)
object statics1 {
  @static
  val field = 1

  @static
  def method = 1
}

trait statics1

// @static in object with companion class (vars are allowed)
object statics2 {
  @static
  val field1 = 1

  @static
  var field2 = 1

  @static
  def method = 1
}

class statics2

// @static in object with companion trait (only @static vars are not allowed)
object statics3 {
  @static def method = 1
  @static val field1 = 1
  var field2 = 1 // this is ok because it's not @static
}

trait statics3

// nested object can declare @static members
object statics4 {
  object inner {
    @static def method = 1
    @static val field1 = 1
  }
  class inner
}
