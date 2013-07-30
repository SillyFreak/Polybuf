/**
 * PolybufConfig.java
 * 
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf;


import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * A {@code PolybufConfig} encapsulates {@link PolybufIO}s for different object types, each identified by an
 * {@code int} that corresponds to the protobuf extension field label associated with that type (see
 * {@link PolybufSerializable}). Note that this class does not have a notion of what message type a
 * {@code PolybufIO} corresponds to, and thus does not enforce anything of the sort. Implementors are free to
 * design {@code PolybufIO} classes that work for any number and any type of messages. The user simply has to
 * register that {@code PolybufIO} with all the messages he wants.
 * </p>
 * 
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
public class PolybufConfig {
    private final Map<Integer, PolybufIO<?>> config;
    
    public PolybufConfig() {
        config = new HashMap<>();
    }
    
    public void put(int type, PolybufIO<?> io) {
        config.put(type, io);
    }
    
    public void remove(int type) {
        config.remove(type);
    }
    
    @SuppressWarnings("unchecked")
    public <T> PolybufIO<T> get(int type) {
        return (PolybufIO<T>) config.get(type);
    }
}
