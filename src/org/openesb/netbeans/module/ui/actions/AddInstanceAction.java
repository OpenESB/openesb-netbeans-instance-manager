package org.openesb.netbeans.module.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openesb.netbeans.module.ui.wizard.InstanceDialog;
import org.openide.util.NbBundle;

/**
 * Add OpenESB instance action launches the wizard.
 * 
 * @author David BRASSELY
 */
public class AddInstanceAction extends AbstractAction {

    public AddInstanceAction() {
        super(NbBundle.getMessage(AddInstanceAction.class, "LBL_Add_Instance"));
    }
    
    public void actionPerformed(ActionEvent e) {
       new InstanceDialog().show();
    }
    
 }
