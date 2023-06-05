package ru.nsu.litvinenko.javafxbd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ru.nsu.litvinenko.javafxbd.model.MenuModel;
import ru.nsu.litvinenko.javafxbd.model.RequestsModel;
import ru.nsu.litvinenko.javafxbd.tables.Locations;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RequestsController implements Initializable {

    Statement statement;
    String login;
    ResultSet resultSet;
    @FXML
    Button query1Button;
    @FXML
    Button query2Button;
    @FXML
    Button query3Button;
    @FXML
    Button query4Button;
    @FXML
    Button query5Button;
    @FXML
    Button query6Button;
    @FXML
    Button query7Button;
    @FXML
    Button query8Button;
    @FXML
    Button query9Button;
    @FXML
    Button query10Button;
    @FXML
    Button query11Button;
    @FXML
    Button query12Button;
    @FXML
    Button closeButton;


    public RequestsController(Statement statement, String login) {
        this.statement = statement;
        this.login = login;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        query5Button.setOnAction(ActionEvent -> {
            try {
                RequestsModel.query5(statement, query5Button.getScene(), login);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        query8Button.setOnAction(ActionEvent -> {
            try {
                RequestsModel.query8(statement, query5Button.getScene(), login);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        closeButton.setOnAction(ActionEvent -> {
            try {
                StarterViewer.start(login, closeButton.getScene(), statement);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }
}
