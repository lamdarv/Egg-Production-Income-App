package com.tujuhsembilan.eggproductionincome.egg_production_income.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EggProductionDTO {
    private String chickenName;
    private int eggsProduced;
    private LocalDate productionDate;
    private double pricePerEgg;
}
