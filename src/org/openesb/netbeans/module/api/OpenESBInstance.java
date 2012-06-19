package org.openesb.netbeans.module.api;

import java.util.prefs.Preferences;
import javax.management.MBeanServerConnection;
import org.netbeans.modules.sun.manager.jbi.management.AppserverJBIMgmtController;

/**
 * Instance of the OpenESB server
 *
 * @author David BRASSELY
 */
public interface OpenESBInstance extends Comparable<OpenESBInstance> {
    
    /**
     * Name of the OpenESB instance
     *
     * @return Instance name
     */
    String getName();
    
    /**
     * Returns state of the OpenESB instance
     * 
     * @return true if the instance is connected
     */
    boolean isConnected();
    
    /**
     * Hostname of the OpenESB instance
     *
     * @return Instance hostname
     */
    String getHost();
    
    OpenESBInstanceKey getKey();
    
    /**
     * Per-instance preferences.
     * @return preferences for various customizations
     */
    Preferences prefs();
    
    AppserverJBIMgmtController getConnection();
}
