/**
 * PolybufSerializable.scala
 *
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf

/**
 * <p>
 * `PolybufSerializable` marks a class as serializable by the `PolybufOutput` and `PolybufInput` classes. A
 * `PolybufSerializable` class provides a type id via `getTypeId()` that corresponds to the protobuf extension
 * field id. For example, consider this message:
 * </p>
 *
 * {{{
 * message Example {
 *     extend Obj {
 *         optional Example example = 100;
 *     }
 *
 *     optional string value = 1;
 * }
 * }}}
 *
 * <p>
 * The `Example` message declares the `Obj`-extension `example = 100`, thus its ID is 100. Protobuf requires that
 * extension field labels are unique, thus the number 100 unambiguously refers to the `Example` message.
 * </p>
 * <p>
 * This example shows how a certain `PolybufSerializable`'s state will be serialized: the `Example` java object
 * that has a `value` field will be converted to an `Example` message by its corresponding `PolybufIO`. That
 * message will then be stored in `Obj`'s `example` extension field. A subclass of `Example` will declare its own
 * message type that contains only those fields declared in the class, not any inherited fields. The serialized
 * form will have two extensions set, and the `PolybufIO` code for `Example` can be reused by the subclass'
 * `PolybufIO`.
 * </p>
 *
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
trait PolybufSerializable {
  /**
   * <p>
   * Returns the corresponding protobuf extension field ID, as explained above.
   * </p>
   */
  def getTypeId(): Int;
}
