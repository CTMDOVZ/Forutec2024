package com.example.forutec_pt1.Publicacion;

import com.example.forutec_pt1.ResourceNotFoundException;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PublicacionDTO> getAllPublicaciones() {
        return publicacionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PublicacionDTO getPublicacionById(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion no encontrada con id: " + id));
        return convertToDTO(publicacion);
    }

    public PublicacionDTO savePublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = new Publicacion();
        publicacion.setContenido(publicacionDTO.getContenido());

        // Convertir String a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime fechaHoraPublicacion = LocalDateTime.parse(publicacionDTO.getFechaHoraPublicacion(), formatter);
        publicacion.setFechaHoraPublicacion(fechaHoraPublicacion);

        // Asignar el usuario a la publicación
        Usuario usuario = usuarioRepository.findById(publicacionDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        publicacion.setUsuario(usuario);

        Publicacion savedPublicacion = publicacionRepository.save(publicacion);

        publicacionDTO.setId(savedPublicacion.getId());
        return publicacionDTO;
    }

    public void deletePublicacion(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion no encontrada con id: " + id));
        publicacionRepository.delete(publicacion);
    }

    private PublicacionDTO convertToDTO(Publicacion publicacion) {
        PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setContenido(publicacion.getContenido());
        publicacionDTO.setFechaHoraPublicacion(publicacion.getFechaHoraPublicacion().toString());
        publicacionDTO.setUsuarioId(publicacion.getUsuario().getId());
        return publicacionDTO;
    }

    private Publicacion convertToEntity(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = new Publicacion();
        publicacion.setId(publicacionDTO.getId());
        publicacion.setContenido(publicacionDTO.getContenido());
        publicacion.setFechaHoraPublicacion(LocalDateTime.parse(publicacionDTO.getFechaHoraPublicacion()));
        publicacion.setUsuario(new Usuario());
        publicacion.getUsuario().setId(publicacionDTO.getUsuarioId());
        return publicacion;
    }
}
