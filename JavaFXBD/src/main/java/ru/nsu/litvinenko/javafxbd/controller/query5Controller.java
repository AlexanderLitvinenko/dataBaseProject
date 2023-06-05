package ru.nsu.litvinenko.javafxbd.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import ru.nsu.litvinenko.javafxbd.tables.Locations;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class query5Controller implements Initializable {


    Statement statement;
    String login;
    ResultSet resultSet;
    @FXML
    TableView<Locations> table;
    @FXML
    TableColumn<Locations, String> locationId;
    @FXML
    TableColumn<Locations, String> locationRegion;
    @FXML
    Button closeButton;


    public query5Controller(Statement statement, String login, ResultSet resultSet) {
        this.statement = statement;
        this.login = login;
        this.resultSet = resultSet;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Locations> data = FXCollections.observableArrayList();


        locationId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLocation_id())));
        locationId.setCellFactory(TextFieldTableCell.forTableColumn());

        locationRegion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegion()));
        locationRegion.setCellFactory(TextFieldTableCell.forTableColumn());


        try {
            while (resultSet.next()) {
                Locations locations = new Locations(resultSet.getInt("location_id"), resultSet.getString("region"),"");
                data.add(locations);
            }
            table.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.setItems(data);
        System.out.println("query5 was open");

        closeButton.setOnAction(ActionEvent -> {
            try {
                StarterViewer.start(login, closeButton.getScene(), statement);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });

    }
}
