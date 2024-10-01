package com.doctor.doctor.controller;

import com.doctor.doctor.dto.RendezVousDto;
import com.doctor.doctor.service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    // Récupérer la liste des rendez-vous
    @GetMapping
    public ResponseEntity<List<RendezVousDto>> getAllRendezVous() {
        List<RendezVousDto> rendezVousList = rendezVousService.getListeRendezVous();
        return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
    }

    // Créer un nouveau rendez-vous
    @PostMapping
    public ResponseEntity<RendezVousDto> createRendezVous(@RequestBody RendezVousDto rendezVousDto) {
        RendezVousDto createdRendezVous = rendezVousService.createRendezVous(rendezVousDto);
        return new ResponseEntity<>(createdRendezVous, HttpStatus.CREATED);
    }

    // Mettre à jour un rendez-vous existant
    @PutMapping("/{id}")
    public ResponseEntity<RendezVousDto> updateRendezVous(
            @PathVariable Long id, @RequestBody RendezVousDto rendezVousDto) {
        RendezVousDto updatedRendezVous = rendezVousService.updateRendezVous(id, rendezVousDto);
        return new ResponseEntity<>(updatedRendezVous, HttpStatus.OK);
    }

    // Supprimer un rendez-vous
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        rendezVousService.deleteRendezVous(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
