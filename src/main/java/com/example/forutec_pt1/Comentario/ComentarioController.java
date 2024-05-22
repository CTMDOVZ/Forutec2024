package com.example.forutec_pt1.Comentario;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ComentarioDTO getComentarioById(@PathVariable Long id) {
        return comentarioService.getComentarioById(id);
    }

    @PostMapping
    public ComentarioDTO saveComentario(@RequestBody ComentarioDTO comentarioDTO) {
        return comentarioService.saveComentario(comentarioDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteComentario(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
    }
}

