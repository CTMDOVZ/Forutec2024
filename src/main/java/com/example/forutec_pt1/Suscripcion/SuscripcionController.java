package com.example.forutec_pt1.Suscripcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    @Autowired
    private SuscripcionService suscripcionService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<SuscripcionDTO> getAllSuscripciones() {
        return suscripcionService.getAllSuscripciones();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public SuscripcionDTO getSuscripcionById(@PathVariable Long id) {
        return suscripcionService.getSuscripcionById(id);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<SuscripcionDTO> createSuscripcion(@RequestBody SuscripcionDTO suscripcionDTO) {
        SuscripcionDTO savedSuscripcion = suscripcionService.saveSuscripcion(suscripcionDTO);
        return new ResponseEntity<>(savedSuscripcion, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuscripcion(@PathVariable Long id) {
        suscripcionService.deleteSuscripcion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
