package org.openesb.netbeans.module.client;

/**
 * Defines the type of connection to use
 * 
 * @author David BRASSELY
 */
public enum ConnectionType {
    HTTP("s1ashttp"), HTTPS("s1ashttps"), JRMP("jmxrmi"), IIOP("iiop");

    String protocol;

    /** @param protocolString */
    private ConnectionType(String protocolString) {
        this.protocol = protocolString;
    }

    /** @return the protocol */
    public String getProtocol() {
        return protocol;
    }

    /** @return the protocol */
    public String getDescription() {
        switch (this) {
        case HTTP:
            return "Unsecure HTTP Connection";
        case HTTPS:
            return "Secure HTTP Connection";
        case JRMP:
            return "JSR-160 JRMP Connection";
        case IIOP:
            return "JMX RMI Over IIOP Connection";
        default:
            return "Unknown";
        }
    }

}
