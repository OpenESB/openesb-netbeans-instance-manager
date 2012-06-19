package org.openesb.netbeans.module.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.openesb.netbeans.module.constants.OpenESBInstanceConstants.*;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;

/**
 * Instance properties for OpenESB instance
 *
 * @author David BRASSELY
 */
public class OpenESBInstanceProperties extends HashMap<String,String> {
    
    private Sheet.Set set;
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public OpenESBInstanceProperties(String name, String host, String port, String username, String password) {
        put(INSTANCE_NAME, name);
        put(INSTANCE_HOST, host);
        put(INSTANCE_PORT, port);
        put(INSTANCE_USERNAME, username);
        put(INSTANCE_PASSWORD, password);
    }
    
    public OpenESBInstanceProperties(Map<String,String> properties) {
        super(properties);
    }

    @Override
    public synchronized String put(String key, String value) {
        String o = super.put(key, value);
        pcs.firePropertyChange(key, o, value);
        return o;
    }
    
    @Override
    public synchronized String remove(Object key) {
        String o = super.remove((String) key);
        pcs.firePropertyChange((String) key, o, null);
        return o;
    }

    public boolean isPersisted() {
        return true;
    }
    
    public Sheet.Set getSheetSet() {
        if (null == set) {
            set = Sheet.createPropertiesSet();
            
            // Set display name
            set.setDisplayName(get(INSTANCE_NAME));
            
            // Put properties in
            set.put(new PropertySupport<?>[] {
                new JBIInstanceProperty(INSTANCE_NAME,
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "TXT_Instance_Prop_Name"),
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "DESC_Instance_Prop_Name"),
                        true, false),
                        new JBIInstanceProperty(INSTANCE_HOST,
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "TXT_Instance_Prop_Url"),
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "DESC_Instance_Prop_Url"),
                        true, false),
                        new JBIInstanceProperty(INSTANCE_HOST,
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "TXT_Instance_Prop_Url"),
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "DESC_Instance_Prop_Url"),
                        true, false),
                        new PropertySupport<Integer>(INSTANCE_PORT, Integer.class,
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "TXT_Instance_Prop_Sync"),
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "DESC_Instance_Prop_Sync"),
                        true, true) {
                            public Integer getValue() {
                                return Integer.valueOf(get(INSTANCE_PORT));
                            }
                            public void setValue(Integer val) {
                                put(INSTANCE_PORT, val.toString());
                            }
                            public @Override boolean canWrite() {
                                return isPersisted();
                            }
                        },
                        new JBIInstanceProperty(INSTANCE_USERNAME,
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "TXT_Instance_Prop_Url"),
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "DESC_Instance_Prop_Url"),
                        true, false),
                        new JBIInstanceProperty(INSTANCE_PASSWORD,
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "TXT_Instance_Prop_Url"),
                        NbBundle.getMessage(OpenESBInstanceProperties.class, "DESC_Instance_Prop_Url"),
                        false, false)
            });
        }
        
        return set;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }
    
    public List<PropertyChangeListener> getCurrentListeners() {
        return Arrays.asList(pcs.getPropertyChangeListeners());
    }

    private class JBIInstanceProperty extends PropertySupport<String> {
        
        private String key;
        
        public JBIInstanceProperty(String key, String name, String desc, boolean read, boolean write) {
            super(key, String.class, name, desc, read, write);
            
            this.key = key;
        }
        
        @Override
        public void setValue(String value) {
            put(key, value);
        }
        
        @Override
        public String getValue() {
            return get(key);
        }
    }

    public static List<String> split(String prop) {
        return prop != null && prop.trim().length() > 0 ?
            Arrays.asList(prop.split("/")) : // NOI18N
            Collections.<String>emptyList();
    }

    public static String join(List<String> pieces) {
        StringBuilder b = new StringBuilder();
        for (String piece : pieces) {
            assert !piece.contains("/") : piece;
            if (b.length() > 0) {
                b.append('/');
            }
            b.append(piece);
        }
        return b.toString();
    }

}
