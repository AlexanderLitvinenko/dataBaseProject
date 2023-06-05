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
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CaptainsController implements Initializable {
    String login;
    Statement statement;
    ResultSet resultSet;

    public CaptainsController(String login, Statement statement, ResultSet resultSet) {
        this.login = login;
        this.statement = statement;
        this.resultSet = resultSet;
    }

    @FXML
    TableView<Captains> table;
    @FXML
    TableColumn<Captains, String> captainFirstName;
    @FXML
    TableColumn<Captains, String> captainLastName;
    @FXML
    TableColumn<Captains, String> nameOfDivision;
    //    @FXML
//    TableColumn<Captains, String> unionIn;
    @FXML
    Button captainAddButton;
    @FXML
    TextField textFieldUnitName;
    @FXML
    Button closeButton;
    @FXML
    Button deleteButton;
    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TableColumn<Captains, String> captainRank;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Captains> data = FXCollections.observableArrayList();
        captainFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        captainFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        captainLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        captainLastName.setCellFactory(TextFieldTableCell.forTableColumn());

        nameOfDivision.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName_of_unit()));
        nameOfDivision.setCellFactory(TextFieldTableCell.forTableColumn());

        captainRank.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRank_name())));
        captainRank.setCellFactory(TextFieldTableCell.forTableColumn());
        try {
            while (resultSet.next()) {
                Captains captains = new Captains(resultSet.getString("name_of_unit"),
                        resultSet.getString("first_name"), resultSet.getString("last_name"),
                        resultSet.getString("rank_name"));
                data.add(captains);
            }
            table.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.setItems(data);
        System.out.println("captains class Controller was open");

        captainAddButton.setOnAction(actionEvent -> {
            try (Statement statement1 = statement.getConnection().createStatement()) {
                ResultSet resultSet1 = statement1.executeQuery("SELECT MAX(SOLDIER_ID) AS MX FROM SOLDIERS");
                resultSet1.next();
                int mx = resultSet1.getInt("MX") + 1;
                resultSet1 = statement1.executeQuery("SELECT TYPE_OF_UNIT_ID FROM TYPES_OF_UNITS WHERE NAME_OF_UNIT = '" + textFieldUnitName.getText() + "'");
                resultSet1.next();
                int type_of_unit_id = resultSet1.getInt("type_of_unit_id");

                //resultSet1 = statement1.executeQuery("SELECT ")
                //new Soldiers(mx, textFieldRank.getText(), rank_id, textFieldFirstName.getText(), textFieldLastName.getText());
                //Units units = new Units(mx,);
                //statement1.executeUpdate("INSERT INTO  SOLDIERS values (" + mx + ", " + rank_id + ", '" + textFieldFirstName.getText() + "' ,'" + textFieldLastName.getText() + "')");
                //data.add(units);
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
