package org.openesb.netbeans.module.api;

/**
 *
 * @author David BRASSELY
 */
public class OpenESBInstanceKey {
    
    private final String hostname;
    private final String port;
    
    public OpenESBInstanceKey(String hostname, String port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPort() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OpenESBInstanceKey other = (OpenESBInstanceKey) obj;
        if ((this.hostname == null) ? (other.hostname != null) : !this.hostname.toUpperCase().equals(other.hostname.toUpperCase())) {
            return false;
        }
        if ((this.port == null) ? (other.port != null) : !this.port.toUpperCase().equals(other.port.toUpperCase())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.hostname != null ? this.hostname.toUpperCase().hashCode() : 0);
        hash = 37 * hash + (this.port != null ? this.port.toUpperCase().hashCode() : 0);
        return hash;
    }
}
