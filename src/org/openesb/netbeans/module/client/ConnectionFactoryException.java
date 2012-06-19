package org.openesb.netbeans.module.client;

/**
 *
 * @author David BRASSELY
 */
public class ConnectionFactoryException extends Exception {
    
    public ConnectionFactoryException(Throwable cause) {
        super(cause);
    }
    
    public ConnectionFactoryException(String message) {
        super(message);
    }
}
