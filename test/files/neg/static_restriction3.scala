import annotation.static

// object nested in a class can't declare @static members
class static_restriction3_nested1 {
  object inner {
    @static def method = 1
    @static val field1 = 1
    @static var field2 = 1
  }
}

// object nested in a trait can't declare @static members
trait static_restriction3_nested2 {
  object inner {
    @static def method = 1
    @static val field1 = 1
    @static var field2 = 1
  }
}

// object nested in a member can't declare @static members
object static_restriction3_nested3 {
  def foo = {
    object nested {
      @static def method = 1
      @static val field1 = 1
      @static var field2 = 1
    }
    ()
  }
}
