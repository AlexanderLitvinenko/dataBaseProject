package ru.nsu.litvinenko.javafxbd.tables;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
public class Soldiers {
    Integer soldier_id;
    String rank_name;
    Integer rank_id;
    String first_name;
    String last_name;
    //String surname;

}
