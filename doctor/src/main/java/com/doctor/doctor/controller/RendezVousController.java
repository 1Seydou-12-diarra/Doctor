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

    @GetMapping
    public List<RendezVousDto> getAllRendezVous() {
        return rendezVousService.getAllRendezVous();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVousDto> getRendezVousById(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.getRendezVousById(id));
    }

    @PostMapping
    public ResponseEntity<RendezVousDto> createRendezVous(@RequestBody RendezVousDto rendezVousDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rendezVousService.createRendezVous(rendezVousDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVousDto> updateRendezVous(@PathVariable Long id, @RequestBody RendezVousDto rendezVousDto) {
        return ResponseEntity.ok(rendezVousService.updateRendezVous(id, rendezVousDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        rendezVousService.deleteRendezVous(id);
        return ResponseEntity.noContent().build();
    }
}

