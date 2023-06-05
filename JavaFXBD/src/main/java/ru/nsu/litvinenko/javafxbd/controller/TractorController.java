package ru.nsu.litvinenko.javafxbd.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.tables.Tractors;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TractorController implements Initializable {

    String login;
    Statement statement;
    ResultSet resultSet;
    @FXML
    TableView<Tractors> tableTractors;
    @FXML
    TableColumn<Tractors, String> columnId;
    @FXML
    TableColumn<Tractors, String> columnCapacity;
    @FXML
    TextField textFieldCapacity;
    @FXML
    Button addTractorButton;

    public TractorController(String login, Statement statement, ResultSet resultSet) {
        this.login = login;
        this.statement = statement;
        this.resultSet = resultSet;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Tractors> data = FXCollections.observableArrayList();


        columnId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTractor_id())));
        columnId.setCellFactory(TextFieldTableCell.forTableColumn());

        columnCapacity.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLoad_capacity())));
        columnCapacity.setCellFactory(TextFieldTableCell.forTableColumn());

        try {
            while (resultSet.next()) {
                Tractors tractors = new Tractors(resultSet.getInt("tractor_id"), resultSet.getInt("load_capacity"));
                data.add(tractors);
            }
            tableTractors.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableTractors.setItems(data);
        System.out.println("Soldiers Class Controller was open");

        addTractorButton.setOnAction(actionEvent -> {
            try (Statement statement1 = statement.getConnection().createStatement()) {
                ResultSet resultSet1 = statement1.executeQuery("SELECT MAX(VEHICLE_ID) AS MX FROM NUMBER_OF_VEHICLES");
                resultSet1.next();
                int mx = resultSet1.getInt("MX") + 1;
                Tractors tractor = new Tractors(mx, Integer.valueOf(textFieldCapacity.getText()));
                statement1.executeUpdate("INSERT INTO  TRACTORS values (" + mx + ", " + tractor.getLoad_capacity() + ")");
                data.add(tractor);
                tableTractors.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
