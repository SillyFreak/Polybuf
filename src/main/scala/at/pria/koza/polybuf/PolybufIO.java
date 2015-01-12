/**
 * PolybufIO.java
 * 
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf;


import at.pria.koza.polybuf.proto.Polybuf.Obj;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessage.GeneratedExtension;


/**
 * <p>
 * The {@code PolybufIO} interface is used by {@link PolybufOutput} and {@link PolybufInput} to implement
 * conversion between {@link PolybufSerializable} and {@link Obj} instances. See the
 * {@link #serialize(PolybufOutput, PolybufSerializable, net.slightlymagic.ticTacToe.proto.Objects.Obj.Builder)
 * serialize}, {@link #initialize(PolybufInput, Obj) initialize} and
 * {@link #deserialize(PolybufInput, Obj, PolybufSerializable) deserialize} methods for details.
 * </p>
 * 
 * @version V1.0 20.05.2013
 * @author SillyFreak
 */
public interface PolybufIO<T> {
    /**
     * <p>
     * Returns this IO's type's type ID. This can be retrieved like this:
     * </p>
     * <p>
     * {@link PolybufIO#getExtension() getExtension()}{@code .} {@link GeneratedExtension#getDescriptor()
     * getDescriptor()}{@code .} {@link FieldDescriptor#getNumber() getNumber()}
     * </p>
     * 
     * @return this IO's type's type ID
     */
    public int getType();
    
    /**
     * <p>
     * Returns the extension used by the type of this IO to store type data in protobuf messages. This is primarily
     * used in {@link PolybufConfig#add(PolybufIO)} to register the extension in the {@link ExtensionRegistry}.
     * </p>
     * 
     * @return the extension used by this IO
     */
    public GeneratedExtension<Obj, ?> getExtension();
    
    /**
     * <p>
     * Serializes {@code object} into the given {@link Obj}. Usually, this will create one or more messages that
     * are stored as extensions in the {@code Obj}. Multiple extensions usually mean that the object has superclass
     * data that is also serialized.
     * </p>
     * 
     * @param out The {@link PolybufOutput} doing serialization
     * @param object The object to serialize
     * @param obj The {@link Obj} to serialize to
     * @throws PolybufException if anything goes wrong
     */
    public void serialize(PolybufOutput out, T object, Obj.Builder obj) throws PolybufException;
    
    /**
     * <p>
     * Returns an object as the result of deserialization. This method should not call
     * {@link PolybufInput#readObject(Obj) readObject}, because at the time {@code initialize} is called, the
     * resulting object can't be referenced by other objects to be deserialized. If {@code readObject} is called,
     * this method must be sure that any objects to be recursively deserialized don't reference this one.
     * </p>
     * 
     * @param in The {@link PolybufInput} doing deserialization
     * @param obj The {@link Obj} to deserialize from
     * @return A possibly unfinished instance that is the result of deserialization
     * @throws PolybufException if anything goes wrong
     */
    public T initialize(PolybufInput in, Obj obj) throws PolybufException;
    
    /**
     * <p>
     * Finishes deserialization into the given object. When this method is called, the instance will already be
     * stored in the {@link PolybufInput}, so {@link PolybufInput#readObject(Obj) readObject} may be called freely.
     * </p>
     * 
     * @param in The {@link PolybufInput} doing deserialization
     * @param obj The {@link Obj} to deserialize from
     * @param object The object to serialize to
     * @throws PolybufException if anything goes wrong
     */
    public void deserialize(PolybufInput in, Obj obj, T object) throws PolybufException;
}
