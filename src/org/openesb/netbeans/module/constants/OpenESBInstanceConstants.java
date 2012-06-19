package org.openesb.netbeans.module.constants;

/**
 *
 * @author David BRASSELY
 */
public class OpenESBInstanceConstants {

    private OpenESBInstanceConstants() {}
    
    public static final String INSTANCE_NAME = "name"; // NOI18N
    
    public static final String INSTANCE_HOST = "host"; // NOI18N
    
    public static final String INSTANCE_PORT = "port"; // NOI18N
    
    public static final String INSTANCE_USERNAME = "username"; // NOI18N
    
    public static final String INSTANCE_PASSWORD = "password"; // NOI18N

    /**
     * preferred jobs for the instance, list of job names, separated by |
     */
    public static final String INSTANCE_PREF_JOBS = "pref_jobs"; // NOI18N

    /**
     * Nonsalient jobs for the instance, list of job names, separated by |
     */
    public static final String INSTANCE_SUPPRESSED_JOBS = "suppressed_jobs"; // NOI18N

}
