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
import javafx.scene.layout.VBox;
import ru.nsu.litvinenko.javafxbd.tables.NumberOfVehicles;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SoldierController implements Initializable {
    String login;
    Statement statement;
    ResultSet resultSet;
    @FXML
    VBox soldierVbox;
    @FXML
    TableView<Soldiers> table;
    @FXML
    TableColumn<Soldiers, String> soldierFirstName;
    @FXML
    TableColumn<Soldiers, String> soldierLastName;
    @FXML
    TableColumn<Soldiers, String> soldierSurname;
    @FXML
    TableColumn<Soldiers, String> soldierRank;
    @FXML
    Button soldierAddButton;
    @FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldLastName;
    @FXML
    TextField textFieldRank;
    @FXML
    Button closeButton;
    @FXML
    Button deleteButton;

    public SoldierController(String login, Statement statement, ResultSet resultSet) {
        this.login = login;
        this.statement = statement;
        this.resultSet = resultSet;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Soldiers> data = FXCollections.observableArrayList();


        soldierFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        soldierFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        soldierLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        soldierLastName.setCellFactory(TextFieldTableCell.forTableColumn());

//        soldierSurname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
//        soldierSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        soldierRank.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRank_name())));
        soldierRank.setCellFactory(TextFieldTableCell.forTableColumn());
        try {
            while (resultSet.next()) {
                Soldiers soldiers = new Soldiers(resultSet.getInt("soldier_id"), resultSet.getString("rank_name"), resultSet.getInt("rank_id"), resultSet.getString("first_name"), resultSet.getString("last_name"));
                data.add(soldiers);
            }
            table.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.setItems(data);
        System.out.println("Soldiers Class Controller was open");

        soldierAddButton.setOnAction(actionEvent -> {
            try (Statement statement1 = statement.getConnection().createStatement()) {
                ResultSet resultSet1 = statement1.executeQuery("SELECT MAX(SOLDIER_ID) AS MX FROM SOLDIERS");
                resultSet1.next();
                int mx = resultSet1.getInt("MX") + 1;
                resultSet1 = statement1.executeQuery("SELECT RANK_ID FROM RANKS WHERE RANK_NAME = '" + textFieldRank.getText() + "'");
                resultSet1.next();
                int rank_id = resultSet1.getInt("rank_id");
                Soldiers soldiers = new Soldiers(mx, textFieldRank.getText(), rank_id, textFieldFirstName.getText(), textFieldLastName.getText());
                statement1.executeUpdate("INSERT INTO  SOLDIERS values (" + mx + ", " + rank_id + ", '" + textFieldFirstName.getText() + "' ,'" + textFieldLastName.getText() + "')");
                data.add(soldiers);
                table.refresh();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        soldierFirstName.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Soldiers soldiers = table.getItems().get(row);
            soldiers.setFirst_name(newValue);
            table.refresh();
        });
        soldierLastName.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Soldiers soldiers = table.getItems().get(row);
            soldiers.setLast_name(newValue);
            table.refresh();
        });


        deleteButton.setOnAction(ActionEvent -> {
            int selectedIndexRow = table.getSelectionModel().getSelectedIndex();
            if (selectedIndexRow != -1) {
                Soldiers soldiers = table.getItems().get(selectedIndexRow);
                try {
                    Statement statement1 = statement.getConnection().createStatement();
                    statement1.executeUpdate("DELETE FROM SOLDIERS WHERE SOLDIER_ID =" + soldiers.getSoldier_id());
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
