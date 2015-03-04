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
import com.sun.prism.paint.Color;
import com.sun.prism.paint.Paint;
import java.awt.Transparency;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Ranniery
 */
public class ProbabilisticReachWindow extends AnchorPane{
    private int mStepCount;
    private State mCurrentState;

    private final ToolBar mToolbar;
    private final Button mBtnCalculate;
    private final Button mBtnAdd;
    private final TextField mSrcSttField;
    private final TextField mTgtSttField;
    private final ChoiceBox mChoiceBox;
    private final TextField mOptionalField;
    private final TextField mOutputField;
    private final BasicComponentViewer mViewer;
    private final ScrollPane mScrollPanel;
    private final TableView<Entry> mTableView;
    private final TableColumn<Entry, String> mSourceCol;
    private final TableColumn<Entry, String> mTargetCol;
    private final TableColumn<Entry, String> mInequationCol;
    private final TableColumn<Entry, String> mConditionCol;
    private final TableColumn<Entry, String> mResultCol;
    private final ObservableList<Entry> mEntries = FXCollections.observableArrayList();

    public ProbabilisticReachWindow() {
        mViewer = new BasicComponentViewer();
        mScrollPanel = new ScrollPane(mViewer);
        AnchorPane.setTopAnchor(mScrollPanel, 38D);
        AnchorPane.setLeftAnchor(mScrollPanel, 0D);
        AnchorPane.setRightAnchor(mScrollPanel, 0D);
        AnchorPane.setBottomAnchor(mScrollPanel, 200D);
        mViewer.minHeightProperty().bind(mScrollPanel.heightProperty());
        mViewer.minWidthProperty().bind(mScrollPanel.widthProperty());
        getChildren().add(mScrollPanel);        

        mSrcSttField = new TextField();
        mSrcSttField.setPromptText("Source");
        mSrcSttField.setPrefWidth(58.0);
        
        mTgtSttField = new TextField();
        mTgtSttField.setPromptText("Target");
        mTgtSttField.setPrefWidth(58.0);
        
        mChoiceBox = new ChoiceBox();
        mChoiceBox.getItems().addAll(">", ">=", "<", "<=", "=", "!=");
        mChoiceBox.setPrefWidth(30.0);
        Tooltip mToolTip = new Tooltip("Inequation");
        mChoiceBox.setTooltip(mToolTip);
        
        mOptionalField = new TextField();
        mOptionalField.setPromptText("Condition");
        mOptionalField.setPrefWidth(70.0);
        
        
        mBtnCalculate = new Button("Calculate");
        mBtnCalculate.setOnAction((ActionEvent e) -> {
            calculate(false, null);            
        });
        
        mBtnAdd = new Button("+");
        mBtnAdd.setOnAction((ActionEvent e) -> {
            addEntry();            
        });
        
        mOutputField = new TextField();
        mOutputField.setPromptText("Result");
        mOutputField.setPrefWidth(58.0);
        mOutputField.setEditable(false);
        mOutputField.setFocusTraversable(false);
        mOutputField.setMouseTransparent(true);
        mOutputField.setStyle("-fx-background-color: yellow; -fx-prompt-text-fill: black;");
        
        mTableView = new TableView();
        mTableView.setPrefHeight(200);
        AnchorPane.setLeftAnchor(mTableView, 0D);
        AnchorPane.setRightAnchor(mTableView, 0D);
        AnchorPane.setBottomAnchor(mTableView, 0D);
        getChildren().add(mTableView);
        
        mSourceCol = new TableColumn<>("Source");
        mSourceCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("source"));
        mSourceCol.setPrefWidth(100);
        mTargetCol = new TableColumn<>("Target");
        mTargetCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("target"));
        mTargetCol.setPrefWidth(100);
        mInequationCol = new TableColumn<>("Inequation");
        mInequationCol.setPrefWidth(100);
        mInequationCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("inequation"));
        mConditionCol = new TableColumn<>("Condition");
        mConditionCol.setPrefWidth(100);
        mConditionCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("condition"));
        mResultCol = new TableColumn<>("Result");
        mResultCol.setPrefWidth(100);
        mResultCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("result"));
        
        mTableView.getColumns().addAll(mSourceCol, mTargetCol, mInequationCol, mConditionCol, mResultCol);
        mTableView.setItems(mEntries);
        ContextMenu menu = new ContextMenu();
        MenuItem verify = new MenuItem("Verify Property");
        verify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Entry selectedEntry = (Entry) mTableView.getSelectionModel().getSelectedItem();
                calculate(true, selectedEntry);
                refresh();
                System.out.println();
            }
        });
        MenuItem verifyAll = new MenuItem("Verify All");
        verifyAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Entry> allEntries = mTableView.getItems();
                for(Entry selectedEntry : allEntries){
                    calculate(true, selectedEntry);
                    refresh();
                    System.out.println();
                }
            }
        });
//        EM ANDAMENTO
//        MenuItem remove = new MenuItem("Remove");
//        remove.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Entry selectedEntry = (Entry) mTableView.getSelectionModel().getSelectedItem();
//                mTableView.getSelectionModel().getSelectedItem().;
//                refresh();
//                System.out.println();
//            }
//        });
        menu.getItems().add(verify);
        menu.getItems().add(verifyAll);
//        menu.getItems().add(remove);
        mTableView.setContextMenu(menu);

        mToolbar = new ToolBar();
        mToolbar.getItems().addAll(mSrcSttField);
        mToolbar.getItems().addAll(mTgtSttField);
        mToolbar.getItems().addAll(mChoiceBox);
        mToolbar.getItems().addAll(mOptionalField);
        mToolbar.getItems().addAll(mBtnCalculate);
        mToolbar.getItems().addAll(mOutputField);
        mToolbar.getItems().addAll(mBtnAdd);
        AnchorPane.setTopAnchor(mToolbar, 0D);
        AnchorPane.setLeftAnchor(mToolbar, 0D);
        AnchorPane.setRightAnchor(mToolbar, 0D);
        getChildren().add(mToolbar);
    }
    
    private void refresh(){
        mTableView.setItems(null);
        mTableView.layout();
        mTableView.setItems(mEntries);
    }
    
    private void addEntry(){
        calculate(false, null);
        String aux1 = mSrcSttField.getText();
        String aux2 = mTgtSttField.getText();
        String aux3 = (String) mChoiceBox.getSelectionModel().getSelectedItem();
        String aux4 = mOptionalField.getText();
        String aux5 = mOutputField.getText();
        Entry newEntry = new Entry(aux1, aux2, aux3, aux4, aux5);
        
        if(aux4 == null || aux4.trim().isEmpty()){
            newEntry.setInequation("");
            newEntry.setCondition("");
            //Contains
            for(Entry aux : mEntries){
                if(aux.getSource().equals(newEntry.getSource()) && aux.getTarget().equals(newEntry.getTarget()) && aux.getInequation().equals(newEntry.getInequation()) && aux.getCondition().equals(newEntry.getCondition()) && aux.getResult().equals(newEntry.getResult())){
                    return;
                }
            }
            mEntries.add(newEntry);
        }else{
            //Contains
            for(Entry aux : mEntries){
                if(aux.getSource().equals(newEntry.getSource()) && aux.getTarget().equals(newEntry.getTarget()) && aux.getInequation().equals(newEntry.getInequation()) && aux.getCondition().equals(newEntry.getCondition()) && aux.getResult().equals(newEntry.getResult())){
                    return;
                }
            }
            mEntries.add(newEntry);
        }
        
    }
    
    private String calculate(boolean fromMenu, Entry selectedEntry){
        mBtnCalculate.setText("Calculate");
        
        Component a = mViewer.getComponent();
        String aux1 = mSrcSttField.getText();
        String aux2 = mTgtSttField.getText();
        String aux3 = (String) mChoiceBox.getSelectionModel().getSelectedItem();
        String aux4 = mOptionalField.getText();
        
        if(fromMenu){
            aux1 = selectedEntry.getSource();
            aux2 = selectedEntry.getTarget();
            aux3 = selectedEntry.getInequation();
            aux4 = selectedEntry.getCondition();
        }
        
        if(aux1 == null || aux1.trim().isEmpty() || aux2 == null || aux2.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Provide source and target states!");
            return null;
        }
        int source = Integer.parseInt(aux1);
        int destination = Integer.parseInt(aux2);
        State sourceS = a.getStateByID(source);
        State destinationS = a.getStateByID(destination);
        double p = new ProbabilisticReachAlgorithm().probabilityBetween(a, sourceS, destinationS);
        String result = String.valueOf(p);
        
        if(aux4 == null || aux4.trim().isEmpty()){
            mOutputField.setText(result);
            mOutputField.setStyle("-fx-background-color: yellow");
            if(fromMenu){ selectedEntry.setResult(result + " ok!");}
        }else{
            double optional = Double.parseDouble(aux4);
            switch (aux3){
                case ">":
                    if(p > optional){
                        mOutputField.setText("True");
                        mOutputField.setStyle("-fx-background-color: limegreen");
                        if(fromMenu){selectedEntry.setResult("True" + " ok!");}
                    }else{
                        mOutputField.setText("False");
                        mOutputField.setStyle("-fx-background-color: red");
                        if(fromMenu){selectedEntry.setResult("False" + " ok!");}
                    }
                    break;
                case ">=":
                    if(p >= optional){
                        mOutputField.setText("True");
                        mOutputField.setStyle("-fx-background-color: limegreen");
                        if(fromMenu){selectedEntry.setResult("True" + " ok!");}
                    }else{
                        mOutputField.setText("False");
                        mOutputField.setStyle("-fx-background-color: red");
                        if(fromMenu){selectedEntry.setResult("False" + " ok!");}
                    }
                    break;
                case "<":
                    if(p < optional){
                        mOutputField.setText("True");
                        mOutputField.setStyle("-fx-background-color: limegreen");
                        if(fromMenu){selectedEntry.setResult("True" + " ok!");}
                    }else{
                        mOutputField.setText("False");
                        mOutputField.setStyle("-fx-background-color: red");
                        if(fromMenu){selectedEntry.setResult("False" + " ok!");}
                    }
                    break;
                case "<=":
                    if(p <= optional){
                        mOutputField.setText("True");
                        mOutputField.setStyle("-fx-background-color: limegreen");
                        if(fromMenu){selectedEntry.setResult("True" + " ok!");}
                    }else{
                        mOutputField.setText("False");
                        mOutputField.setStyle("-fx-background-color: red");
                        if(fromMenu){selectedEntry.setResult("False" + " ok!");}
                    }
                    break;
                case "=":
                    if(p == optional){
                        mOutputField.setText("True");
                        mOutputField.setStyle("-fx-background-color: limegreen");
                        if(fromMenu){selectedEntry.setResult("True" + " ok!");}
                    }else{
                        mOutputField.setText("False");
                        mOutputField.setStyle("-fx-background-color: red");
                        if(fromMenu){selectedEntry.setResult("False" + " ok!");}
                    }
                    break;
                case "!=":
                    if(p != optional){
                        mOutputField.setText("True");
                        mOutputField.setStyle("-fx-background-color: limegreen");
                        if(fromMenu){selectedEntry.setResult("True" + " ok!");}
                    }else{
                        mOutputField.setText("False");
                        mOutputField.setStyle("-fx-background-color: red");
                        if(fromMenu){selectedEntry.setResult("False" + " ok!");}
                    }
                    break;    
            }
        }
        
        return result;
        
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

    public static class Entry {

        private SimpleStringProperty  mSource;
        private SimpleStringProperty mTarget;
        private SimpleStringProperty mInequation;
        private SimpleStringProperty mCondition;
        private SimpleStringProperty mResult;

        Entry(String source, String target, String inequation, String condition, String result) {
            this.mSource = new SimpleStringProperty(source);
            this.mTarget = new SimpleStringProperty(target);
            this.mInequation = new SimpleStringProperty(inequation);
            this.mCondition = new SimpleStringProperty(condition);
            this.mResult = new SimpleStringProperty(result);
        }

        public String getSource() {
            return mSource.get();
        }

        public void setSource(String source) {
            mSource.set(source);
        }

        public String getTarget() {
            return mTarget.get();
        }

        public void setTarget(String target) {
            mTarget.set(target);
        }

        public String getInequation() {
            return mInequation.get();
        }

        public void setInequation(String inequation) {
            mInequation.set(inequation);
        }
        
        public String getCondition() {
            return mCondition.get();
        }

        public void setCondition(String condition) {
            mCondition.set(condition);
        }
        
        public String getResult() {
            return mResult.get();
        }

        public void setResult(String result) {
            mResult.set(result);
        }

    }

}
