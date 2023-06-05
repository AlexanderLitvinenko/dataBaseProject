package ru.nsu.litvinenko.javafxbd;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.nsu.litvinenko.javafxbd.model.StarterModel;

import java.net.URL;
import java.util.ResourceBundle;

public class СonnectController implements Initializable {
    @FXML
    private TextField textFiledPort;
    @FXML
    private TextField textFieldIp;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Button buttonConnect;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonConnect.setOnAction(actionEvent -> StarterModel.start(textFieldIp.getText(),
                textFiledPort.getText(), textFieldLogin.getText(), textFieldPassword.getText(),textFieldPassword.getScene()));
    }
}