package ru.nsu.litvinenko.javafxbd.model;

import javafx.scene.Scene;
import ru.nsu.litvinenko.javafxbd.view.MenuView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuModel {
    public static void soldiers(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM SOLDIERS S JOIN RANKS R ON S.RANK_ID = R.RANK_ID");
        MenuView.soldiers(statement, scene, rs, login);
    }

    public static void captains(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery
                ("SELECT  TYPES_OF_UNITS.NAME_OF_UNIT,SOLDIERS.FIRST_NAME,SOLDIERS.LAST_NAME,RANKS.RANK_NAME " +
                        "FROM UNITS_AND_CAPTAINS JOIN TYPES_OF_UNITS ON UNITS_AND_CAPTAINS.TYPE_OF_UNIT_ID = " +
                        "TYPES_OF_UNITS.TYPE_OF_UNIT_ID JOIN SOLDIERS ON UNITS_AND_CAPTAINS.CAPTAIN_OF_UNIT_ID = SOLDIERS.SOLDIER_ID "
                        +"JOIN RANKS ON RANKS.RANK_ID = SOLDIERS.RANK_ID");
        MenuView.captains(statement, scene, resultSet, login);
    }

    public static void numberOfVehicles(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM NUMBER_OF_VEHICLES");
        MenuView.numberOfVehicles(statement, scene, resultSet, login);
    }

    public static void locations(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery("SELECT LOCATIONS.LOCATION_ID, REGION,NAME_OF_UNIT FROM TYPES_OF_UNITS TOU " +
                "JOIN UNITS_AND_CAPTAINS UAC ON TOU.TYPE_OF_UNIT_ID = UAC.TYPE_OF_UNIT_ID " +
                "JOIN UNITS_AND_LOCATIONS UAL ON UAC.UNIT_ID = UAL.UNIT_ID " +
                "JOIN UNITS_AND_CAPTAINS UAC2 ON UAL.UNIT_ID = UAC2.UNIT_ID " +
                "JOIN LOCATIONS ON LOCATIONS.LOCATION_ID = UAL.LOCATION_ID");
        MenuView.locations(statement, scene, resultSet, login);
    }


    public static void numberOfWeapons(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM NUMBER_OF_WEAPONS");
        MenuView.numberOfWeapons(statement, scene, resultSet, login);
    }

    public static void bmps(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM BMPS");
        MenuView.bmps(statement, scene, resultSet, login);
    }


    public static void tractors(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM TRACTORS");
        MenuView.tractors(statement, scene, resultSet, login);
    }


    public static void divisions(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery("SELECT UNIT_ID, TYPES_OF_UNITS.NAME_OF_UNIT " +
                "FROM UNITS_AND_CAPTAINS UAC " +
                "JOIN TYPES_OF_UNITS ON TYPES_OF_UNITS.TYPE_OF_UNIT_ID = UAC.TYPE_OF_UNIT_ID");
        MenuView.divisions(statement, scene, resultSet, login);
    }
}
