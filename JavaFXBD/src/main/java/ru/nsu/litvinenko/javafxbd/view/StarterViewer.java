package ru.nsu.litvinenko.javafxbd.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import ru.nsu.litvinenko.javafxbd.startController;
import ru.nsu.litvinenko.javafxbd.controller.MenuController;

import java.io.IOException;
import java.sql.Statement;

public class StarterViewer {
    public static void start(String login, Scene scene, Statement statement) {
        FXMLLoader fxmlLoader = new FXMLLoader(startController.class.getResource("menu.fxml"));
        fxmlLoader.setController(new MenuController(login, statement));
        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException error) {
            throw new RuntimeException(error);
        }
    }

}
