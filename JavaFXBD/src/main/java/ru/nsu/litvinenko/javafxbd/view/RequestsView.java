package ru.nsu.litvinenko.javafxbd.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import ru.nsu.litvinenko.javafxbd.controller.RequestsController;
import ru.nsu.litvinenko.javafxbd.controller.SoldierController;
import ru.nsu.litvinenko.javafxbd.controller.query5Controller;
import ru.nsu.litvinenko.javafxbd.controller.query8Controller;
import ru.nsu.litvinenko.javafxbd.tables.Locations;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.tables.UnitsAndVehicles;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class RequestsView {
    public static void query5(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Locations.class.getResource("query5.fxml"));
        fxmlLoader.setController(new query5Controller(statement, login, resultSet));
        scene.setRoot(fxmlLoader.load());
    }

    public static void query8(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UnitsAndVehicles.class.getResource("query8.fxml"));
        fxmlLoader.setController(new query8Controller(statement, login, resultSet));
        scene.setRoot(fxmlLoader.load());
    }
}
