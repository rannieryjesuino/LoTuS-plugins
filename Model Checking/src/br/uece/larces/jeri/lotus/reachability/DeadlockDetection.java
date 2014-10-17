/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.larces.jeri.lotus.reachability;

import br.uece.seed.app.UserInterface;
import br.uece.seed.ext.ExtensionManager;
import br.uece.seed.ext.Plugin;
import br.uece.lotus.Component;
import br.uece.lotus.Project;
import br.uece.lotus.State;
import br.uece.lotus.Transition;
import br.uece.lotus.project.ProjectExplorer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collection;
import br.uece.lotus.viewer.TransitionViewFactory;

/**
 *
 * @author Ranniery
 */
public class DeadlockDetection extends Plugin {
    /*
    @Override
    public void onStart(ExtensionManager extensionManager) throws Exception {
            
        ProjectExplorer pe = (ProjectExplorer) extensionManager.get(ProjectExplorer.class);
        List<Component> aux = new ArrayList<>();
        pe.getComponentMenu().newItem("Detect Deadlock States")
            .setWeight(Integer.MAX_VALUE)
            .setAction(() -> {
            if (pe.getSelectedComponents().size() != 1) {
                throw new RuntimeException("Select exactly ONE component!");
                }     
                Component a = pe.getSelectedComponents().get(0);
                List<State> deadlocks = detectDeadlocks(a);
                System.out.println("Deadlock States:");
                for(State deadlock : deadlocks){
                    System.out.println(deadlock.getLabel());
                }
                })
                .create();
    }
    */
    public List<State> detectDeadlocks (Component a) {
        List<State> deadlocks = new ArrayList<>();
        for(State aux : a.getStates()){
            if(aux.getOutgoingTransitions() == null){
                deadlocks.add(aux);
            }
        }
        return deadlocks;
    }
}
