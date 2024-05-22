package com.example.forutec_pt1.Publicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public List<PublicacionDTO> getAllPublicaciones() {
        return publicacionService.getAllPublicaciones();
    }

    @GetMapping("/{id}")
    public PublicacionDTO getPublicacionById(@PathVariable Long id) {
        return publicacionService.getPublicacionById(id);
    }

    @PostMapping
    public PublicacionDTO savePublicacion(@RequestBody PublicacionDTO publicacionDTO) {
        return publicacionService.savePublicacion(publicacionDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePublicacion(@PathVariable Long id) {
        publicacionService.deletePublicacion(id);
    }
}
