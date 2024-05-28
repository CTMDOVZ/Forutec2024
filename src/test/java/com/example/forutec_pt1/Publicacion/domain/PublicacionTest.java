package com.example.forutec_pt1.Publicacion.domain;

import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PublicacionTest {

    private Publicacion publicacion;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario 1");
        usuario.setApellido("Apellido 1");
        usuario.setCorreoInstitucional("usuario1@example.com");
        usuario.setContrasena("password");

        publicacion = new Publicacion();
        publicacion.setId(1L);
        publicacion.setContenido("Contenido de la publicación");
        publicacion.setFechaHoraPublicacion(LocalDateTime.now());
        publicacion.setUsuario(usuario);
    }

    @Test
    void shouldCreatePublicacionWithValidData() {
        assertThat(publicacion.getContenido()).isEqualTo("Contenido de la publicación");
        assertThat(publicacion.getUsuario().getNombre()).isEqualTo("Usuario 1");
    }

    @Test
    void shouldUpdateContenido() {
        publicacion.setContenido("Contenido actualizado");
        assertThat(publicacion.getContenido()).isEqualTo("Contenido actualizado");
    }

    @Test
    void shouldReturnFechaHoraPublicacion() {
        assertThat(publicacion.getFechaHoraPublicacion()).isNotNull();
    }

    @Test
    void shouldReturnUsuario() {
        assertThat(publicacion.getUsuario()).isEqualTo(usuario);
    }
}
