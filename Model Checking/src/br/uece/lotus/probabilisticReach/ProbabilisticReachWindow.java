/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.probabilisticReach;

import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.Transition;
import br.uece.lotus.probabilisticReach.Parser;
import br.uece.lotus.viewer.BasicComponentViewer;
import br.uece.lotus.viewer.StateView;
import br.uece.lotus.viewer.View;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Ranniery
 */
public class ProbabilisticReachWindow extends AnchorPane{
    private int mStepCount;
    private State mCurrentState;

    private final ToolBar mToolbar;
    private final Button mBtnCalculate;
    private final TextField mSrcSttField;
    private final TextField mTgtSttField;
    private final ChoiceBox mChoiceBox;
    private final TextField mOptionalField;
    private final Label mOutputField;
    private final BasicComponentViewer mViewer;
    private final ScrollPane mScrollPanel;

    public ProbabilisticReachWindow() {
        mViewer = new BasicComponentViewer();
        mScrollPanel = new ScrollPane(mViewer);
        AnchorPane.setTopAnchor(mScrollPanel, 38D);
        AnchorPane.setLeftAnchor(mScrollPanel, 0D);
        AnchorPane.setRightAnchor(mScrollPanel, 0D);
        AnchorPane.setBottomAnchor(mScrollPanel, 0D);
        mViewer.minHeightProperty().bind(mScrollPanel.heightProperty());
        mViewer.minWidthProperty().bind(mScrollPanel.widthProperty());
        getChildren().add(mScrollPanel);        

        mSrcSttField = new TextField();
        mSrcSttField.setPromptText("Source State");
        
        mTgtSttField = new TextField();
        mTgtSttField.setPromptText("Target State");
        
        mChoiceBox = new ChoiceBox();
        mChoiceBox.getItems().addAll(">", ">=", "<", "<=", "=", "!=");
        mChoiceBox.setPrefWidth(30.0);
        Tooltip mToolTip = new Tooltip("Inequation");
        mChoiceBox.setTooltip(mToolTip);
        
        mOptionalField = new TextField();
        mOptionalField.setPromptText("Condition");
        
        mOutputField = new Label("Result");
        
        mBtnCalculate = new Button("Probability");
        mBtnCalculate.setOnAction((ActionEvent e) -> {
            calculate();            
        });

        mToolbar = new ToolBar();
        mToolbar.getItems().addAll(mSrcSttField);
        mToolbar.getItems().addAll(mTgtSttField);
        mToolbar.getItems().addAll(mChoiceBox);
        mToolbar.getItems().addAll(mOptionalField);
        mToolbar.getItems().addAll(mOutputField);
        mToolbar.getItems().addAll(mBtnCalculate);
        AnchorPane.setTopAnchor(mToolbar, 0D);
        AnchorPane.setLeftAnchor(mToolbar, 0D);
        AnchorPane.setRightAnchor(mToolbar, 0D);
        getChildren().add(mToolbar);
    }
    
    private void calculate(){
        mBtnCalculate.setText("Calculate");
        Component a = mViewer.getComponent();
        String aux1 = mSrcSttField.getText();
        String aux2 = mTgtSttField.getText();
        String aux3 = (String) mChoiceBox.getSelectionModel().getSelectedItem();
        String aux4 = mOptionalField.getText();
        if(aux1 == null || aux1.trim().isEmpty() || aux2 == null || aux2.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Provide source and target states!");
            return;
        }
        int source = Integer.parseInt(aux1);
        int destination = Integer.parseInt(aux2);
        State sourceS = a.getStateByID(source);
        State destinationS = a.getStateByID(destination);
        double p = new ProbabilisticReachAlgorithm().probabilityBetween(a, sourceS, destinationS);
        String result = String.valueOf(p);
        
        if(aux4 == null || aux4.trim().isEmpty()){
            mOutputField.setText(result);
        }else{
            double optional = Double.parseDouble(aux4);
            switch (aux3){
                case ">":
                    if(p > optional){
                        mOutputField.setText("True");
                    }else{
                        mOutputField.setText("False");
                    }
                    break;
                case ">=":
                    if(p >= optional){
                        mOutputField.setText("True");
                    }else{
                        mOutputField.setText("False");
                    }
                    break;
                case "<":
                    if(p < optional){
                        mOutputField.setText("True");
                    }else{
                        mOutputField.setText("False");
                    }
                    break;
                case "<=":
                    if(p <= optional){
                        mOutputField.setText("True");
                    }else{
                        mOutputField.setText("False");
                    }
                    break;
                case "=":
                    if(p == optional){
                        mOutputField.setText("True");
                    }else{
                        mOutputField.setText("False");
                    }
                    break;
                case "!=":
                    if(p != optional){
                        mOutputField.setText("True");
                    }else{
                        mOutputField.setText("False");
                    }
                    break;    
            }
        }
        
    }
    
    private void start() {
        mBtnCalculate.setText("Calculate");
    }

    private void showChoices() {
        applyEnableStyle(mCurrentState);
        for (Transition t : mCurrentState.getOutgoingTransitions()) {
            applyChoiceStyle(t);
            applyChoiceStyle(t.getDestiny());
        }
    }

    private void applyEnableStyle(State s) {
        s.setColor(null);
        s.setTextColor("black");
        s.setTextSyle(State.TEXTSTYLE_NORMAL);
        s.setBorderColor("black");
        s.setBorderWidth(1);
    }

    private void applyEnableStyle(Transition t) {
        t.setColor("black");
        t.setTextSyle(Transition.TEXTSTYLE_NORMAL);
        t.setTextColor("black");
        t.setWidth(1);
    }

    private void applyDisabledStyle(State s) {
        s.setColor("#d0d0d0");
        s.setTextColor("#c0c0c0");
        s.setTextSyle(State.TEXTSTYLE_NORMAL);
        s.setBorderColor("gray");
        s.setBorderWidth(1);
    }

    private void applyDisabledStyle(Transition t) {
        t.setColor("#d0d0d0");
        t.setTextColor("#c0c0c0");
        t.setTextSyle(Transition.TEXTSTYLE_NORMAL);
        t.setWidth(1);
    }

    public void setComponent(Component c) {
        mViewer.setComponent(c);
        start();
    }

    private void applyChoiceStyle(Transition t) {
        t.setColor("blue");
        t.setTextSyle(Transition.TEXTSTYLE_BOLD);
        t.setTextColor("blue");
        t.setWidth(2);
    }

    private void applyChoiceStyle(State s) {
        s.setColor(null);
        s.setBorderColor("blue");
        s.setTextSyle(Transition.TEXTSTYLE_BOLD);
        s.setTextColor("blue");
        s.setBorderWidth(2);
    }

    public static class Step {

        private String mAction;
        private String mFrom;
        private String mTo;

        Step(String action, String from, String to) {
            mAction = action;
            mFrom = from;
            mTo = to;
        }

        public String getAction() {
            return mAction;
        }

        public void setAction(String action) {
            this.mAction = action;
        }

        public String getFrom() {
            return mFrom;
        }

        public void setFrom(String from) {
            this.mFrom = from;
        }

        public String getTo() {
            return mTo;
        }

        public void setTo(String to) {
            this.mTo = to;
        }

    }

}
