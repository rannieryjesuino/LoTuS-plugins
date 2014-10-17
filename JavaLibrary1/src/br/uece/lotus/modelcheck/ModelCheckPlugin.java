/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.modelcheck;

import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.project.ProjectExplorer;
import br.uece.seed.app.ExtensibleMenu;
import br.uece.seed.app.UserInterface;
import br.uece.seed.ext.ExtensionManager;
import br.uece.seed.ext.Plugin;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author emerson
 */
public class ModelCheckPlugin extends Plugin {

    private UserInterface mUserInterface;
    private ProjectExplorer mProjectExplorer;

    @Override
    public void onStart(ExtensionManager extensionManager) throws Exception {
        mUserInterface = (UserInterface) extensionManager.get(UserInterface.class);
        mProjectExplorer = (ProjectExplorer) extensionManager.get(ProjectExplorer.class);
        
        final ExtensibleMenu componentMenu = mProjectExplorer.getComponentMenu();
        componentMenu.newItem("Parallel Composition")
                .setWeight(Integer.MAX_VALUE)
                .setAction(() -> {
                    if (mProjectExplorer.getSelectedComponents().size() != 2) {
                        throw new RuntimeException("Select exactly TWO components!");
                    }
                    Component a = mProjectExplorer.getSelectedComponents().get(0);
                    Component b = mProjectExplorer.getSelectedComponents().get(1);
//                        System.out.println("clicou em " + a +", "+ b);
                    Component c = new ParallelCompositor().compor(a, b);
                    c.setName(a.getName() + " || " + b.getName());
                    mProjectExplorer.getSelectedProject().addComponent(c);
                })
                .create();

        mProjectExplorer.getComponentMenu().newItem("Detect Deadlock States")
                .setWeight(Integer.MAX_VALUE)
                .setAction(() -> {
                    if (mProjectExplorer.getSelectedComponents().size() != 1) {
                        throw new RuntimeException("Select exactly ONE component!");
                    }
                    Component a = mProjectExplorer.getSelectedComponents().get(0);
                    List<State> deadlocks = new DeadlockDetection().detectDeadlocks(a);
                    System.out.println("Deadlock States:");
                    for (State deadlock : deadlocks) {
                        System.out.println(deadlock.getLabel());
                    }
                })
                .create();
        
        final ExtensibleMenu mainMenu = mUserInterface.getMainMenu();        
        mainMenu.newItem("Model/Probabilistic Reach")
                .setWeight(Integer.MAX_VALUE)
                .setAction(() -> {
                    if (mProjectExplorer.getSelectedComponents().size() != 1) {
                        throw new RuntimeException("Select exactly ONE component!");
                    }
                    Component a = mProjectExplorer.getSelectedComponents().get(0);
                    String aux1 = JOptionPane.showInputDialog("Please input source state:");
                    String aux2 = JOptionPane.showInputDialog("Please input destination state:");
                    int source = Integer.parseInt(aux1);
                    int destination = Integer.parseInt(aux2);
                    State sourceS = a.getStateByID(source);
                    State destinationS = a.getStateByID(destination);
                    double p = new ProbabilisticReach().probabilityBetween(a, sourceS, destinationS, 1);
                    System.out.println(p);
                    JOptionPane.showMessageDialog(null, "Probability to reach state " + destination + " from state " + source + " is: " + p);
                })
                .create();                             
        
    }

}
