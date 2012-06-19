package org.openesb.netbeans.module.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.openesb.netbeans.module.api.OpenESBChangeListener;
import static org.openesb.netbeans.module.constants.OpenESBInstanceConstants.*;
import org.openesb.netbeans.module.api.OpenESBInstance;
import org.openesb.netbeans.module.api.OpenESBInstanceKey;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;

/**
 * Implementation of the OpenESB manager
 *
 * @author David BRASSELY
 */
public class OpenESBManagerImpl {

    private static final RequestProcessor RP = new RequestProcessor(OpenESBManagerImpl.class);
    
    /** The only instance of the OpenESB manager implementation in the system */
    private static OpenESBManagerImpl defaultInstance;
    
    private final List<OpenESBChangeListener> listeners = new ArrayList<OpenESBChangeListener>();
    
    private Map<OpenESBInstanceKey, OpenESBInstanceImpl> instances;
    
    private OpenESBManagerImpl() {

    }
    
    /**
     * Singleton accessor
     *
     * @return instance of the OpenESB manager implementation
     */
    public static synchronized OpenESBManagerImpl getDefault() {
        if (defaultInstance == null) {
            defaultInstance = new OpenESBManagerImpl();
        }
        return defaultInstance;
    }
    
    public OpenESBInstanceImpl addInstance(final OpenESBInstanceImpl instance) {
        if (null == instance || null != getInstancesMap().get(instance.getKey()))
            return null;
        
        if (null != getInstancesMap().put(instance.getKey(), instance))
            return null;
        
        fireChangeListeners();
        
        instance.storeDefinition();
        
        instance.getProperties().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                instance.storeDefinition();
            }
        });
        
        return instance;
    }
    
    public OpenESBInstanceImpl removeInstance(OpenESBInstanceImpl instance) {
        if (null == instance || null == getInstancesMap().get(instance.getHost()))
            return null;
        
        if (null == getInstancesMap().remove(instance.getHost()))
            return null;
        
        // Stop autosynchronization if it's running
        instance.terminate();
        
        // Fire changes into all listeners
        fireChangeListeners();
        
        // Remove instance file
        removeInstanceDefinition(instance);
        
        return instance;
    }
    
    public OpenESBInstanceImpl getInstance(String hostname, String port) {
        return getInstancesMap().get(
                new OpenESBInstanceKey(hostname, port));
    }
    
    public synchronized Collection<OpenESBInstanceImpl> getInstances() {
        return Arrays.asList(getInstancesMap().values().toArray(new OpenESBInstanceImpl[] {}));
    }
    
    public OpenESBInstance getInstanceByName(String name) {
        for (OpenESBInstance i : getInstances()) {
            if (i.getName().equals(name))
                return i;
        }
        
        return null;
    }
    
    public void addOpenESBChangeListener(OpenESBChangeListener l) {
        listeners.add(l);
    }
    
    public void removeOpenESBChangeListener(OpenESBChangeListener l) {
        listeners.remove(l);
    }
    
    private void fireChangeListeners() {
        ArrayList<OpenESBChangeListener> tempList;
        
        synchronized (listeners) {
            tempList = new ArrayList<OpenESBChangeListener>(listeners);
        }
        
        for (OpenESBChangeListener l : tempList) {
            l.stateChanged();
            l.contentChanged();
        }
    }
    
    public void terminate() {
        // Clear default instance
        defaultInstance = null;
        // Terminate instances
        for (OpenESBInstance instance : getInstances())
            ((OpenESBInstanceImpl) instance).terminate();
    }

    static Preferences instancePrefs() {
        return NbPreferences.forModule(OpenESBManagerImpl.class).node("instances"); // NOI18N
    }
    
    public static String simplifyServerLocation(String name, boolean forKey) {
        // http://deadlock.netbeans.org/hudson/ => deadlock.netbeans.org_hudson
        String display = name.replaceFirst("https?://", "").replaceFirst("/$", "");
        return forKey ? display.replaceAll("[/:]", "_") : display; // NOI18N
    }
    
    private void removeInstanceDefinition(OpenESBInstanceImpl instance) {
        try {
            instance.prefs().removeNode();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    private Map<OpenESBInstanceKey, OpenESBInstanceImpl> getInstancesMap() {
        if (null == instances) {
            instances = new HashMap<OpenESBInstanceKey, OpenESBInstanceImpl>();
            
            // initialization
            init();
        }
        
        return instances;
    }

    private void init() {
        RP.post(new Runnable() {
            public void run() {
                try {
                    try {
                        for (String kid : instancePrefs().childrenNames()) {
                            Preferences node = instancePrefs().node(kid);
                            Map<String, String> m = new HashMap<String, String>();
                            for (String k : node.keys()) {
                                m.put(k, node.get(k, null));
                            }
                            if (!m.containsKey(INSTANCE_NAME) || !m.containsKey(INSTANCE_HOST) || !m.containsKey(INSTANCE_PORT) || !m.containsKey(INSTANCE_USERNAME) || !m.containsKey(INSTANCE_PASSWORD)) {
                                continue;
                            }
                            OpenESBInstanceImpl.createJBIInstance(new OpenESBInstanceProperties(m));
                        }
                    } catch (BackingStoreException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } finally {
                    // Fire changes
                    fireChangeListeners();
                }
            }
        });
    }

}
