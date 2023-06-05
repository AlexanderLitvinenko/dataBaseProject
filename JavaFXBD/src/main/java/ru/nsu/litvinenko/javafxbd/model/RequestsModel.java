package ru.nsu.litvinenko.javafxbd.model;

import javafx.scene.Scene;
import ru.nsu.litvinenko.javafxbd.view.MenuView;
import ru.nsu.litvinenko.javafxbd.view.RequestsView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RequestsModel {
    public static void query5(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM LOCATIONS ");
        RequestsView.query5(statement, scene, rs, login);
    }

    public static void query8(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT UNITS_AND_VEHICLES.VEHICLE_ID, NUMBER_OF_VEHICLES.NAME_OF_VEHICLE, UNITS_AND_VEHICLES.COUNT_OF_VEHICLE FROM UNITS_AND_VEHICLES JOIN NUMBER_OF_VEHICLES ON UNITS_AND_VEHICLES.VEHICLE_ID = NUMBER_OF_VEHICLES.VEHICLE_ID WHERE UNITS_AND_VEHICLES.COUNT_OF_VEHICLE > 5");
        RequestsView.query8(statement, scene, rs, login);
    }


}
