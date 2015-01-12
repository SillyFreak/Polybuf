/**
 * PolybufException.scala
 *
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf

import java.io.IOException

/**
 * <p>
 * `PolybufException`
 * </p>
 *
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
class PolybufException(msg: String, cause: Throwable) extends IOException(msg, cause) {
  def this() = this(null, null)
  def this(msg: String) = this(msg, null)
  def this(cause: Throwable) = this(if (cause == null) null else cause.toString(), cause)
}
