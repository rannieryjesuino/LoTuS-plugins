/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.modelcheck;

import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.Transition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ranniery
 */
public class ProbabilisticReach {
    
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
    
    public double probabilityBetween (Component a, State source, State destination, double probability) {
        int tam = getStatesSize(a.getStates());//statesL.size();
        double[][] probabilities = new double[tam][tam];
        probabilities = zerar(probabilities, tam);
        List<Transition> transitions = transitionsList(a);
        int i;
        int j;
        for(Transition t : transitions){
            i = t.getSource().getID();
            j = t.getDestiny().getID();
            probabilities[i][j] = t.getProbability();
        }
//      multiplica as matrizes um monte de vez
        i = source.getID();
        j = destination.getID();
        return probabilities[i][j];
    }
    
    public List<Transition> transitionsList(Component a){
        Iterable<Transition> aux = a.getTransitions();
        List<Transition> aux2 = new ArrayList();
        for(Transition t : aux){
            aux2.add(t);
        }
        return aux2;
    }
    
    public int getStatesSize(Iterable<State> states){
        List<State> statesL = new ArrayList();
        for(State aux : states){
            statesL.add(aux);
        }
        int size = statesL.size();
        return size;
    }
    
    public double[][] zerar (double[][] probabilities, int tam){
        for(int i = 0; i < tam; i++){
            for(int j = 0; j < tam; j++){
                probabilities[i][j] = 0;
            }
        }
        return probabilities;
    }
   
//        if(destination == source){
//            return probability;
//        }
//        for(Transition finder : destination.getIncomingTransitions()){
//            probability = finder.getProbability() + probabilityBetween(source, finder.getSource(), finder.getProbability() * probability);
//        }
    
    
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
