package com.tujuhsembilan.eggproductionincome.egg_production_income.controller;

import com.tujuhsembilan.eggproductionincome.egg_production_income.dto.EggProductionDTO;
import com.tujuhsembilan.eggproductionincome.egg_production_income.model.EggProduction;
import com.tujuhsembilan.eggproductionincome.egg_production_income.service.EggProductionService;
import com.tujuhsembilan.eggproductionincome.egg_production_income.service.EggSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eggs")
public class EggProductionController {

    @Autowired
    private EggProductionService eggProductionService;

    @Autowired
    private EggSummaryService eggSummaryService;

    // Add Egg Production
    @PostMapping("/add")
    public ResponseEntity<?> addEggProduction(@RequestBody EggProductionDTO eggProductionDTO) {
        try {
            EggProduction savedProduction = eggProductionService.addEggProduction(eggProductionDTO); // Simpan dan dapatkan entitas
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Ayam with ID " + savedProduction.getId() + " successfully added");
            response.put("data", savedProduction);  // Mengembalikan data EggProduction dengan ID
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    // Get All Egg Productions
    @GetMapping("/all")
    public ResponseEntity<?> getAllEggProductions() {
        try {
            List<EggProduction> productions = eggProductionService.getAllEggProduction();
            if (productions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No egg productions found.");
            }
            return ResponseEntity.ok(productions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    // Get Egg Production by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEggProductionById(@PathVariable Long id) {
        try {
            EggProduction eggProduction = eggProductionService.getEggProductionById(id);
            if (eggProduction == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Egg production with ID " + id + " not found.");
            }
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Egg production with ID " + id + " found");
            response.put("data", eggProduction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    // Delete Egg Production by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEggProduction(@PathVariable Long id) {
        try {
            boolean deleted = eggProductionService.deleteEggProduction(id);
            if (deleted) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Egg production with ID " + id + " successfully deleted");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Egg production with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    // Get Total Eggs Produced (Summary)
    @GetMapping("/summary")
    public ResponseEntity<?> getTotalEggsProduced() {
        try {
            int totalEggs = eggSummaryService.getTotalEggsProduced();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Total eggs produced: " + totalEggs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    // Get Revenue
    @GetMapping("/revenue")
    public ResponseEntity<?> calculateRevenue(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try {
            // Konversi string ke LocalDate
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            // Panggil service untuk menghitung revenue
            double totalRevenue = eggProductionService.calculateRevenue(start, end);

            // Kembalikan hasil
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Total revenue from " + start + " to " + end);
            response.put("revenue", totalRevenue);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}
