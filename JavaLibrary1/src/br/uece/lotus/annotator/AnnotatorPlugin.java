/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uece.lotus.annotator;

import br.uece.seed.app.UserInterface;
import br.uece.seed.ext.ExtensionManager;
import br.uece.seed.ext.Plugin;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

/**
 *
 * @author emerson
 */
public class AnnotatorPlugin extends Plugin {

    private UserInterface mUserInterface;

    @Override
    public void onStart(ExtensionManager extensionManager) throws Exception {
        mUserInterface = (UserInterface) extensionManager.get(UserInterface.class);

        mUserInterface.getMainMenu().newItem("Model/Probabilistic Annotator")
                .setWeight(Integer.MAX_VALUE)
                .setAction(() -> {
                    try {
                        URL location = getClass().getResource("/br/uece/lotus/annotator/AnnotatorManager.fxml");
                        FXMLLoader loader = new FXMLLoader();
                        loader.setClassLoader(getClass().getClassLoader());
                        loader.setLocation(location);
                        loader.setBuilderFactory(new JavaFXBuilderFactory());
                        Parent root = (Parent) loader.load(location.openStream());
                        mUserInterface.getBottomPanel().newTab("Probabilistic Annotator", root, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .create();
    }

}