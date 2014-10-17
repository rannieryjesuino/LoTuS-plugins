/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.modelcheck;

import br.uece.lotus.annotator.ProbabilisticAnnotator;
import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.Transition;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author emerson
 */
public class ProbabilisticAnnotatorTest {

    private static InputStream PATH = new ByteArrayInputStream("x\ny\nx\ny\nx\ny\nx\ny".getBytes());
    private static Component mComponent;
    private static Transition mX;
    private static Transition mY;
    private static ProbabilisticAnnotator mAnnotator;
    private static InputStream PATH_2 = new ByteArrayInputStream("x\ny\nx\ny\nx\ny\nx\ny".getBytes());

    public ProbabilisticAnnotatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        mAnnotator = new ProbabilisticAnnotator();

        mComponent = new Component();
        State a = mComponent.newState(0);
        State b = mComponent.newState(1);
        State c = mComponent.newState(2);

        mX = mComponent.buildTransition(a, b)
                .setLabel("x")
                .create();
        mY = mComponent.buildTransition(a, c)
                .setLabel("y")
                .create();
    }

    @Test
    public void test50() {
        mAnnotator.annotate(mComponent, PATH);
        Assert.assertEquals(0.5, mX.getProbability());
        Assert.assertEquals(0.5, mY.getProbability());
    }

    @Test
    public void test51() {
        mAnnotator.annotate(mComponent, input(""));
        Assert.assertEquals(0.5, mX.getProbability());
        Assert.assertEquals(0.5, mY.getProbability());
    }

    private InputStream input(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
    
}
