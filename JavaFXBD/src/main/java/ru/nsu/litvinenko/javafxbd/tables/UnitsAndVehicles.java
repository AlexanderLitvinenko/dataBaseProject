package ru.nsu.litvinenko.javafxbd.tables;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitsAndVehicles {

    int vehicle_id;
    String name_of_vehicle;
    int count_of_vehicle;
}
