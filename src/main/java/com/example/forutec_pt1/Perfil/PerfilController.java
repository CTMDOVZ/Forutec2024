package com.example.forutec_pt1.Perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<PerfilDTO>> getAllPerfiles() {
        List<PerfilDTO> perfiles = perfilService.getAllPerfiles();
        return new ResponseEntity<>(perfiles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPerfilById(@PathVariable Long id) {
        PerfilDTO perfilDTO = perfilService.getPerfilById(id);
        return new ResponseEntity<>(perfilDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PerfilDTO> savePerfil(@RequestBody PerfilDTO perfilDTO) {
        PerfilDTO savedPerfil = perfilService.savePerfil(perfilDTO);
        return new ResponseEntity<>(savedPerfil, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> updatePerfil(@PathVariable Long id, @RequestBody PerfilDTO perfilDTO) {
        PerfilDTO updatedPerfil = perfilService.updatePerfil(id, perfilDTO);
        return new ResponseEntity<>(updatedPerfil, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        perfilService.deletePerfil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
