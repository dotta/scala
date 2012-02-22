package scala.tools.nsc.interactive.tests.yourkit

private[yourkit] trait MethodInvoker {
  
  protected def instance: AnyRef
    
  protected def invoke[T](methodName: String, paramsTpes: Class[_]*)(args: AnyRef*): T = {
    assert(paramsTpes.size == args.size)
    
    try {
      if(paramsTpes.isEmpty) {
        val method = instance.getClass().getMethod(methodName)
        method.invoke(instance).asInstanceOf[T]
      }
      else {
        val method = instance.getClass().getMethod(methodName, paramsTpes : _*)
      	method.invoke(instance, args : _*).asInstanceOf[T]
      }
    } 
    catch {
      case e =>
        e.printStackTrace()
        error("Failed invocation of " + e.getMessage());
    }
  }
}