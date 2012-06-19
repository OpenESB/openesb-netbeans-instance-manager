package org.openesb.netbeans.module.client;

import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 *
 * @author David BRASSELY
 */
public class ConnectionFactory {
    
    /** Creates a new instance of ManagementClientFactory */
    private ConnectionFactory() {
    }
    
    /**
     * Creates a new instance of ManagementClient object
     * 
     * @param hostName
     * @param portNumber
     * @param userName
     * @param password
     * @param connectionType
     * @return ManagementClient object
     * @throws ManagementRemoteException
     */
    public static MBeanServerConnection getInstance(String hostName, int portNumber,
            String userName, String password, ConnectionType connectionType)
            throws ConnectionFactoryException {

        try {
            return getMBeanServerConnection(hostName, portNumber,
                    userName, password, connectionType);
        } catch (ConnectionFactoryException rEx) {
            throw rEx;
        } catch (Exception e) {
            String[] args = { hostName, (new Integer(portNumber)).toString(),
                    userName, connectionType.getProtocol() };
            Exception exception = createManagementException(
                    "jbi.ui.client.factory.connection.host.port.uname.password.protocol",
                    args, e);
            throw new ConnectionFactoryException(exception);
        }
    }

    /**
     * This method returns the MBeanServerConnection to used to invoke the MBean
     * methods via HTTP connector.
     * 
     * @param url -
     *            service:jmx:rmi:///jndi/rmi://<hostName>:<portNumber>/management/rmi-jmx-connector
     * @userName - the userName name for authenticating with MBeanServer
     * @password - the password for authenticating with MBeanServer
     * @return MBeanServerConnection
     * @throws ManagementRemoteException
     */
    private static MBeanServerConnection getMBeanServerConnection(String urlString,
            String userName, String password) throws ConnectionFactoryException {
        try {
            // Create a JMXMP connector client and
            // connect it to the JMXMP connector server
            // final JMXServiceURL url = new JMXServiceURL(urlString);
            // final JMXServiceURL url = new JMXServiceURL(null, hostName,
            // portNumber);
            final JMXServiceURL url = new JMXServiceURL(urlString);
            String[] credentials = new String[] { userName, password };
            Map<String, String[]> environment = new HashMap<String, String[]>();
            environment.put("jmx.remote.credentials", credentials);
            final JMXConnector connector = JMXConnectorFactory.connect(url,
                    environment);
            return connector.getMBeanServerConnection();
        } catch (Exception e) {
            String[] args = { urlString, userName };
            Exception exception = createManagementException(
                    "jbi.ui.client.factory.connection.url.uname.password",
                    args, e);
            throw new ConnectionFactoryException(exception);
        }
    }
    
    /**
     * This method returns the MBeanServerConnection to used to invoke the MBean
     * methods via HTPP connector.
     * 
     * @param hostName -
     *            the hostName part of the URL. If null, defaults to the local
     *            hostName name, as determined by
     *            InetAddress.getLocalHost().getHostName(). If it is a numeric
     *            IPv6 address, it can optionally be enclosed in square brackets
     *            [].
     * @portNumber - the portNumber part of the URL.
     * @userName - the userName name for authenticating with MBeanServer
     * @password - the password for authenticating with MBeanServer
     * @return MBeanServerConnection
     * @throws ManagementRemoteException
     */
    private static MBeanServerConnection getMBeanServerConnection(String hostName,
            int portNumber, String userName, String password,
            ConnectionType type) throws ConnectionFactoryException {
        try {
            if (type == ConnectionType.JRMP) {
                // Create a JMXMP connector client and
                // connect it to the JMXMP connector server
                // final JMXServiceURL url = new JMXServiceURL(null, hostName,
                // portNumber);
                // String urlString =
                // "service:jmx:rmi:///jndi/rmi://"+hostName+":"+portNumber+"/jmxri";
                String urlString = "service:jmx:rmi:///jndi/rmi://" + hostName
                        + ":" + portNumber + "/jmxrmi";
                return getMBeanServerConnection(urlString, userName,
                        password);
            } else if (type == ConnectionType.IIOP) {
                String urlString = "service:jmx:iiop://" + hostName + ":"
                        + portNumber + "/jndi/JMXConnector";
                return getMBeanServerConnection(urlString, userName,
                        password);
            } else {
                final JMXServiceURL url = new JMXServiceURL(type.getProtocol(),
                        hostName, portNumber);
                final JMXConnector connector = JMXConnectorFactory.connect(url,
                        initEnvironment(userName, password));
                return connector.getMBeanServerConnection();
            }
        } catch (Exception e) {
            String[] args = { hostName, (new Integer(portNumber)).toString(),
                    userName, type.getProtocol() };
            Exception exception = createManagementException(
                    "jbi.ui.client.factory.connection.host.port.uname.password.protocol",
                    args, null);
            
            throw new ConnectionFactoryException(exception);
        }
    }
    
    /**
     * This method initialize the environment for creating the JMXConnector.
     * 
     * @return Map - HashMap of environemtn
     */
    private static Map<String, Object> initEnvironment(String userName, String password) {
        final Map<String, Object> environment = new HashMap<String, Object>();
        final String PKGS = "com.sun.enterprise.admin.jmx.remote.protocol";
        
        environment.put(JMXConnectorFactory.PROTOCOL_PROVIDER_CLASS_LOADER,
                ConnectionFactory.class.getClassLoader());
        environment.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, PKGS);
        environment.put("USER", userName);
        environment.put("PASSWORD", password);
        environment.put("com.sun.enterprise.as.http.auth", "BASIC");
        return (environment);
    }
}
