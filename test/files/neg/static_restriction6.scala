import scala.annotation.static

object static_restriction6 {
  @static def method = 1
  @static val field1 = 1
  @static var field2 = 1
}

trait static_restriction6
