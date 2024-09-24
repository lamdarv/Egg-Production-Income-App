package com.tujuhsembilan.eggproductionincome.egg_production_income.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "egg_productions")
public class EggProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "chicken_name", nullable = false, length = 100)
    private String chickenName;

    @Column(name = "production_date", nullable = false)
    private LocalDate productionDate;

    @Column(name = "eggs_produced", nullable = false)
    private int eggsProduced;

    @Column(name = "price_per_egg", nullable = false)
    private double pricePerEgg;

    // Additional constructor without ID for easier creation
    public EggProduction(String chickenName, LocalDate productionDate, int eggsProduced, double pricePerEgg) {
        this.chickenName = chickenName;
        this.productionDate = productionDate;
        this.eggsProduced = eggsProduced;
        this.pricePerEgg = pricePerEgg;
    }
}
