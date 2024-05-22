package com.example.forutec_pt1.Comentario;

import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.ResourceNotFoundException;
import com.example.forutec_pt1.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<ComentarioDTO> getAllComentarios() {
        return comentarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ComentarioDTO getComentarioById(Long id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado con id: " + id));
        return convertToDTO(comentario);
    }

    public ComentarioDTO saveComentario(ComentarioDTO comentarioDTO) {
        Comentario comentario = convertToEntity(comentarioDTO);
        Comentario savedComentario = comentarioRepository.save(comentario);
        return convertToDTO(savedComentario);
    }

    public void deleteComentario(Long id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado con id: " + id));
        comentarioRepository.delete(comentario);
    }

    private ComentarioDTO convertToDTO(Comentario comentario) {
        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setContenido(comentario.getContenido());
        comentarioDTO.setFechaHoraComentario(comentario.getFechaHoraComentario().toString());
        comentarioDTO.setUsuarioId(comentario.getUsuario().getId());
        comentarioDTO.setPublicacionId(comentario.getPublicacion().getId());
        return comentarioDTO;
    }

    private Comentario convertToEntity(ComentarioDTO comentarioDTO) {
        Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
        comentario.setContenido(comentarioDTO.getContenido());
        comentario.setFechaHoraComentario(LocalDateTime.parse(comentarioDTO.getFechaHoraComentario()));
        comentario.setUsuario(new Usuario());
        comentario.getUsuario().setId(comentarioDTO.getUsuarioId());
        comentario.setPublicacion(new Publicacion());
        comentario.getPublicacion().setId(comentarioDTO.getPublicacionId());
        return comentario;
    }
}
