package org.openesb.netbeans.module.ui.wizard;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import org.openesb.netbeans.module.api.OpenESBInstance;
import org.openesb.netbeans.module.api.UI;
import org.openesb.netbeans.module.impl.OpenESBInstanceImpl;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

public class InstanceDialog extends DialogDescriptor {

    private static final Logger LOG = Logger.getLogger(InstanceDialog.class.getName());

    private final InstancePropertiesVisual panel;
    private final JButton addButton;
    private Dialog dialog;
    private OpenESBInstance created;
    
    public InstanceDialog() {
        this(new InstancePropertiesVisual());
    }
    private InstanceDialog(InstancePropertiesVisual panel) {
        super(panel, NbBundle.getMessage(InstanceDialog.class, "LBL_InstanceWiz_Title"));
        this.panel = panel;
        addButton = new JButton(NbBundle.getMessage(InstanceDialog.class, "InstanceDialog.add"));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tryToAdd();
            }
        });
        panel.init(createNotificationLineSupport(), addButton);
        setOptions(new Object[] {addButton, NotifyDescriptor.CANCEL_OPTION});
        setClosingOptions(new Object[] {NotifyDescriptor.CANCEL_OPTION});
    }

    public OpenESBInstance show() {
        dialog = DialogDisplayer.getDefault().createDialog(this);
        dialog.setVisible(true);
        LOG.log(Level.FINE, "Added OpenESB instance: {0}", created);
        return created;
    }

    private void tryToAdd() {
    //    addButton.setEnabled(false);
        panel.showChecking();
        dialog.pack();
        RequestProcessor.getDefault().post(new Runnable() {
            public void run() {
                /*
                try {
                    URL u = new URL(panel.getUrl());
                    HttpURLConnection connection = new ConnectionBuilder().homeURL(u).url(new URL(u, "?checking=redirects")).httpConnection(); // NOI18N
                    String sVersion = connection.getHeaderField("X-Hudson"); // NOI18N
                    connection.disconnect();
                    if (sVersion == null) {
                        problem(NbBundle.getMessage(InstanceDialog.class, "MSG_WrongVersion", HudsonVersion.SUPPORTED_VERSION));
                        return;
                    }
                    if (!"checking=redirects".equals(connection.getURL().getQuery())) { // NOI18N
                        problem(NbBundle.getMessage(InstanceDialog.class, "MSG_incorrect_redirects"));
                        return;
                    }
                } catch (IOException x) {
                    LOG.log(Level.INFO, null, x);
                    problem(NbBundle.getMessage(InstanceDialog.class, "MSG_FailedToConnect"));
                    return;
                }
                 */
                
                created = OpenESBInstanceImpl.createJBIInstance(panel.getDisplayName(), panel.getHostname(), panel.getPort(), panel.getUsername(), panel.getPassword());
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        dialog.dispose();
                        UI.selectNode(panel.getHostname());
                    }
                });
            }
            private void problem(final String explanation) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        addButton.setEnabled(true);
                        panel.checkFailed(explanation);
                        dialog.pack();
                    }
                });
            }
        });
    }

}
