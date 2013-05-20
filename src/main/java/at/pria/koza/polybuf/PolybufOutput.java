/**
 * PolybufOutput.java
 * 
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf;


import java.util.IdentityHashMap;
import java.util.Map;

import at.pria.koza.polybuf.proto.Polybuf.Obj;


/**
 * <p>
 * {@code PolybufOutput}
 * </p>
 * 
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
public class PolybufOutput {
    private final Map<Object, Obj> objects;
    private final PolybufConfig    config;
    private int                    id = 0;
    
    public PolybufOutput(PolybufConfig config) {
        objects = new IdentityHashMap<>();
        this.config = config;
    }
    
    @SuppressWarnings("unchecked")
    public Obj writeObject(Object o) throws PolybufException {
        if(!(o instanceof PolybufSerializable)) throw new PolybufException("not serializable: " + o);
        
        PolybufSerializable object = (PolybufSerializable) o;
        int typeId = object.getTypeId();
        PolybufIO<Object> io = (PolybufIO<Object>) config.get(typeId);
        if(io == null) throw new PolybufException("No IO for type: " + typeId);
        
        Obj.Builder obj = Obj.newBuilder();
        
        //create a reference for future writeObject calls
        obj.setId(++id);
        objects.put(o, obj.build());
        
        //parse the actual object. allow objects to overwrite the type
        obj.setTypeId(typeId);
        io.serialize(this, object, obj);
        return obj.build();
    }
}
