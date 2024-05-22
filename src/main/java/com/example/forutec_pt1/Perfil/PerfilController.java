package com.example.forutec_pt1.Perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public List<PerfilDTO> getAllPerfiles() {
        return perfilService.getAllPerfiles();
    }

    @GetMapping("/{id}")
    public PerfilDTO getPerfilById(@PathVariable Long id) {
        return perfilService.getPerfilById(id);
    }

    @PostMapping
    public PerfilDTO savePerfil(@RequestBody PerfilDTO perfilDTO) {
        return perfilService.savePerfil(perfilDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePerfil(@PathVariable Long id) {
        perfilService.deletePerfil(id);
    }
}
