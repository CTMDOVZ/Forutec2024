package com.example.forutec_pt1.Usuario;

import com.example.forutec_pt1.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Usuario obtenerPorId2(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public UsuarioDTO getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return convertToDTO(usuario);
    }

    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = convertToEntity(usuarioDTO);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return convertToDTO(savedUsuario);
    }
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setCorreoInstitucional(usuarioDTO.getCorreoInstitucional());
        Usuario updatedUsuario = usuarioRepository.save(usuarioExistente);
        return convertToDTO(updatedUsuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuarioRepository.delete(usuario);
    }
    public UsuarioDTO patchUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        if (usuarioDTO.getNombre() != null) {
            usuarioExistente.setNombre(usuarioDTO.getNombre());
        }
        if (usuarioDTO.getApellido() != null) {
            usuarioExistente.setApellido(usuarioDTO.getApellido());
        }
        if (usuarioDTO.getCorreoInstitucional() != null) {
            usuarioExistente.setCorreoInstitucional(usuarioDTO.getCorreoInstitucional());
        }
        if (usuarioDTO.getContrasena() != null) {
            usuarioExistente.setContrasena(usuarioDTO.getContrasena());
        }


        Usuario patchedUsuario = usuarioRepository.save(usuarioExistente);
        return convertToDTO(patchedUsuario);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setCorreoInstitucional(usuario.getCorreoInstitucional());
        return usuarioDTO;
    }

    private Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreoInstitucional(usuarioDTO.getCorreoInstitucional());
        return usuario;
    }
}
