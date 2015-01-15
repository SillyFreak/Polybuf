/**
 * PolybufIO.scala
 *
 * Created on 12.01.2015
 */
package at.pria.koza.polybuf

import com.google.protobuf.GeneratedMessage.GeneratedExtension
import at.pria.koza.polybuf.proto.Polybuf.Obj

/**
 * <p>
 * The `PolybufIO` trait is used by `PolybufOutput` and `PolybufInput` to implement conversion between
 * `PolybufSerializable` and `Obj` instances. See the `serialize`, `initialize`, and `deserialize` methods for
 * details.
 * </p>
 *
 * @version V1.0 20.05.2013
 * @author SillyFreak
 */
trait PolybufIO[T <: PolybufSerializable] {
  type TT = T

  /**
   * <p>
   * Returns this IO's type's type ID. This can be retrieved like this:
   * </p>
   * {{{
   * getExtension().getDescriptor().getNumber()
   * }}}
   *
   * @return this IO's type's type ID
   */
  def typeId = extension.getDescriptor.getNumber

  /**
   * <p>
   * Returns the extension used by the type of this IO to store type data in protobuf messages. This is primarily
   * used in `PolybufConfig.add(PolybufIO)` to register the extension in the `ExtensionRegistry`.
   * </p>
   *
   * @return the extension used by this IO
   */
  def extension: GeneratedExtension[Obj, _]

  /**
   * <p>
   * Serializes `instance` into the given `Obj`. Usually, this will create one or more messages that are stored as
   * extensions in the `Obj`. Multiple extensions usually mean that the object has superclass data that is also
   * serialized.
   * </p>
   *
   * @param out The `PolybufOutput` doing serialization
   * @param instance The object to serialize
   * @param obj The `Builder` to serialize to
   * @throws PolybufException if anything goes wrong
   */
  @throws[PolybufException]("if anything goes wrong")
  def serialize(out: PolybufOutput, instance: TT, obj: Obj.Builder): Unit

  /**
   * <p>
   * Returns an object as the result of deserialization. This method should not call
   * `PolybufInput.readObject(Obj)`, because at the time `initialize` is called, the resulting object can't be
   * referenced by other objects to be deserialized. If `readObject` is called, this method must be sure that any
   * objects to be recursively deserialized don't reference this one.
   * </p>
   *
   * @param in The `PolybufInput` doing deserialization
   * @param obj The `Obj` to deserialize from
   * @return A possibly unfinished instance that is the result of deserialization
   * @throws PolybufException if anything goes wrong
   */
  @throws[PolybufException]("if anything goes wrong")
  def initialize(in: PolybufInput, obj: Obj): TT

  /**
   * <p>
   * Finishes deserialization into the given object. When this method is called, the instance will already be
   * stored in the `PolybufInput`, so `PolybufInput.readObject(Obj)` may be called freely.
   * </p>
   *
   * @param in The `PolybufInput` doing deserialization
   * @param obj The `Obj` to deserialize from
   * @param instance The object to serialize to
   * @throws PolybufException if anything goes wrong
   */
  @throws[PolybufException]("if anything goes wrong")
  def deserialize(in: PolybufInput, obj: Obj, instance: TT): Unit = {}
}
