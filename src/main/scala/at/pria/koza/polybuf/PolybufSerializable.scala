/**
 * PolybufSerializable.java
 *
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf

/**
 * <p>
 * {@code PolybufSerializable} marks a class as serializable by the {@link PolybufOutput} and {@link PolybufInput}
 * classes. A {@code PolybufSerializable} class provides a {@linkplain #getTypeId() type id} that corresponds to
 * the protobuf extension field id. For example, consider this message:
 * </p>
 *
 * <pre>
 * message Example {
 *     extend Obj {
 *         optional Example example = 100;
 *     }
 *
 *     optional string value = 1;
 * }
 * </pre>
 *
 * <p>
 * The {@code Example} message declares the {@code Obj}-extension {@code example = 100}, thus its ID is 100.
 * Protobuf requires that extension field labels are unique, thus the number 100 unambiguously refers to the
 * {@code Example} message.
 * </p>
 * <p>
 * This example shows how a certain {@code PolybufSerializable}'s state will be serialized: the {@code Example}
 * java class that has a {@code value} field will be converted to an {@code Example} message by its corresponding
 * {@link PolybufIO}. That message will then be stored in {@code Obj}'s {@code example} extension field. A subclass
 * of {@code Example} will declare its own message type that contains only those fields declared in the class, not
 * any inherited fields. The serialized form will have two extensions set, and the {@link PolybufIO} code for
 * {@code Example} can be reused by the subclass' {@link PolybufIO}.
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
