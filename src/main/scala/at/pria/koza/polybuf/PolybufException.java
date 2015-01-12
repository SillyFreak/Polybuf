/**
 * PolybufException.java
 * 
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf;


import java.io.IOException;


/**
 * <p>
 * {@code PolybufException}
 * </p>
 * 
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
public class PolybufException extends IOException {
    private static final long serialVersionUID = -2257893361939230762L;
    
    public PolybufException() {}
    
    public PolybufException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PolybufException(String message) {
        super(message);
    }
    
    public PolybufException(Throwable cause) {
        super(cause);
    }
}
