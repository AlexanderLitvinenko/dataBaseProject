package ru.nsu.litvinenko.javafxbd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.nsu.litvinenko.javafxbd.model.MenuModel;
import ru.nsu.litvinenko.javafxbd.tables.Locations;
import ru.nsu.litvinenko.javafxbd.tables.Soldiers;
import ru.nsu.litvinenko.javafxbd.view.MenuView;
import ru.nsu.litvinenko.javafxbd.view.StarterViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuController implements Initializable {
    String login;
    Statement statement;
    @FXML
    Button openSoldiersTableButton;
    @FXML
    Button openCaptainsTableButton;
    @FXML
    Button openNumberOfVehiclesButton;
    @FXML
    Button openNumberOfWeaponsButton;
    @FXML
    Button openRequestsButton;
    @FXML
    Button openLocationsButton;
    @FXML
    Button openDivisionsInfoButton;
    @FXML
    Button aboutButton;

    public MenuController(String login, Statement statement) {
        this.login = login;
        this.statement = statement;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hellllllo");
        openSoldiersTableButton.setOnAction(ActionEvent -> {
            try {
                MenuModel.soldiers(statement, openSoldiersTableButton.getScene(), login);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        openCaptainsTableButton.setOnAction(ActionEvent -> {
            try {
                MenuModel.captains(statement, openCaptainsTableButton.getScene(), login);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        openNumberOfVehiclesButton.setOnAction(ActionEvent -> {
            try {
                MenuModel.numberOfVehicles(statement, openNumberOfVehiclesButton.getScene(), login);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        openNumberOfWeaponsButton.setOnAction(ActionEvent -> {
            try {
                MenuModel.numberOfWeapons(statement, openNumberOfWeaponsButton.getScene(), login);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        openRequestsButton.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Locations.class.getResource("requests.fxml"));
                fxmlLoader.setController(new RequestsController(statement, login));
                openRequestsButton.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        openLocationsButton.setOnAction(actionEvent -> {
            try {
                MenuModel.locations(statement, openLocationsButton.getScene(), login);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        openDivisionsInfoButton.setOnAction(actionEvent -> {
            try {
                MenuModel.divisions(statement, openDivisionsInfoButton.getScene(), login);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        aboutButton.setOnAction(actionEvent -> {
            Stage informationStage = new Stage();
            informationStage.initModality(Modality.APPLICATION_MODAL);
            informationStage.setTitle("Информация");

            // Создание меток с информацией
            Label authorLabel = new Label("Автор: Александр Литвиненко");
            Label copyrightLabel = new Label("Авторские права на программу: Александр Литвиненко");
            Label aboutLabel = new Label("О программе: приложение клиента было написано Александром Литвиненко\n"+"    Мой друг: Золоторевский Андрей");

            // Создание контейнера и добавление меток
            VBox root = new VBox(10);
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(authorLabel, copyrightLabel, aboutLabel);

            // Создание сцены и установка контейнера
            Scene scene = new Scene(root, 450, 250);

            // Установка сцены на всплывающее окно
            informationStage.setScene(scene);

            // Отображение всплывающего окна
            informationStage.showAndWait();
        });
    }
}
