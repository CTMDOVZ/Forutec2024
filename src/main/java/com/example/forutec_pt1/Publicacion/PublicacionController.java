package com.example.forutec_pt1.Publicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<PublicacionDTO> getAllPublicaciones() {
        return publicacionService.getAllPublicaciones();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public PublicacionDTO getPublicacionById(@PathVariable Long id) {
        return publicacionService.getPublicacionById(id);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
    @PostMapping
    public PublicacionDTO savePublicacion(@RequestBody PublicacionDTO publicacionDTO) {
        return publicacionService.savePublicacion(publicacionDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePublicacion(@PathVariable Long id) {
        publicacionService.deletePublicacion(id);
    }
}
