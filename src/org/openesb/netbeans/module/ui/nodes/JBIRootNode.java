package org.openesb.netbeans.module.ui.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Action;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import org.netbeans.modules.sun.manager.jbi.nodes.JBINode;
import org.openesb.netbeans.module.api.OpenESBChangeListener;
import org.openesb.netbeans.module.impl.OpenESBInstanceImpl;
import org.openesb.netbeans.module.impl.OpenESBManagerImpl;
import org.openesb.netbeans.module.ui.actions.AddInstanceAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

/**
 *
 * @author David BRASSELY
 */
public class JBIRootNode extends AbstractNode {
    
    public static final String OPENESB_ROOT_NAME = "OpenESB"; // NOI18N
    private static final String ICON_BASE = "org/openesb/netbeans/module/ui/resources/openesb.png"; // NOI18N
    
    @ServicesTabNodeRegistration(name=OPENESB_ROOT_NAME, displayName="#LBL_OpenESBNode", iconResource=ICON_BASE)
    public static JBIRootNode getDefault() {
        return new JBIRootNode();
    }
    
    private JBIRootNode() {
        super(Children.create(new RootNodeChildren(), true));
        setName(OPENESB_ROOT_NAME);
        setDisplayName(NbBundle.getMessage(JBIRootNode.class, "LBL_OpenESBNode"));
        setIconBaseWithExtension(ICON_BASE);
    }
    
    public @Override Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<Action>();
        actions.add(new AddInstanceAction());
        return actions.toArray(new Action[actions.size()]);
    }
    
    private static class RootNodeChildren extends ChildFactory<OpenESBInstanceImpl> implements OpenESBChangeListener {
        
        public RootNodeChildren() {
            OpenESBManagerImpl.getDefault().addOpenESBChangeListener(this);
        }

        protected @Override Node createNodeForKey(OpenESBInstanceImpl key) {
            JBINode node = new JBINode(key.getConnection());
            node.setDisplayName(key.getName());
            
            return node;
        }
        
        protected boolean createKeys(List<OpenESBInstanceImpl> toPopulate) {
            toPopulate.addAll(OpenESBManagerImpl.getDefault().getInstances());
            Collections.sort(toPopulate);
            return true;
        }

        @Override
        public void stateChanged() {
        }

        @Override
        public void contentChanged() {
            refresh(false);
        }
    }
}
