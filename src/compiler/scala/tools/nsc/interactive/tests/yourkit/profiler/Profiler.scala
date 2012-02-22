package scala.tools.nsc.interactive.tests.yourkit
package profiler

/** 
 * Check YourKit API for a description of `Controller`: 
 * 	@see http://www.yourkit.com/docs/95/api/index.html 
 * */
class Profiler private[yourkit] (profiler: AnyRef) 
	extends MethodInvoker {
  
  require(profiler != null)

  override protected val instance = profiler
  
  /** First force garbage collection of heap's objects and then take a
   *  snapshot of the whole memory.
   *  Taking the memory snapshot has the side-effect of incrementing the
   *  generation number of newly created objects. This makes it easier
   *  to compare long-living objects, as they might indicate memory leaks.
   */
  def takeSnapshot(): String = {
    try {
      forceGC()
      captureMemorySnapshot()
    } catch {
      case e => error("Profiler was active, but failed due: " + e.getMessage());
    }
  }

  private def captureMemorySnapshot(): String =
    invoke[String]("captureMemorySnapshot")()

  def forceGC() { invoke[String]("forceGC")() }
}

private[yourkit] object Profiler {
  final val ClassName = "com.yourkit.api.Controller"
}