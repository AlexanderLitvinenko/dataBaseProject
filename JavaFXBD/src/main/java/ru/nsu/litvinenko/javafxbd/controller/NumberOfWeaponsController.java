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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.nsu.litvinenko.javafxbd.tables.NumberOfVehicles;
import ru.nsu.litvinenko.javafxbd.tables.NumberOfWeapons;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NumberOfWeaponsController implements Initializable {

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
    TableView<NumberOfWeapons> table;
    @FXML
    TableColumn<NumberOfWeapons, String> weaponId;
    @FXML
    TableColumn<NumberOfWeapons, String> weaponName;

    @FXML
    TextField nameWeaponTextField;
    @FXML
    TextField attributeValueTextField;
    @FXML
    Text attributeText;

    String fromWitchTable = null;

    public NumberOfWeaponsController(String login, Statement statement, ResultSet resultSet) {
        this.statement = statement;
        this.login = login;
        this.resultSet = resultSet;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<NumberOfWeapons> data = FXCollections.observableArrayList();


        weaponId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getWeapon_id())));
        weaponId.setCellFactory(TextFieldTableCell.forTableColumn());

        weaponName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName_of_weapon()));
        weaponName.setCellFactory(TextFieldTableCell.forTableColumn());

        try {
            while (resultSet.next()) {
                NumberOfWeapons numberOfWeapons = new NumberOfWeapons(resultSet.getInt("weapon_id"),
                        resultSet.getString("name_of_weapon"));
                data.add(numberOfWeapons);
            }
            table.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.setItems(data);
        System.out.println("NumberOfWeapons Class Controller was open");

        nameWeaponTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String s = newValue.toLowerCase();
            if (newValue.equals("carabine")) {
                fromWitchTable = "CARABINES";
                attributeText.setText("Тип карабина: ");
            } else if (newValue.equals("rocket weapon")) {
                fromWitchTable = "ROCKET_WEAPONS";
                attributeText.setText("Дальность поражения: ");
            } else if (newValue.equals("automatic weapon")) {
                fromWitchTable = "AUTOMATIC_WEAPONS";
                attributeText.setText("Калибр: ");
            } else {
                attributeText.setText("Введите тип(carabine|rocket weapon|automatic weapon)");
            }
        });


        addButton.setOnAction(actionEvent -> {
            if (nameWeaponTextField.getText() != null || attributeValueTextField.getText() != null) {

                try (Statement statement1 = statement.getConnection().createStatement()) {
                    int mx, mx1 = 0, mx2 = 0;
                    if (fromWitchTable != null) {
                        ResultSet resultSet1 = statement1.executeQuery("SELECT MAX(WEAPON_ID) AS MX FROM " + fromWitchTable);
                        resultSet1.next();
                        mx1 = resultSet1.getInt("MX") + 1;
                    } else {
                        ResultSet resultSet1 = statement1.executeQuery("SELECT MAX(WEAPON_ID) AS MX FROM NUMBER_OF_WEAPONS");
                        resultSet1.next();
                        mx2 = resultSet1.getInt("MX") + 1;
                    }
                    if (mx1 >= mx2) {
                        mx = mx1;
                    } else {
                        mx = mx2;
                    }

                    NumberOfWeapons numberOfWeapons = new NumberOfWeapons(mx, nameWeaponTextField.getText());
                    //statement1.executeUpdate("INSERT INTO  NUMBER_OF_WEAPONS values (" + mx + ", '" + nameWeaponTextField + "')");
                    addToAnotherTable(statement1, mx, nameWeaponTextField.getText(), attributeValueTextField.getText());

                    data.add(numberOfWeapons);
                    table.refresh();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
//        updateButton.setOnAction(ActionEvent -> {
//            int selectedIndexRow = table.getSelectionModel().getSelectedIndex();
//            if (selectedIndexRow != -1) {
//                try (Statement statement1 = statement.getConnection().createStatement()) {
//                    statement1.executeUpdate("UPDATE NUMBER_OF_WEAPONS SET NAME_OF_WEAPON ='" + nameWeaponTextField.getText() + "'" + "WHERE WEAPON_ID=" + selectedIndexRow);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            table.refresh();
//        });
        weaponName.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            NumberOfWeapons numberOfWeapons = table.getItems().get(row);
            numberOfWeapons.setName_of_weapon(newValue);
            table.refresh();
        });

        deleteButton.setOnAction(ActionEvent -> {
            int selectedIndexRow = table.getSelectionModel().getSelectedIndex();
            if (selectedIndexRow != -1) {
                NumberOfWeapons numberOfWeapons = table.getItems().get(selectedIndexRow);
                try {
                    Statement statement1 = statement.getConnection().createStatement();
                    statement1.executeUpdate("DELETE FROM NUMBER_OF_WEAPONS WHERE WEAPON_ID =" + numberOfWeapons.getWeapon_id());
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

    private void addToAnotherTable(Statement statement1, int mx, String nameOfWeapon, String attributeValue) throws SQLException {
        String weaponName = nameOfWeapon.toLowerCase();
        if (weaponName.equals("carabine")) {
            statement1.executeUpdate("INSERT INTO CARABINES(WEAPON_ID,CARABINE_TYPE) values (" + mx + "," + attributeValue + ")");
            System.out.println("new value was added into carabines");
        }
        if (weaponName.equals("rocket weapon")) {
            statement1.executeUpdate("INSERT INTO ROCKET_WEAPONS(WEAPON_ID,RANGE) values (" + mx + "," + attributeValue + ")");
            System.out.println("new value was added into rocket weapons");
        }
        if (weaponName.equals("automatic weapon")) {
            statement1.executeUpdate("INSERT INTO AUTOMATIC_WEAPONS(WEAPON_ID,CALIBER) values (" + mx + "," + attributeValue + ")");
            System.out.println("new value was added into automatic weapons");
        }
    }
}


