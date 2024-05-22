package com.example.forutec_pt1.Publicacion;

import com.example.forutec_pt1.ResourceNotFoundException;
import com.example.forutec_pt1.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

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
        Publicacion publicacion = convertToEntity(publicacionDTO);
        Publicacion savedPublicacion = publicacionRepository.save(publicacion);
        return convertToDTO(savedPublicacion);
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
