package com.tujuhsembilan.eggproductionincome.egg_production_income.service;

import com.tujuhsembilan.eggproductionincome.egg_production_income.repository.EggProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EggSummaryService {

    @Autowired
    private EggProductionRepository eggProductionRepository;

    public int getTotalEggsProduced() {
        return eggProductionRepository.findAll()
                .stream()
                .mapToInt(production -> production.getEggsProduced())
                .sum();
    }
}
