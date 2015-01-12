/**
 * PolybufConfig.scala
 *
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf

import java.util.HashMap
import java.util.Map
import com.google.protobuf.ExtensionRegistry

/**
 * <p>
 * A `PolybufConfig` encapsulates `PolybufIO`s for different object types, each identified by an `Int` that
 * corresponds to the protobuf extension field label associated with that type (see `PolybufSerializable`). Note
 * that this class does not have a notion of what message type a `PolybufIO` corresponds to, and thus does not
 * enforce anything of the sort. Implementors are free to design `PolybufIO` classes that work for any number and
 * any type of messages. The user simply has to register that `PolybufIO` with all the messages they want.
 * </p>
 *
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
class PolybufConfig() {
  private val _config: Map[Int, PolybufIO[_]] = new HashMap()
  private val _registry: ExtensionRegistry = ExtensionRegistry.newInstance()

  def getRegistry(): ExtensionRegistry = _registry

  def add(io: PolybufIO[_]): Unit = {
    _config.put(io.getType(), io)
    _registry.add(io.getExtension())
  }

  def get[T](typeID: Int): PolybufIO[T] =
    _config.get(typeID).asInstanceOf[PolybufIO[T]]
}
