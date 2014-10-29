/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.probabilisticReach;

import br.uece.lotus.probabilisticReach.ProbabilisticReach;
import br.uece.lotus.probabilisticReach.ProbabilisticReachPlugin;
import br.uece.lotus.probabilisticReach.ProbabilisticReachWindow;
import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.modelcheck.DeadlockDetection;
import br.uece.lotus.modelcheck.ParallelCompositor;
import br.uece.lotus.project.ProjectExplorer;
import br.uece.lotus.properties.PropertiesEditor;
import br.uece.seed.app.UserInterface;
import br.uece.seed.ext.ExtensionManager;
import br.uece.seed.ext.Plugin;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ranniery
 */
public class ProbabilisticReachPlugin extends Plugin implements ProbabilisticReach {
    private UserInterface mUserInterface;
    private ProjectExplorer mProjectExplorer;
    private PropertiesEditor mPropertiesEditor;
    private final Runnable mOpenProbabilisticReach = () -> {
        try {
            Component c = mProjectExplorer.getSelectedComponent();
            if (c == null) {
                JOptionPane.showMessageDialog(null, "Select a component!");
                return;
            }
//            show(mProjectExplorer.getSelectedComponent(), true);
            show(c.clone(), true);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ProbabilisticReachPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
    };

    @Override
    public void onStart(ExtensionManager extensionManager) throws Exception {
        mUserInterface = extensionManager.get(UserInterface.class);
        mProjectExplorer = extensionManager.get(ProjectExplorer.class);
        
        mUserInterface.getMainMenu().newItem("Verification/Probabilistic Reachability")
                .setWeight(Integer.MAX_VALUE)
                .setAction(mOpenProbabilisticReach)
                .create();
////        mProjectExplorer.getComponentMenu()
////                .addItem(Integer.MAX_VALUE, "-", null);
////        mProjectExplorer.getComponentMenu()
////                .addItem(Integer.MAX_VALUE, "Calculate", mOpenProbabilisticReach);
//        mUserInterface.getToolBar().addItem(Integer.MAX_VALUE, "-", null);
//        mUserInterface.getToolBar().addItem(Integer.MAX_VALUE, "Probabilistic Reach", mOpenProbabilisticReach);

    }

    @Override
    public void show(Component c, boolean editable) {
//        Integer tabId = (Integer) c.getValue("tab.id");
//        if (tabId == null) {
        ProbabilisticReachWindow w = new ProbabilisticReachWindow();
        w.setComponent(c);
        int id = mUserInterface.getCenterPanel().newTab(c.getName() + " - [Probabilistic Reachability]", w, true);
//            c.setValue("tab.id", tabId);
//            c.setValue("gui", w);
//        }
        mUserInterface.getCenterPanel().showTab(id);
    }
}
