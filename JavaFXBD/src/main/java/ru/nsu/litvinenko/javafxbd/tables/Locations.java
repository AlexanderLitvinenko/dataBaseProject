package ru.nsu.litvinenko.javafxbd.tables;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Locations {
    int location_id;
    String region;
    String name_of_unit;
}
