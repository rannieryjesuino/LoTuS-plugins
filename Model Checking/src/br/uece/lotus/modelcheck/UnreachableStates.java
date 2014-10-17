/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.modelcheck;

/**
 *
 * @author Ranniery
 */

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
public class UnreachableStates extends Plugin {
    
    public List<State> detectUncheachableStates (Component a) {
        List<State> unreachables = new ArrayList<>();
        
        for(State aux : a.getStates()){
            if(aux.getIncomingTransitionsCount()== 0 && aux != a.getInitialState()){
                unreachables.add(aux);
            }
        }
        
        return unreachables;
    }
}
