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
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.nsu.litvinenko.javafxbd.tables.NumberOfVehicles;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NumberOfVehiclesController implements Initializable {

    Statement statement;
    ResultSet resultSet;
    String login;
    @FXML
    Button closeButton;
    @FXML
    Button addButton;
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;


    @FXML
    TableView<NumberOfVehicles> table;
    @FXML
    TableColumn<NumberOfVehicles, String> vehicleId;
    @FXML
    TableColumn<NumberOfVehicles, String> vehicleName;

    @FXML
    TextField nameVehicleTextField;

    @FXML
    Text attributeText;
    @FXML
    TextField attributeValueTextField;

    String fromWitchTable;

    public NumberOfVehiclesController(String login, Statement statement, ResultSet resultSet) {
        this.statement = statement;
        this.login = login;
        this.resultSet = resultSet;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<NumberOfVehicles> data = FXCollections.observableArrayList();


        vehicleId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getVehicle_id())));
        vehicleId.setCellFactory(TextFieldTableCell.forTableColumn());

        vehicleName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName_of_vehicle()));
        vehicleName.setCellFactory(TextFieldTableCell.forTableColumn());
        try {
            while (resultSet.next()) {
                NumberOfVehicles numberOfVehicles = new NumberOfVehicles(resultSet.getInt("vehicle_id"),
                        resultSet.getString("name_of_vehicle"));
                data.add(numberOfVehicles);
            }
            table.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        nameVehicleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("bmp") || newValue.equals("BMP")) {
                fromWitchTable = "BMPS";
                attributeText.setText("Максимальная скорость: ");
            } else if (newValue.equals("tractor") || newValue.equals("TRACTOR")) {
                fromWitchTable = "TRACTORS";
                attributeText.setText("Грузоподъемность: ");
            } else if (newValue.equals("artillery") || newValue.equals("ARTILLERY")) {
                fromWitchTable = "ARTILLERY";
                attributeText.setText("Дальность поражения: ");
            } else if (newValue.equals("motor transport") || newValue.equals("MOTOR TRANSPORT")) {
                fromWitchTable = "MOTOR_TRANSPORTS";
                attributeText.setText("Тип транспорта: ");
            } else {
                attributeText.setText("Введите тип(bmp|tractor|artillery|motor transport)");
            }
        });

        table.setItems(data);
        System.out.println("NumberOfVehicles Class Controller was open");

        addButton.setOnAction(actionEvent -> {
            //System.out.println("addButton was pressed");
            if (nameVehicleTextField.getText() != null || attributeValueTextField.getText() != null) {
                try (Statement statement1 = statement.getConnection().createStatement()) {
                    int mx;
                    if (fromWitchTable != null) {
                        ResultSet resultSet1 = statement1.executeQuery("SELECT MAX(VEHICLE_ID) AS MX FROM " + fromWitchTable);
                        resultSet1.next();
                        mx = resultSet1.getInt("MX") + 1;
                    } else {
                        ResultSet resultSet1 = statement1.executeQuery("SELECT MAX(VEHICLE_ID) AS MX FROM NUMBER_OF_VEHICLES");
                        resultSet1.next();
                        mx = resultSet1.getInt("MX") + 1;
                    }

                    NumberOfVehicles numberOfVehicles = new NumberOfVehicles(mx, nameVehicleTextField.getText());
                    //  statement1.executeUpdate("INSERT INTO  NUMBER_OF_VEHICLES values (" + mx + ", '" + nameVehicleTextField + "')");
                    //attributeUniqueValue = attributeValueTextField.getText();
                    addToAnotherTable(statement1, mx, nameVehicleTextField.getText(), attributeValueTextField.getText());
                    data.add(numberOfVehicles);
                    table.refresh();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        vehicleName.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            NumberOfVehicles numberOfVehicles = table.getItems().get(row);
            numberOfVehicles.setName_of_vehicle(newValue);
            table.refresh();
        });
        deleteButton.setOnAction(ActionEvent -> {
            int selectedIndexRow = table.getSelectionModel().getSelectedIndex();
            if (selectedIndexRow != -1) {
                NumberOfVehicles numberOfVehicles = table.getItems().get(selectedIndexRow);
                try {
                    Statement statement1 = statement.getConnection().createStatement();
                    statement1.executeUpdate("DELETE FROM NUMBER_OF_VEHICLES WHERE VEHICLE_ID =" + numberOfVehicles.getVehicle_id());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                data.remove(selectedIndexRow);
                table.getItems().remove(selectedIndexRow);
            }
        });


        closeButton.setOnAction(actionEvent -> {
            try {
                StarterViewer.start(login, closeButton.getScene(), statement);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }


    private void addToAnotherTable(Statement statement1, int mx, String nameOfVehicle, String attributeValue) throws SQLException {
        String vehicleName = nameOfVehicle.toLowerCase();
        if (vehicleName.equals("bmp")) {
            statement1.executeUpdate("INSERT INTO BMPS(VEHICLE_ID, MAX_SPEED) values (" + mx + ", " + attributeValue + ")");
            System.out.println("new value was added into bmps");
        }
        if (vehicleName.equals("tractor")) {
            statement1.executeUpdate("INSERT INTO TRACTORS (VEHICLE_ID,LOAD_CAPACITY) values (" + mx + "," + attributeValue + ");");
            System.out.println("new value was added into tractors");
        }
        if (vehicleName.equals("artillery")) {
            statement1.executeUpdate("INSERT INTO ARTILLERY(VEHICLE_ID, RANGE) values (" + mx + "," + attributeValue + ")");
            System.out.println("new value was added into artillery");
        }
        if (vehicleName.equals("motor transport")) {
            statement1.executeUpdate("INSERT INTO MOTOR_TRANSPORTS(VEHICLE_ID,MOTOR_TRANSPORT_TYPE) values (" + mx + "," + attributeValue + ")");
            System.out.println("new value was added into motor transport");
        }
    }
}

