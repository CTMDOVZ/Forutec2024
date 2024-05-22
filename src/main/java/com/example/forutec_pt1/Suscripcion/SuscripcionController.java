package com.example.forutec_pt1.Suscripcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    @Autowired
    private SuscripcionService suscripcionService;

    @GetMapping
    public List<SuscripcionDTO> getAllSuscripciones() {
        return suscripcionService.getAllSuscripciones();
    }

    @GetMapping("/{id}")
    public SuscripcionDTO getSuscripcionById(@PathVariable Long id) {
        return suscripcionService.getSuscripcionById(id);
    }

    @PostMapping
    public SuscripcionDTO saveSuscripcion(@RequestBody SuscripcionDTO suscripcionDTO) {
        return suscripcionService.saveSuscripcion(suscripcionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSuscripcion(@PathVariable Long id) {
        suscripcionService.deleteSuscripcion(id);
    }
}

