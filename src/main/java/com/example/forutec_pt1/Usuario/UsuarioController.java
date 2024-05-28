package com.example.forutec_pt1.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.getUsuarioById(id);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> saveUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO savedUsuario = usuarioService.saveUsuario(usuarioDTO);
        return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO updatedUsuario = usuarioService.updateUsuario(id, usuarioDTO);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> patchUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO patchedUsuario = usuarioService.patchUsuario(id, usuarioDTO);
        return new ResponseEntity<>(patchedUsuario, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/profile/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId2(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId2(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

}
