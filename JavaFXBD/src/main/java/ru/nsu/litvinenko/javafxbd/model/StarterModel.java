package ru.nsu.litvinenko.javafxbd.model;

import javafx.scene.Scene;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class StarterModel {
    public static void start(String ip, String port, String login, String password, Scene scene) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Statement statement = bdPostGet(ip, port, login, password);
            StarterViewer.start(login, scene, statement);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Statement bdPostGet(String ip, String port, String login, String password) throws SQLException {
        Properties connectProps = new Properties();
        connectProps.setProperty("user", login);
        connectProps.setProperty("password", password);
        connectProps.setProperty("oracle.net.CONNECT_TIMEOUT", "500");
        return DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":" + port + ":", connectProps).createStatement();
    }
}

