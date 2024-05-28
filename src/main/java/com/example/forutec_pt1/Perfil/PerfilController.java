package com.example.forutec_pt1.Perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<PerfilDTO> getAllPerfiles() {
        return perfilService.getAllPerfiles();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPerfilById(@PathVariable Long id) {
        PerfilDTO perfil = perfilService.getPerfilById(id);
        return ResponseEntity.ok(perfil);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<PerfilDTO> savePerfil(@RequestBody PerfilDTO perfilDTO) {
        PerfilDTO savedPerfil = perfilService.savePerfil(perfilDTO);
        return ResponseEntity.status(201).body(savedPerfil);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        perfilService.deletePerfil(id);
        return ResponseEntity.noContent().build();
    }
}
