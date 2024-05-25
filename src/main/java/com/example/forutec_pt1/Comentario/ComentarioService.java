package com.example.forutec_pt1.Comentario;

import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.ResourceNotFoundException;
import com.example.forutec_pt1.Suscripcion.Suscripcion;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Publicacion.PublicacionRepository;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.example.forutec_pt1.events.CommentEmailEvent;
import com.example.forutec_pt1.Suscripcion.SuscripcionRepository;

import java.util.List;

import java.time.LocalDateTime;

import java.util.stream.Collectors;

@Service
public class ComentarioService {
    private final SuscripcionRepository suscripcionRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;

    @Autowired
    private final ComentarioRepository comentarioRepository;


    @Autowired
    public ComentarioService(ComentarioRepository comentarioRepository,
                             SuscripcionRepository suscripcionRepository,
                             PublicacionRepository publicacionRepository,
                             UsuarioRepository usuarioRepository,
                             ApplicationEventPublisher eventPublisher) {
        this.comentarioRepository = comentarioRepository;
        this.suscripcionRepository = suscripcionRepository;
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventPublisher = eventPublisher;
    }


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
        Publicacion publicacion = publicacionRepository.findById(comentarioDTO.getPublicacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion no encontrada con id: " + comentarioDTO.getPublicacionId()));
        Usuario usuario = usuarioRepository.findById(comentarioDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + comentarioDTO.getUsuarioId()));

        comentario.setPublicacion(publicacion);
        comentario.setUsuario(usuario);
        comentario.setFechaHoraComentario(LocalDateTime.now());  // Establecer la fecha y hora actual

        Comentario savedComentario = comentarioRepository.save(comentario);

        List<Suscripcion> suscripciones = suscripcionRepository.findByPublicacion(publicacion);
        for (Suscripcion suscripcion : suscripciones) {
            String email = suscripcion.getUsuario().getCorreoInstitucional();
            if (email != null && !email.isEmpty()) {
                CommentEmailEvent event = new CommentEmailEvent(email, comentario.getContenido());
                eventPublisher.publishEvent(event);
            }
        }
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
