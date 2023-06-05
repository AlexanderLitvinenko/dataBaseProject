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
import ru.nsu.litvinenko.javafxbd.tables.Captains;
import ru.nsu.litvinenko.javafxbd.tables.Divisions;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DivisionsController implements Initializable {

    Statement statement;
    String login;
    ResultSet resultSet;

    @FXML
    TableView<Divisions> table;
    @FXML
    TableColumn<Divisions, String> unit_id;
    @FXML
    TableColumn<Divisions, String> idColumn;
    @FXML
    TableColumn<Divisions, String> unitColumn;
    @FXML
    Button closeButton;
    @FXML
    Button deleteButton;

    public DivisionsController(String login, Statement statement, ResultSet resultSet) {
        this.statement = statement;
        this.login = login;
        this.resultSet = resultSet;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Divisions> data = FXCollections.observableArrayList();
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUnit_id())));
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        unitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName_of_unit()));
        unitColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        try {
            while (resultSet.next()) {
                Divisions divisions = new Divisions(resultSet.getInt("unit_id"), resultSet.getString("name_of_unit"));
                data.add(divisions);
            }
            table.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.setItems(data);
        System.out.println("divisions class Controller was open");

        deleteButton.setOnAction(ActionEvent -> {
            int selectedIndexRow = table.getSelectionModel().getSelectedIndex();
            if (selectedIndexRow != -1) {
                Divisions divisions = table.getItems().get(selectedIndexRow);
                try {
                    Statement statement1 = statement.getConnection().createStatement();
                    statement1.executeUpdate("DELETE FROM UNITS_AND_CAPTAINS WHERE UNIT_ID =" + divisions.getUnit_id());
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
}
