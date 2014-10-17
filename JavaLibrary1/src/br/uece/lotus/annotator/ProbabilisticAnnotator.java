/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.annotator;

import br.uece.lotus.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emerson
 */
public class ProbabilisticAnnotator {

    public void annotate(Component c, InputStream input) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] path = line.split(",");
                updateProbabilityOfPath(c, path);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProbabilisticAnnotator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateProbabilityOfPath(Component c, String[] path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
