import scala.annotation.static

// disallow to inherit members with same name in companion class
object static_restriction5 {
  @static def method = 1
  @static val field = 1
}

trait top {
  def method: Int = 1
  val field: Int = 1
}

class static_restriction5 extends top

// disallow @static implementation of inherited members
object static_restriction5bis extends top {
  @static override def method = 1
  @static override val field = 1
}

class static_restriction5bis
