package ru.nsu.litvinenko.javafxbd.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import ru.nsu.litvinenko.javafxbd.tables.BMPS;
import ru.nsu.litvinenko.javafxbd.tables.Locations;
import ru.nsu.litvinenko.javafxbd.tables.NumberOfVehicles;
import ru.nsu.litvinenko.javafxbd.tables.UnitsAndVehicles;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class query8Controller implements Initializable {


    Statement statement;
    String login;
    ResultSet resultSet;
    @FXML
    TableView<UnitsAndVehicles> table;
    @FXML
    TableColumn<UnitsAndVehicles, String> vehicleId;
    @FXML
    TableColumn<UnitsAndVehicles, String> countColumn;
    @FXML
    TableColumn<UnitsAndVehicles, String> nameOfVehicle;
    @FXML
    Button closeButton;
    @FXML
    TextField vehicleNameTextField;
    @FXML
    Button updateButton;

    //техники которой больше 5
    public query8Controller(Statement statement, String login, ResultSet resultSet) {
        this.statement = statement;
        this.login = login;
        this.resultSet = resultSet;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<UnitsAndVehicles> data = FXCollections.observableArrayList();


        vehicleId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getVehicle_id())));
        vehicleId.setCellFactory(TextFieldTableCell.forTableColumn());

        nameOfVehicle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName_of_vehicle()));
        nameOfVehicle.setCellFactory(TextFieldTableCell.forTableColumn());

        countColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCount_of_vehicle())));
        countColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        try {
            while (resultSet.next()) {
                UnitsAndVehicles unitsAndVehicles = new UnitsAndVehicles(resultSet.getInt("vehicle_id"), resultSet.getString("name_of_vehicle"), resultSet.getInt("count_of_vehicle"));
                data.add(unitsAndVehicles);
            }
            table.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.setItems(data);
        System.out.println("query8 was open");





        closeButton.setOnAction(ActionEvent -> {
            try {
                StarterViewer.start(login, closeButton.getScene(), statement);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });

    }
}
