package org.openesb.netbeans.module.ui.nodes;

import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.netbeans.modules.sun.manager.jbi.management.AppserverJBIMgmtController;
import org.netbeans.modules.sun.manager.jbi.nodes.JBINode;
import org.openesb.netbeans.module.api.OpenESBInstance;

/**
 *
 * @author David BRASSELY
 */
public class JBIInstanceNode extends JBINode {
    
    public JBIInstanceNode(OpenESBInstance instance) {
        super(getController(instance));
    }
    
    static AppserverJBIMgmtController getController(OpenESBInstance instance) {
            String PROTOCOL_CLASS = "com.sun.enterprise.admin.jmx.remote.protocol"; // NOI18N
            String HTTP_AUTH_PROPERTY_NAME = "com.sun.enterprise.as.http.auth"; // NOI18N
            String DEFAULT_HTTP_AUTH_SCHEME = "BASIC"; // NOI18N
            String ADMIN_USER_ENV_PROPERTY_NAME = "USER"; // NOI18N
            String ADMIN_PASSWORD_ENV_PROPERTY_NAME = "PASSWORD"; // NOI18N
            String RTS_HTTP_CONNECTOR = "service:jmx:s1ashttp";         // NOI18N

            String hostName = null;
            String port = null;
            String userName = null;
            String password = null;

            hostName = "localhost"; // NOI18N
            port = "4848"; // NOI18N
            userName = "admin"; // NOI18N
            password = "adminadmin"; // NOI18N

            final Map<String, Object> environment = new HashMap<String, Object> ();
            /*
            environment.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, PROTOCOL_CLASS);
            environment.put(HTTP_AUTH_PROPERTY_NAME, DEFAULT_HTTP_AUTH_SCHEME);
            environment.put(ADMIN_USER_ENV_PROPERTY_NAME, userName);
            environment.put(ADMIN_PASSWORD_ENV_PROPERTY_NAME, password);
             */
        try {
            JMXServiceURL jmxUrl = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + "localhost" + ":" + "8686" + "/jmxrmi");
  //      Map<String, String> environment = new HashMap<String, String>();
        environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", "adminadmin"});
        environment.put("USER", "admin");
                environment.put("PASSWORD", "adminadmin");
                environment.put("com.sun.enterprise.as.http.auth", "BASIC");
        JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxUrl, environment);
        MBeanServerConnection jmxMBeanServerCon = jmxConnector.getMBeanServerConnection();
        
            AppserverJBIMgmtController controller = new AppserverJBIMgmtController(jmxMBeanServerCon);
            return controller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
