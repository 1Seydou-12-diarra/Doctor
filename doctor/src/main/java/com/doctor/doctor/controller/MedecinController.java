package com.doctor.doctor.controller;

import com.doctor.doctor.dto.MedecinDto;
import com.doctor.doctor.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medecins")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;

    @GetMapping
    public List<MedecinDto> getAllMedecins() {
        return medecinService.getAllMedecins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedecinDto> getMedecinById(@PathVariable Long id) {
        return ResponseEntity.ok(medecinService.getMedecinById(id));
    }

    @PostMapping
    public ResponseEntity<MedecinDto> createMedecin(@RequestBody MedecinDto medecinDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medecinService.createMedecin(medecinDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedecinDto> updateMedecin(@PathVariable Long id, @RequestBody MedecinDto medecinDto) {
        return ResponseEntity.ok(medecinService.updateMedecin(id, medecinDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable Long id) {
        medecinService.deleteMedecin(id);
        return ResponseEntity.noContent().build();
    }
}
