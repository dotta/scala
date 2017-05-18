import scala.annotation.static

// @static lazy vals are not permitted
// Rationale is that dotty @static implementation doesn't support @static lazy fields, and I'm thinking Scala
// may want to adopt dotty's lazy encoding in the future. Hence the reason for having the same limitations for
// @static members.
object static_limitations1 {
  @static lazy val field = 1
}

class static_limitations1

// if an object declares @static members, a companion class must always exist
object static_limitations2 {
  @static val field = 1
}
