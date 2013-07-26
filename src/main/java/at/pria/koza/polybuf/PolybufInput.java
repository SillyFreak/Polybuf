/**
 * PolybufInput.java
 * 
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf;


import java.util.HashMap;
import java.util.Map;

import at.pria.koza.polybuf.proto.Polybuf.Obj;


/**
 * <p>
 * {@code PolybufInput}
 * </p>
 * 
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
public class PolybufInput {
    private final Map<Integer, Object> objects;
    private final PolybufConfig        config;
    
    public PolybufInput(PolybufConfig config) {
        objects = new HashMap<>();
        this.config = config;
    }
    
    public Object readObject(Obj obj) throws PolybufException {
        int typeId = obj.getTypeId();
        if(typeId == 0) {
            int id = obj.getId();
            if(id == 0) {
                return null;
            } else {
                Object object = objects.get(id);
                if(object == null) {
                    throw new PolybufException("Unknown object: " + obj.getClass().getName() + ":" + id);
                }
                return object;
            }
        }
        
        PolybufIO<Object> io = config.get(typeId);
        if(io == null) throw new PolybufException("No IO for type: " + typeId);
        Object object = io.initialize(this, obj);
        objects.put(obj.getId(), object);
        io.deserialize(this, obj, object);
        
        return object;
    }
}
