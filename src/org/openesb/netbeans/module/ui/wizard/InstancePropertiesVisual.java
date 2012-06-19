package org.openesb.netbeans.module.ui.wizard;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openesb.netbeans.module.impl.OpenESBManagerImpl;
import org.openide.NotificationLineSupport;
import org.openide.util.NbBundle;

class InstancePropertiesVisual extends JPanel {
    
    public InstancePropertiesVisual() {
        initComponents();
        DocumentListener l = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                check();
            }
            public void removeUpdate(DocumentEvent e) {
                check();
            }
            public void changedUpdate(DocumentEvent e) {}
        };
        nameTxt.getDocument().addDocumentListener(l);
        hostTxt.getDocument().addDocumentListener(l);
        checkProgress.setVisible(false);
    }

    private NotificationLineSupport msgs;
    private JButton addButton;

    void init(NotificationLineSupport msgs, JButton addButton) {
        assert msgs != null;
        this.msgs = msgs;
        this.addButton = addButton;
        check();
    }

    void showChecking() {
        checkProgress.setVisible(true);
        nameTxt.setEnabled(false);
        hostTxt.setEnabled(false);
    }

    void checkFailed(String explanation) {
        msgs.setErrorMessage(explanation);
        checkProgress.setVisible(false);
        nameTxt.setEnabled(true);
        hostTxt.setEnabled(true);
        hostTxt.requestFocusInWindow();
    }

    String getDisplayName() {
        return nameTxt.getText().trim();
    }
    
    String getHostname() {
        return hostTxt.getText().trim();
    }
    
    String getPort() {
        return portTxt.getText().trim();
    }
    
    String getUsername() {
        return usernameTxt.getText().trim();
    }
    
    String getPassword() {
        return passwordTxt.getText().trim();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        nameLabel = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        hostLabel = new javax.swing.JLabel();
        hostTxt = new javax.swing.JTextField();
        checkProgress = new javax.swing.JProgressBar();
        portLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordTxt = new javax.swing.JPasswordField();
        portTxt = new javax.swing.JFormattedTextField();

        nameLabel.setLabelFor(nameTxt);
        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "LBL_Name")); // NOI18N

        hostLabel.setLabelFor(hostTxt);
        org.openide.awt.Mnemonics.setLocalizedText(hostLabel, org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "LBL_Host")); // NOI18N

        hostTxt.setText(org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "InstancePropertiesVisual.hostTxt.text")); // NOI18N
        hostTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostTxtActionPerformed(evt);
            }
        });

        checkProgress.setIndeterminate(true);
        checkProgress.setString(org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "InstancePropertiesVisual.checkProgress.string")); // NOI18N
        checkProgress.setStringPainted(true);

        portLabel.setLabelFor(portTxt);
        org.openide.awt.Mnemonics.setLocalizedText(portLabel, org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "LBL_Port")); // NOI18N

        usernameLabel.setLabelFor(usernameTxt);
        org.openide.awt.Mnemonics.setLocalizedText(usernameLabel, org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "LBL_Username")); // NOI18N

        usernameTxt.setText(org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "InstancePropertiesVisual.usernameTxt.text")); // NOI18N

        passwordLabel.setLabelFor(hostTxt);
        org.openide.awt.Mnemonics.setLocalizedText(passwordLabel, org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "LBL_Password")); // NOI18N

        passwordTxt.setText(org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "InstancePropertiesVisual.passwordTxt.text")); // NOI18N

        portTxt.setColumns(10);
        portTxt.setText(org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "InstancePropertiesVisual.portTxt.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(hostLabel)
                            .addComponent(portLabel)
                            .addComponent(usernameLabel)
                            .addComponent(passwordLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                            .addComponent(nameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                            .addComponent(usernameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                            .addComponent(hostTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                            .addComponent(portTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hostLabel)
                    .addComponent(hostTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portLabel)
                    .addComponent(portTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(checkProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        nameTxt.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "InstancePropertiesVisual.nameTxt.AccessibleContext.accessibleDescription")); // NOI18N
        hostTxt.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "InstancePropertiesVisual.urlTxt.AccessibleContext.accessibleDescription")); // NOI18N

        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstancePropertiesVisual.class, "InstancePropertiesVisual.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
private void hostTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostTxtActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_hostTxtActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar checkProgress;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JTextField hostTxt;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTxt;
    private javax.swing.JLabel portLabel;
    private javax.swing.JFormattedTextField portTxt;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTxt;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    
    private void check() {
        addButton.setEnabled(false);
        String name = getDisplayName();
        String hostname = getHostname();
        String port = getPort();
        String username = getUsername();
        
        if (name.length() == 0) {
            msgs.setInformationMessage(NbBundle.getMessage(InstanceDialog.class, "MSG_EmptyName"));
            return;
        }
        
        if (OpenESBManagerImpl.getDefault().getInstanceByName(name) != null) {
            msgs.setErrorMessage(NbBundle.getMessage(InstanceDialog.class, "MSG_ExistName"));
            return;
        }
        
        if (hostname.length() == 0) {
            msgs.setInformationMessage(NbBundle.getMessage(InstanceDialog.class, "MSG_EmptyHostname"));
            return;
        }
        
        if (port.length() == 0) {
            msgs.setInformationMessage(NbBundle.getMessage(InstanceDialog.class, "MSG_EmptyPort"));
            return;
        }
        
        if (username.length() == 0) {
            msgs.setInformationMessage(NbBundle.getMessage(InstanceDialog.class, "MSG_EmptyUsername"));
            return;
        }
        
        // TODO: test hostname + port to define a unique instance !
        if (OpenESBManagerImpl.getDefault().getInstance(hostname, port) != null) {
            msgs.setErrorMessage(NbBundle.getMessage(InstanceDialog.class, "MSG_ExistInstance"));
            return;
        }
        
        msgs.clearMessages();
        addButton.setEnabled(true);
    }

}
