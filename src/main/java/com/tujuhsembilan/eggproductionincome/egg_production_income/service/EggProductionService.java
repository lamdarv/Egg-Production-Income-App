package com.tujuhsembilan.eggproductionincome.egg_production_income.service;

import com.tujuhsembilan.eggproductionincome.egg_production_income.dto.EggProductionDTO;
import com.tujuhsembilan.eggproductionincome.egg_production_income.model.EggProduction;
import com.tujuhsembilan.eggproductionincome.egg_production_income.repository.EggProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EggProductionService {

    @Autowired
    private EggProductionRepository eggProductionRepository;

    public EggProduction addEggProduction(EggProductionDTO eggProductionDTO) {
        // Membuat entitas EggProduction dan menyimpan ke database
        EggProduction eggProduction = new EggProduction(
                eggProductionDTO.getChickenName(),
                eggProductionDTO.getProductionDate() != null ? eggProductionDTO.getProductionDate() : LocalDate.now(),
                eggProductionDTO.getEggsProduced(),
                eggProductionDTO.getPricePerEgg()
        );
        return eggProductionRepository.save(eggProduction);
    }


    public List<EggProduction> getAllEggProduction() {
        return eggProductionRepository.findAll();
    }

    public EggProduction getEggProductionById(Long id) {
        return eggProductionRepository.findById(id).orElse(null);
    }

    public boolean deleteEggProduction(Long id) {
        if (eggProductionRepository.existsById(id)) {
            eggProductionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public EggProduction updateEggProduction(Long id, EggProductionDTO eggProductionDTO) {
        EggProduction eggProduction = getEggProductionById(id);
        if (eggProduction != null) {
            eggProduction.setChickenName(eggProductionDTO.getChickenName());
            eggProduction.setEggsProduced(eggProductionDTO.getEggsProduced());
            eggProduction.setProductionDate(eggProductionDTO.getProductionDate() != null ? eggProductionDTO.getProductionDate() : LocalDate.now());
            return eggProductionRepository.save(eggProduction);
        }
        return null;
    }

    public double calculateRevenue(LocalDate startDate, LocalDate endDate) {
        // Ambil semua produksi telur dalam rentang tanggal yang diberikan
        List<EggProduction> productions = eggProductionRepository.findAllByProductionDateBetween(startDate, endDate);

        // Hitung total revenue (jumlah telur * harga per telur)
        return productions.stream()
                .mapToDouble(production -> production.getEggsProduced() * production.getPricePerEgg())
                .sum();
    }
}