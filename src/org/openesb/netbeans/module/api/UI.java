package org.openesb.netbeans.module.api;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openesb.netbeans.module.ui.nodes.JBIRootNode;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.nodes.NodeNotFoundException;
import org.openide.nodes.NodeOp;
import org.openide.util.Mutex;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Utility class for manipulating Hudson-related UI elements.
 */
public class UI {

    private UI() {}

    private static final Logger LOG = Logger.getLogger(UI.class.getName());

    /**
     * Try to select a node somewhere beneath the root node in the Services tab.
     * @param path a path as in {@link NodeOp#findPath(Node, String[])}
     */
    public static void selectNode(final String... path) {
        Mutex.EVENT.readAccess(new Runnable() {
            public void run() {
                TopComponent tab = WindowManager.getDefault().findTopComponent("services"); // NOI18N
                if (tab == null) {
                    // XXX have no way to open it, other than by calling ServicesTabAction
                    LOG.fine("No ServicesTab found");
                    return;
                }
                tab.open();
                tab.requestActive();
                if (!(tab instanceof ExplorerManager.Provider)) {
                    LOG.fine("ServicesTab not an ExplorerManager.Provider");
                    return;
                }
                final ExplorerManager mgr = ((ExplorerManager.Provider) tab).getExplorerManager();
                final Node root = mgr.getRootContext();
                RequestProcessor.getDefault().post(new Runnable() {
                    public void run() {
                        Node hudson = NodeOp.findChild(root, JBIRootNode.OPENESB_ROOT_NAME);
                        if (hudson == null) {
                            LOG.fine("ServicesTab does not contain " + JBIRootNode.OPENESB_ROOT_NAME);
                            return;
                        }
                        Node _selected;
                        try {
                            _selected = NodeOp.findPath(hudson, path);
                        } catch (NodeNotFoundException x) {
                            LOG.log(Level.FINE, "Could not find subnode", x);
                            _selected = x.getClosestNode();
                        }
                        final Node selected = _selected;
                        Mutex.EVENT.readAccess(new Runnable() {
                            public void run() {
                                try {
                                    mgr.setSelectedNodes(new Node[] {selected});
                                } catch (PropertyVetoException x) {
                                    LOG.log(Level.FINE, "Could not select path", x);
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
