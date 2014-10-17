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
<<<<<<< HEAD:JavaLibrary1/src/br/uece/larces/jeri/lotus/reachability/ProbabilisticReach.java
public class ProbabilisticReach extends Plugin{
    
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
        double sum = 0;
        double[][] multiply = new double[tam][tam];
        for ( i = 0 ; i < tam ; i++ )
         {
            for ( j = 0 ; j < tam ; j++ )
            {   
               for (int k = 0 ; k < tam ; k++ )
               {
                  sum = sum + probabilities[i][k]*probabilities[k][j];
               }
 
               multiply[i][j] = sum;
               sum = 0;
            }
         }
        for ( i = 0 ; i < tam ; i++ ){
            for ( j = 0 ; j < tam ; j++ ){
               System.out.print(multiply[i][j]+"\t");
            }
            System.out.print("\n");
        }
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
    
    public static double truncar(double d, int casas_decimais){  
  
        int var1 = (int) d;   // Remove a parte decimal do número... 2.3777 fica 2  
        double var2 = var1*Math.pow(10,casas_decimais); // adiciona zeros..2.0 fica 200.0  
        double var3 = (d - var1)*Math.pow(10,casas_decimais); /** Primeiro retira a parte decimal fazendo 2.3777 - 2 ..fica 0.3777, depois multiplica por 10^(casas decimais) 
        por exemplo se o número de casas decimais que queres considerar for 2, então fica 0.3777*10^2 = 37.77 **/  
        int var4 = (int) var3; // Remove a parte decimal da var3, ficando 37  
        int var5 = (int) var2; // Só para não haver erros de precisão: 200.0 passa a 200  
        int resultado = var5+var4; // O resultado será 200+37 = 237  
        double resultado_final = resultado/Math.pow(10,casas_decimais); // Finalmente divide-se o resultado pelo número de casas decimais, 237/100 = 2.37  
        return resultado_final; // Retorna o resultado_final :P   
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
