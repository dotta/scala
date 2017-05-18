import scala.annotation.static

// @static members are only allowed in objects
class static_restriction1 {
  @static val field = 1

  @static def method = 1
}
