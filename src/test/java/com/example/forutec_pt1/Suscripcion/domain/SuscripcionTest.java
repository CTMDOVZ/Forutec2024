package com.example.forutec_pt1.Suscripcion.domain;

import com.example.forutec_pt1.Categoria.Categoria;
import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Suscripcion.Suscripcion;
import com.example.forutec_pt1.Usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class SuscripcionTest {

    private Usuario usuario;
    private Categoria categoria;
    private Publicacion publicacion;
    private Suscripcion suscripcion;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario 1");
        usuario.setApellido("Apellido 1");
        usuario.setCorreoInstitucional("usuario1@example.com");
        usuario.setContrasena("password");

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoria 1");

        publicacion = new Publicacion();
        publicacion.setId(1L);
        publicacion.setContenido("Contenido de la publicación");
        publicacion.setFechaHoraPublicacion(LocalDateTime.now());
        publicacion.setUsuario(usuario);

        suscripcion = new Suscripcion();
        suscripcion.setId(1L);
        suscripcion.setUsuario(usuario);
        suscripcion.setCategoria(categoria);
        suscripcion.setPublicacion(publicacion);
    }

    @Test
    void shouldCreateSuscripcionWithValidData() {
        assertThat(suscripcion.getUsuario()).isEqualTo(usuario);
        assertThat(suscripcion.getCategoria()).isEqualTo(categoria);
        assertThat(suscripcion.getPublicacion()).isEqualTo(publicacion);
    }

    @Test
    void shouldUpdateCategoria() {
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setId(2L);
        nuevaCategoria.setNombre("Nueva Categoria");
        suscripcion.setCategoria(nuevaCategoria);
        assertThat(suscripcion.getCategoria()).isEqualTo(nuevaCategoria);
    }

    @Test
    void shouldReturnSuscripcionInformation() {
        assertThat(suscripcion.getUsuario().getNombre()).isEqualTo("Usuario 1");
        assertThat(suscripcion.getCategoria().getNombre()).isEqualTo("Categoria 1");
        assertThat(suscripcion.getPublicacion().getContenido()).isEqualTo("Contenido de la publicación");
    }
}
