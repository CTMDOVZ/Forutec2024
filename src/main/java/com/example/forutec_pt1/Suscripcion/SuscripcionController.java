package com.example.forutec_pt1.Suscripcion;

import com.example.forutec_pt1.Categoria.Categoria;
import com.example.forutec_pt1.Categoria.CategoriaRepository;
import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Publicacion.PublicacionRepository;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

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
    public Suscripcion createSuscripcion(@RequestBody Suscripcion suscripcion) {
        Usuario usuario = usuarioRepository.findById(suscripcion.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Categoria categoria = categoriaRepository.findById(suscripcion.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Publicacion publicacion = publicacionRepository.findById(suscripcion.getPublicacion().getId())
                .orElseThrow(() -> new RuntimeException("Publicacion no encontrada"));

        suscripcion.setUsuario(usuario);
        suscripcion.setCategoria(categoria);
        suscripcion.setPublicacion(publicacion);

        return suscripcionRepository.save(suscripcion);
    }

    @DeleteMapping("/{id}")
    public void deleteSuscripcion(@PathVariable Long id) {
        suscripcionService.deleteSuscripcion(id);
    }
}
