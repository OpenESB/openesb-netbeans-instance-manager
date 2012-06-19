package org.openesb.netbeans.module.api;

import java.util.EventListener;

/**
 * OpenESB instance change listener
 *
 * @author David BRASSELY
 */
public interface OpenESBChangeListener extends EventListener {

    /**
     * Change of the state
     */
    public void stateChanged();
    
    /**
     * Change of the content
     */
    public void contentChanged();
}
