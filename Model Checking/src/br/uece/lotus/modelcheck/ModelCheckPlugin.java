/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.modelcheck;

import br.uece.lotus.probabilisticReach.ProbabilisticReachAlgorithm;
import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.project.ProjectExplorer;
import br.uece.seed.app.UserInterface;
import br.uece.seed.ext.ExtensionManager;
import br.uece.seed.ext.Plugin;
import java.util.ArrayList;
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
        mProjectExplorer.getComponentMenu().newItem("Parallel Composition")
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

        List<Component> aux = new ArrayList<>();
//        pe.getComponentMenu().newItem("Unreachable States")
        mUserInterface.getMainMenu().newItem("Verification/Unreachable States")
            .setWeight(Integer.MAX_VALUE)
            .setAction(() -> {
            if (mProjectExplorer.getSelectedComponents().size() != 1) {
                throw new RuntimeException("Select exactly ONE component!");
                }     
                Component a = mProjectExplorer.getSelectedComponents().get(0);
                List<State> unreachables = new UnreachableStates().detectUncheachableStates(a);
                System.out.println("Unreachable States:");
                String output = new String();
                    int tam = unreachables.size();
//                    System.out.println("Deadlock States:");
                    if(unreachables.size() > 0){
                        output += unreachables.get(0).getLabel();
                        for (int i = 1; i < unreachables.size(); i++) {
                            output += ", " + unreachables.get(i).getLabel();
                        }
                        output += ".";
                        JOptionPane.showMessageDialog(null, "Unreachable States: " + output);
                    }else{
                        JOptionPane.showMessageDialog(null, "No unreachable states!");
                    }
            })
                .create();
        
        mUserInterface.getMainMenu().newItem("Verification/Deadlock States")
                .setWeight(Integer.MAX_VALUE)
                .setAction(() -> {
                    if (mProjectExplorer.getSelectedComponents().size() != 1) {
                        throw new RuntimeException("Select exactly ONE component!");
                    }
                    Component a = mProjectExplorer.getSelectedComponents().get(0);
                    List<State> deadlocks = new DeadlockDetection().detectDeadlocks(a);
                    String output = new String();
                    int tam = deadlocks.size();
//                    System.out.println("Deadlock States:");
                    if(deadlocks.size() > 0){
                        output += deadlocks.get(0).getLabel();
                        for (int i = 1; i < deadlocks.size(); i++) {
                            output += ", " + deadlocks.get(i).getLabel();
                        }
                        output += ".";
                        JOptionPane.showMessageDialog(null, "Deadlock States: " + output);
                    }else{
                        JOptionPane.showMessageDialog(null, "No deadlocks!");
                    }
                })
                .create();
        
//        mUserInterface.getMainMenu().newItem("Model/Probabilistic Reach")
//                .setWeight(Integer.MAX_VALUE)
//                .setAction(() -> {
//                    if (mProjectExplorer.getSelectedComponents().size() != 1) {
//                        throw new RuntimeException("Select exactly ONE component!");
//                    }
//                    Component a = mProjectExplorer.getSelectedComponents().get(0);
//                    String aux1 = JOptionPane.showInputDialog("Please input source state:");
//                    String aux2 = JOptionPane.showInputDialog("Please input destination state:");
//                    int source = Integer.parseInt(aux1);
//                    int destination = Integer.parseInt(aux2);
//                    State sourceS = a.getStateByID(source);
//                    State destinationS = a.getStateByID(destination);
//                    double p = new ProbabilisticReachAlgorithm().probabilityBetween(a, sourceS, destinationS, 1);
//                    
//                })
//                .create();
    }

}
