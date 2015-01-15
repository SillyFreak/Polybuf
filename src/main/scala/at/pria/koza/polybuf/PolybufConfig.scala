/**
 * PolybufConfig.scala
 *
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf

import scala.collection.mutable

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
  private val config = mutable.Map[Int, PolybufIO[_ <: PolybufSerializable]]()
  val registry: ExtensionRegistry = ExtensionRegistry.newInstance()

  def add(io: PolybufIO[_ <: PolybufSerializable]): Unit = {
    config.put(io.typeId, io)
    registry.add(io.extension)
  }

  def get(typeID: Int): Option[PolybufIO[_ <: PolybufSerializable]] =
    config.get(typeID)
}
