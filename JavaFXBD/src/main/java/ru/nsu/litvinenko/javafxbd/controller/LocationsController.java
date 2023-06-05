package ru.nsu.litvinenko.javafxbd.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import ru.nsu.litvinenko.javafxbd.tables.Locations;
import ru.nsu.litvinenko.javafxbd.tables.NumberOfVehicles;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LocationsController implements Initializable {


    private ResultSet resultSet;
    private String login;
    private Statement statement;

    @FXML
    TableView<Locations> table;
    @FXML
    TableColumn<Locations, String> locationId;
    @FXML
    TableColumn<Locations, String> region;
    @FXML
    TableColumn<Locations, String> unitName;
    @FXML
    Button closeButton;
    @FXML
    Button addButton;
    @FXML
    Button deleteButton;
    @FXML
    TextField regionNameTextField;

    public LocationsController(String login, Statement statement, ResultSet resultSet) {
        this.login = login;
        this.statement = statement;
        this.resultSet = resultSet;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Locations> data = FXCollections.observableArrayList();


        locationId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLocation_id())));
        locationId.setCellFactory(TextFieldTableCell.forTableColumn());

        region.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegion()));
        region.setCellFactory(TextFieldTableCell.forTableColumn());

        unitName.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getName_of_unit())));
        unitName.setCellFactory(TextFieldTableCell.forTableColumn());
        try {
            while (resultSet.next()) {
                Locations locations = new Locations(resultSet.getInt("location_id"), resultSet.getString("region"), resultSet.getString("name_of_unit"));
                data.add(locations);
            }
            table.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.setItems(data);
        System.out.println("Soldiers Class Controller was open");

        addButton.setOnAction(actionEvent -> {
            try (Statement statement1 = statement.getConnection().createStatement()) {
                ResultSet resultSet1 = statement1.executeQuery("SELECT MAX(LOCATION_ID) AS MX FROM LOCATIONS");
                resultSet1.next();
                int mx = resultSet1.getInt("MX") + 1;

                String regionNameString = regionNameTextField.getText().toUpperCase();
                Locations locations = new Locations(mx, regionNameString, "");
                statement1.executeUpdate("INSERT INTO  LOCATIONS values (" + mx + ", '" + locations.getRegion() + "')");
                data.add(locations);
                table.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        region.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Locations locations = table.getItems().get(row);
            locations.setRegion(newValue);
            table.refresh();
        });


        closeButton.setOnAction(actionEvent -> {
            try {
                StarterViewer.start(login, closeButton.getScene(), statement);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }
}
