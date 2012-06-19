package org.openesb.netbeans.module.impl;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.netbeans.modules.sun.manager.jbi.management.AppserverJBIMgmtController;
import org.openesb.netbeans.module.api.OpenESBInstanceKey;
import static org.openesb.netbeans.module.constants.OpenESBInstanceConstants.*;
import org.openesb.netbeans.module.api.OpenESBInstance;
import org.openide.util.RequestProcessor;

/**
 * Implementation of the OpenESBInstance
 *
 * @author David BRASSELY
 */
public class OpenESBInstanceImpl implements OpenESBInstance {

    private static final Logger LOG = Logger.getLogger(OpenESBInstanceImpl.class.getName());
    private static final RequestProcessor RP = new RequestProcessor(OpenESBInstanceImpl.class.getName(),
            // Permit concurrent connections to several servers; semaphore serializes per server.
            10);
    private OpenESBInstanceProperties properties;
    private boolean connected;

    private OpenESBInstanceImpl(OpenESBInstanceProperties properties) {
        this.properties = properties;
    }

    public boolean isPersisted() {
        return properties.isPersisted();
    }

    public void makePersistent() {
        if (isPersisted()) {
            return;
        }
        String name = properties.get(INSTANCE_NAME);
        String host = properties.get(INSTANCE_HOST);
        String port = properties.get(INSTANCE_PORT);
        String username = properties.get(INSTANCE_USERNAME);
        String password = properties.get(INSTANCE_PASSWORD);

        OpenESBInstanceProperties newProps = new OpenESBInstanceProperties(name, host, port, username, password);
        //just in case there are also other properties.
        for (Map.Entry<String, String> ent : properties.entrySet()) {
            newProps.put(ent.getKey(), ent.getValue());
        }

        //reassign listeners
        List<PropertyChangeListener> list = properties.getCurrentListeners();
        for (PropertyChangeListener listener : list) {
            newProps.addPropertyChangeListener(listener);
            properties.removePropertyChangeListener(listener);
        }
        properties = newProps;

        storeDefinition();
    }

    void storeDefinition() {
        if (!isPersisted()) {
            return;
        }
        Preferences node = prefs();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            node.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Preferences prefs() {
        return OpenESBManagerImpl.instancePrefs().node(OpenESBManagerImpl.simplifyServerLocation(getName(), true));
    }

    public static OpenESBInstanceImpl createJBIInstance(String name, String host, String port, String username, String password) {
        return createJBIInstance(new OpenESBInstanceProperties(name, host, port, username, password));
    }

    public static OpenESBInstanceImpl createJBIInstance(OpenESBInstanceProperties properties) {
        OpenESBInstanceImpl instance = new OpenESBInstanceImpl(properties);
        assert instance.getName() != null;
        assert instance.getProperties().get(INSTANCE_HOST) != null;
        assert Integer.parseInt(instance.getProperties().get(INSTANCE_PORT)) >= 0;

        if (null == OpenESBManagerImpl.getDefault().addInstance(instance)) {
            return null;
        }

        return instance;
    }

    public void terminate() {
        // Clear all
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    public OpenESBInstanceProperties getProperties() {
        return properties;
    }

    public String getName() {
        return getProperties().get(INSTANCE_NAME);
    }

    public String getHost() {
        return getProperties().get(INSTANCE_HOST);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OpenESBInstance)) {
            return false;
        }

        final OpenESBInstance other = (OpenESBInstance) obj;

        if (getKey() != other.getKey()
                && (getKey() == null || !getKey().equals(other.getKey()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return getKey() == null ? 445 : getKey().hashCode();
    }

    public @Override
    String toString() {
        return getKey().toString();
    }

    public int compareTo(OpenESBInstance o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public AppserverJBIMgmtController getConnection() {
        
        try {
        //    ManagementClientFactory.getInstance("localhost", 8686, "admin", "").
        } catch (Exception e) {
            return null;
        }
        
        final Map<String, Object> environment = new HashMap<String, Object>();
        
        
        final String PKGS = "com.sun.enterprise.admin.jmx.remote.protocol";

        try {

            environment.put(JMXConnectorFactory.PROTOCOL_PROVIDER_CLASS_LOADER,
                    getClass().getClassLoader());
            environment.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, PKGS);

            JMXServiceURL jmxUrl = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + "localhost" + ":" + "8686" + "/jmxrmi");
            environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", ""});
            environment.put("USER", "admin");
            environment.put("PASSWORD", "");
            environment.put("com.sun.enterprise.as.http.auth", "BASIC");

            JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxUrl, environment);
            MBeanServerConnection jmxMBeanServerCon = jmxConnector.getMBeanServerConnection();

            AppserverJBIMgmtController controller = new AppserverJBIMgmtController(jmxMBeanServerCon);
            return controller;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OpenESBInstanceKey getKey() {
        return new OpenESBInstanceKey(
                getProperties().get(INSTANCE_HOST),
                getProperties().get(INSTANCE_PORT));
    }
}
