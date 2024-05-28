package com.example.forutec_pt1.Comentario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public List<ComentarioDTO> getAllComentarios() {
        return comentarioService.getAllComentarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDTO> getComentario(@PathVariable Long id) {
        ComentarioDTO comentario = comentarioService.getComentarioById(id);
        return ResponseEntity.ok(comentario);
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> saveComentario(@RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO savedComentario = comentarioService.saveComentario(comentarioDTO);
        return ResponseEntity.status(201).body(savedComentario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
        return ResponseEntity.noContent().build();
    }
}

