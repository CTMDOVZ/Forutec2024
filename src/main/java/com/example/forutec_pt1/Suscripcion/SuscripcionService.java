package com.example.forutec_pt1.Suscripcion;

import com.example.forutec_pt1.Categoria.Categoria;
import com.example.forutec_pt1.Exceptions.ResourceNotFoundException;
import com.example.forutec_pt1.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuscripcionService {

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    public List<SuscripcionDTO> getAllSuscripciones() {
        return suscripcionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SuscripcionDTO getSuscripcionById(Long id) {
        Suscripcion suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suscripcion no encontrada con id: " + id));
        return convertToDTO(suscripcion);
    }

    public SuscripcionDTO saveSuscripcion(SuscripcionDTO suscripcionDTO) {
        Suscripcion suscripcion = convertToEntity(suscripcionDTO);
        Suscripcion savedSuscripcion = suscripcionRepository.save(suscripcion);
        return convertToDTO(savedSuscripcion);
    }

    public void deleteSuscripcion(Long id) {
        Suscripcion suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suscripcion no encontrada con id: " + id));
        suscripcionRepository.delete(suscripcion);
    }

    private SuscripcionDTO convertToDTO(Suscripcion suscripcion) {
        SuscripcionDTO suscripcionDTO = new SuscripcionDTO();
        suscripcionDTO.setId(suscripcion.getId());
        suscripcionDTO.setUsuarioId(suscripcion.getUsuario().getId());
        suscripcionDTO.setCategoriaId(suscripcion.getCategoria().getId());
        return suscripcionDTO;
    }

    private Suscripcion convertToEntity(SuscripcionDTO suscripcionDTO) {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setId(suscripcionDTO.getId());
        suscripcion.setUsuario(new Usuario());
        suscripcion.getUsuario().setId(suscripcionDTO.getUsuarioId());
        suscripcion.setCategoria(new Categoria());
        suscripcion.getCategoria().setId(suscripcionDTO.getCategoriaId());
        return suscripcion;
    }
}
