package scala.tools.nsc.interactive.tests.yourkit

import memory._
import profiler._

/** Proxy for YourKit API.
 *
 *  Classes and methods are accessed via reflection to avoid dependencies on
 *  YourKit libraries during compilation.
 *  
 *  Ideally all classes in `yourkit` package should be moved out of the compiler, 
 *  in a separate project that will execute all profiling tests after the Scala 
 *  compiler is compiled (i.e., before the distribution is created).
 *  This will be a lot easier once `sbt` will replace `ant`.
 */
private[tests] class YourKitProxy {

  /** Creates a your kit memory profiler */
  def createProfiler(): Profiler = {
    val controllerClazz = createController()
    new Profiler(createProfiler(controllerClazz))
  }

  private def createController(): Class[_] =
    classFor(Profiler.ClassName)

  private def createProfiler(controllerClazz: Class[_]): AnyRef = {
    try {
      controllerClazz.newInstance().asInstanceOf[AnyRef]
    } catch {
      case e => error("Failed to create YourKit Profiler instance due to: " + e.getMessage());
    }
  }

  /** A `snapshotFile` can be created via `Profiler#takeSnapshot()`. This method allows to load
   *  the file in memory to perform analysis (such as counting objects).
   */
  def createMemorySnapshot(snapshotFile: String): MemoryAnalyzer = {
    val file = new java.io.File(snapshotFile)
    assert(file.isFile)
    val clazz = classFor(MethodAnalyzer.ClassName)
    try {
      // looking for `MemorySnapshot(java.io.File snapshotFile)` constructor
      val instance = instantiate(clazz, Array(classOf[java.io.File]))(file)
      assert(instance != null)
      // finally return the adapter that reflectively calls method's on the above created instance
      new MemoryAnalyzer(instance)
    } catch {
      case e => error("Failed to create YourKit MemorySnapshot instance due to: " + e.getMessage())
    }
  }

  private def instantiate(clazz: java.lang.Class[_], paramsTypes: Array[Class[_]])(args: AnyRef*): AnyRef = {
    val constructor = clazz.getConstructors().find(_.getParameterTypes() sameElements paramsTypes)
    assert(constructor.isDefined)
    try {
      constructor.get.newInstance(args: _*).asInstanceOf[AnyRef]
    } catch {
      case e =>
        e.printStackTrace()
        error("Failed to create instance due to: "+e.getMessage)
      
    }
  }

  private def classFor(className: String): Class[_] = {
    try {
      Class.forName(className)
    } catch {
      case e: ClassNotFoundException =>
        error("Cannot find class `" + className + "`. " +
          "Hint: Did you make sure that YourKit libraries are in the classpath?")
    }
  }
}