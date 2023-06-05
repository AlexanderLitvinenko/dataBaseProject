package ru.nsu.litvinenko.javafxbd.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import ru.nsu.litvinenko.javafxbd.controller.*;
import ru.nsu.litvinenko.javafxbd.tables.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class MenuView {
    public static void soldiers(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Soldiers.class.getResource("soldiers.fxml"));
        fxmlLoader.setController(new SoldierController(login, statement, resultSet));
        scene.setRoot(fxmlLoader.load());
    }

    public static void captains(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Soldiers.class.getResource("captains.fxml"));
        fxmlLoader.setController(new CaptainsController(login, statement, resultSet));
        scene.setRoot(fxmlLoader.load());
    }

    public static void numberOfVehicles(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NumberOfVehicles.class.getResource("numberOfVehicles.fxml"));
        fxmlLoader.setController(new NumberOfVehiclesController(login, statement, resultSet));
        scene.setRoot(fxmlLoader.load());
    }

    public static void numberOfWeapons(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NumberOfWeapons.class.getResource("numberOfWeapons.fxml"));
        fxmlLoader.setController(new NumberOfWeaponsController(login, statement, resultSet));
        scene.setRoot(fxmlLoader.load());
    }

    public static void locations(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Locations.class.getResource("locations.fxml"));
        fxmlLoader.setController(new LocationsController(login, statement, resultSet));
        scene.setRoot(fxmlLoader.load());
    }

    public static void bmps(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Soldiers.class.getResource("bmps.fxml"));
        fxmlLoader.setController(new CaptainsController(login, statement, resultSet));
        scene.setRoot(fxmlLoader.load());
    }

    public static void tractors(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Soldiers.class.getResource("tractors.fxml"));
        fxmlLoader.setController(new CaptainsController(login, statement, resultSet));
        scene.setRoot(fxmlLoader.load());
    }

    public static void divisions(Statement statement, Scene scene, ResultSet resultSet, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Divisions.class.getResource("divisions.fxml"));
        fxmlLoader.setController(new DivisionsController(login, statement, resultSet));
        scene.setRoot(fxmlLoader.load());
    }
}
