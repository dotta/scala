package scala.tools.nsc.interactive.tests.yourkit.memory

object CompilerMemoryFilterRuleDef {
  lazy val hashEntries = retainedObjectsRule("scala.collection.mutable.HashEntry")
  lazy val richCompilationUnits = retainedObjectsRule("scala.tools.nsc.interactive.RichCompilationUnits$RichCompilation")
  lazy val types = retainedObjectsRule("scala.reflect.internal.Type*")
  lazy val trees = retainedObjectsRule("scala.reflect.internal.Trees$*")
  lazy val symbols = retainedObjectsRule("scala.reflect.internal.Symbols$*")
  
  /*def retainedObjectsRule(classNamePattern: String): xml.Elem = 
    <retained-objects><objects class={classNamePattern}/></retained-objects>*/
  
  def retainedObjectsRule(classNamePattern: String): xml.Elem =
    <objects class={classNamePattern}/>
}