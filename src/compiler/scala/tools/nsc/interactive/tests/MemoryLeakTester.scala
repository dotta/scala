package scala.tools.nsc.interactive.tests

import core._

import yourkit.YourKitProxy
import yourkit.memory.MemoryAnalyzer


/**
 * 
 * For each test created make sure to have a .javaopts file with the following
 * vm arguments:
 * 	
 * 	-Djava.awt.headless=true -agentlib:yjpagent=disablecounts,disablealloc,disablej2ee
 * 
 * If the above arguments are not declared, the test will be unable to create the 
 * profiler (which is used to take memory snapshots).
 */
class MemoryLeakTester
  extends AskParse
  with AskShutdown
  with AskReload
  with AskType
  with PresentationCompilerInstance
  with TestResources {

  /** The number of runs has a direct impact on the time needed to carry out the test. 
   *  However, you do want to maximize the number of runs since memory leaks will become 
   *  more apparent that way (retained objects that spawn several compilations will keep 
   *  growing). So, the actual number of compilation runs may have to be tuned depending 
   *  on the test case.*/
  protected val CompilationRuns = 50
  
  private lazy val yourKitProxy = new YourKitProxy()

  /** Test's entry point */
  def main(args: Array[String]) {
    runTest()

    askShutdown()
    sys.exit(0)
  }

  // classpath comes from partest
  settings.usejavacp.value = true
  
  /*settings.YpresentationDebug.value = true
  settings.YpresentationVerbose.value = true*/

  private def runTest() {
    val profiler = yourKitProxy.createProfiler()

    profiler.forceGC()
    
    // will be used as oracle for the test
    compileSources()
    val oracleFile = profiler.takeSnapshot()
    
    // trying to make evident existence of long-living objects, 
    // which may be evidence of memory leaks. 
    for (i <- 0 to CompilationRuns)
      compileSources()

    // result of the test that has to be compared with the oracle.
    val resultFile = profiler.takeSnapshot()

    val oracle = yourKitProxy.createMemorySnapshot(oracleFile)
    val result = yourKitProxy.createMemorySnapshot(resultFile)
    
    import yourkit.memory.CompilerMemoryFilterRuleDef._
    
    report(oracle, result)("HashEntry", hashEntries.toString)
    report(oracle, result)("RichCompilationUnits", richCompilationUnits.toString)
    report(oracle, result)("Types", types.toString)
    report(oracle, result)("Trees", trees.toString)
    report(oracle, result)("Symbols", symbols.toString)
  }
  
  private def report(oracle: MemoryAnalyzer, result: MemoryAnalyzer)(testName: String, xmlDescriptor: String) {
    reporter.println("oracle " + testName + " object count: "+oracle.getObjectCount(xmlDescriptor))
    reporter.println("result object " + testName + " count: "+result.getObjectCount(xmlDescriptor))
    
    reporter.println("oracle " + testName + " shallow size: "+oracle.getShallowSize(xmlDescriptor))
    reporter.println("result " + testName + " shallow size: "+result.getShallowSize(xmlDescriptor))
  }

  private def compileSources() {
    // force synchronous requests
    askReload(sourceFiles)(NullReporter).get
    askType(sourceFiles, true)(NullReporter) foreach (_.get)
  }
}