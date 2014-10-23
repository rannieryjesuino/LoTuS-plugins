/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uece.lotus.probabilisticReach;

import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.Transition;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Note
 */
public class Parser {
    
    public int[] toInt (String[] tokens){
        int size = tokens.length;
        int[] targets = new int[size];
        for(int i = 0; i < size; i++){
            targets[i] = Integer.parseInt(tokens[i]); 
        }
        return targets;
    }
}
