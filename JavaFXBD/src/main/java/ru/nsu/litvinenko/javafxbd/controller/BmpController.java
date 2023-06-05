package ru.nsu.litvinenko.javafxbd.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class BmpController implements Initializable {
    String login;
    Statement statement;
    ResultSet resultSet;

    public BmpController(String login, Statement statement, ResultSet resultSet) {
        this.login = login;
        this.statement = statement;
        this.resultSet = resultSet;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
