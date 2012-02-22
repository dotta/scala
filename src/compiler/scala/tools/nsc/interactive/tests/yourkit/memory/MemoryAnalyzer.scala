package scala.tools.nsc.interactive.tests.yourkit
package memory

/** 
 * Check YourKit API for a description of `MemorySnapshot`: 
 * 	@see http://www.yourkit.com/docs/95/api/index.html 
 * */
class MemoryAnalyzer private[yourkit] (memorySnapshot: AnyRef) 
	extends MethodInvoker {
 
  require(memorySnapshot != null)
  
  override protected val instance = memorySnapshot
  
  def getObjectCount(xmlSetDescriptor: String): Int =
    invoke[Int]("getObjectCount", classOf[String])(xmlSetDescriptor)
  
  def getShallowSize(xmlSetDescriptor: String): Long = 
    invoke[Long]("getShallowSize", classOf[String])(xmlSetDescriptor)
}

private[yourkit] object MethodAnalyzer {
  final val ClassName = "com.yourkit.api.MemorySnapshot"
}