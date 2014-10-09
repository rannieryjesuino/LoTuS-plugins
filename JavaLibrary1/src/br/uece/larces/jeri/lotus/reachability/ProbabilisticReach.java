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
import javax.swing.JOptionPane;

/**
 *
 * @author Ranniery
 */
public class ProbabilisticReach extends Plugin{
    
    @Override
    public void onStart(ExtensionManager extensionManager) throws Exception {
            
        ProjectExplorer pe = (ProjectExplorer) extensionManager.get(ProjectExplorer.class);
        List<Component> aux = new ArrayList<>();
        pe.getMenu().addItem(Integer.MAX_VALUE, "Probabilistic Reach", () ->{
            if (pe.getSelectedComponents().size() != 1) {
                throw new RuntimeException("Select exactly ONE component!");
                }     
                Component a = pe.getSelectedComponents().get(0);
                String aux1 = JOptionPane.showInputDialog("Please input source state:");
                String aux2 = JOptionPane.showInputDialog("Please input destination state:");
                int source = Integer.parseInt(aux1);
                int destination = Integer.parseInt(aux2);
                State sourceS = a.getStateByID(source);
                State destinationS = a.getStateByID(destination);
                probabilityBetween(sourceS, destinationS, 1);
        });
    }
    
    /*
    
    ProjectExplorer pe = (ProjectExplorer) extensionManager.get(ProjectExplorer.class);
        List<Component> aux = new ArrayList<>();
        pe.getComponentMenu().newItem("Probabilistic Reach")
            .setWeight(Integer.MAX_VALUE)
            .setAction(() -> {
            if (pe.getSelectedComponents().size() != 1) {
                throw new RuntimeException("Select exactly ONE component!");
                }     
                Component a = pe.getSelectedComponents().get(0);
                String aux1 = JOptionPane.showInputDialog("Please input source state:");
                String aux2 = JOptionPane.showInputDialog("Please input destination state:");
                int source = Integer.parseInt(aux1);
                int destination = Integer.parseInt(aux2);
                State sourceS = a.getStateByID(source);
                State destinationS = a.getStateByID(destination);
                probabilityBetween(sourceS, destinationS, 1);
            })
            
            .create();
    }
    
    */
    
    public double probabilityBetween (State source, State destination, double probability) {
        if(destination == source){
            return probability;
        }
        for(Transition finder : destination.getIncomingTransitions()){
            probability = finder.getProbability() + probabilityBetween(source, finder.getSource(), finder.getProbability() * probability);
        }
        return 0;
    }
    /*
    
    static boolean pathBetween(GraphNode source, GraphNode destination) {
  // Base case
  if (source.equals(destination)) return true;
  // Recursive case: see if there's a path from 
  // a neighbor to the destination.
  for(Iterator neighbors = source.getNeighbors()); 
      neighbors.hasMoreElements(); ) {
    GraphNode neighbor = neighbors.nextElement();
    if pathBetween(neighbor,destination) return true;
  }
  // If we reach this point, we've effectively tried every 
  // possible way to reach the destination.  It must not
  // be possible.
  return false;
} // reachable
    
    */
}
