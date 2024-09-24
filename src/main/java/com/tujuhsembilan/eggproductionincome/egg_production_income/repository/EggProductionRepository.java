package com.tujuhsembilan.eggproductionincome.egg_production_income.repository;

import com.tujuhsembilan.eggproductionincome.egg_production_income.model.EggProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EggProductionRepository extends JpaRepository<EggProduction, Long> {
    List<EggProduction> findAllByProductionDateBetween(LocalDate startDate, LocalDate endDate);
}
