package com.example.forutec_pt1.Comentario.domain;

import com.example.forutec_pt1.Comentario.Comentario;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Publicacion.Publicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ComentarioTest {

    private Usuario usuario;
    private Publicacion publicacion;
    private Comentario comentario;

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

        comentario = new Comentario();
        comentario.setId(1L);
        comentario.setContenido("Contenido del comentario");
        comentario.setFechaHoraComentario(LocalDateTime.now());
        comentario.setUsuario(usuario);
        comentario.setPublicacion(publicacion);
    }

    //Verifica que se pueda crear un comentario con datos válidos
    @Test
    void shouldCreateComentarioWithValidData() {
        assertThat(comentario.getContenido()).isEqualTo("Contenido del comentario");
        assertThat(comentario.getFechaHoraComentario()).isNotNull();
        assertThat(comentario.getUsuario()).isEqualTo(usuario);
        assertThat(comentario.getPublicacion()).isEqualTo(publicacion);
    }

    //Verifica que se pueda actualizar el contenido de un comentario
    @Test
    void shouldUpdateContenido() {
        comentario.setContenido("Contenido actualizado");
        assertThat(comentario.getContenido()).isEqualTo("Contenido actualizado");
    }

    //Verifica que se pueda obtener la información del comentario, incluyendo el usuario y la publicación asociados
    @Test
    void shouldReturnComentarioInformation() {
        assertThat(comentario.getContenido()).isEqualTo("Contenido del comentario");
        assertThat(comentario.getUsuario().getNombre()).isEqualTo("Usuario 1");
        assertThat(comentario.getPublicacion().getContenido()).isEqualTo("Contenido de la publicación");
    }
}
