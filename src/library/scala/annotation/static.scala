/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2003-2015, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.annotation

/** An annotation to compile object's members as static.  This is useful for
 *  interop with Java and other JVM languages, as well as with JavaScript, and
 *  is convenient for optimizations. For example:
 *  {{{
 *    object Utils {
 *      @static def isPositive(x: Int): Boolean = x >= 0
 *    }
 *  }}}
 *  The `isPositive` method will be compiled into a static method. The member can
 *  be accessed with the usual syntax, i.e., `Utils.isPositive`.
 *
 *  The following restrictions limits usage of the annotation:
 *
 *  1) Only objects can have members annotated with `@static`.
 *  2) The fields annotated with `@static` should preceed any non-`@static` fields.
 *     This ensures that we do not introduce surprises for users in initialization
 *     order of this class.
 *  3) The right hand side of a method or field annotated with `@static` can only refer
 *     to top-level classes, members of globally accessible objects and `@static` members.
 *     In particular, for non-static objects `this` is not accesible. `super` is never
 *     accessible.
 *  4) If a member `foo` of an `object C` is annotated with `@static`, the companion
 *     `class C` is not allowed to define term members with name `foo`.
 *  5) If a member `foo` of an `object C` is annotated with `@static`, the companion
 *     `class C` is not allowed to inherit classes that define a term member with name
 *     `foo`.
 *  6) Only `@static` methods and vals are supported in companions of traits. Java8 supports
 *     those, but not vars, and JavaScript does not have interfaces at all.
 *
 *  @since 2.12.3
 */
final class static extends scala.annotation.StaticAnnotation
