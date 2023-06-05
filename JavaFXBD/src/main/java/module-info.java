module ru.nsu.litvinenko.javafxbd {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires ojdbc8;

    opens ru.nsu.litvinenko.javafxbd.controller;
    opens ru.nsu.litvinenko.javafxbd to javafx.fxml;
    exports ru.nsu.litvinenko.javafxbd;
}