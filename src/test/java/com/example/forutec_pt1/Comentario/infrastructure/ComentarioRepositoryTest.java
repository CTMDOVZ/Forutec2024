package com.example.forutec_pt1.Comentario.infrastructure;

import com.example.forutec_pt1.Comentario.Comentario;
import com.example.forutec_pt1.Comentario.ComentarioRepository;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Publicacion.PublicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class ComentarioRepositoryTest {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Usuario usuario;
    private Publicacion publicacion;
    private Comentario comentario;

    @BeforeEach
    void setUp() {
        comentarioRepository.deleteAll();
        publicacionRepository.deleteAll();
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setFirstname("Usuario 1");
        usuario.setLastname("Apellido 1");
        usuario.setEmail("usuario1@example.com");
        usuario.setContrasena("password");
        usuario = usuarioRepository.save(usuario);

        publicacion = new Publicacion();
        publicacion.setContenido("Contenido de la publicación");
        publicacion.setFechaHoraPublicacion(LocalDateTime.now());
        publicacion.setUsuario(usuario);
        publicacion = publicacionRepository.save(publicacion);

        comentario = new Comentario();
        comentario.setContenido("Contenido del comentario");
        comentario.setFechaHoraComentario(LocalDateTime.now());
        comentario.setUsuario(usuario);
        comentario.setPublicacion(publicacion);
    }

    //Verifica que se pueda guardar un comentario en la base de datos
    @Test
    void shouldSaveComentario() {
        Comentario savedComentario = comentarioRepository.save(comentario);
        assertThat(savedComentario).isNotNull();
        assertThat(savedComentario.getId()).isNotNull();
        assertThat(savedComentario.getContenido()).isEqualTo("Contenido del comentario");
        assertThat(savedComentario.getUsuario()).isEqualTo(usuario);
        assertThat(savedComentario.getPublicacion()).isEqualTo(publicacion);
    }

    //Verifica que se pueda encontrar un comentario por su ID
    @Test
    void shouldFindComentarioById() {
        Comentario savedComentario = comentarioRepository.save(comentario);
        Optional<Comentario> foundComentario = comentarioRepository.findById(savedComentario.getId());
        assertThat(foundComentario).isPresent();
        assertThat(foundComentario.get().getContenido()).isEqualTo("Contenido del comentario");
        assertThat(foundComentario.get().getUsuario()).isEqualTo(usuario);
        assertThat(foundComentario.get().getPublicacion()).isEqualTo(publicacion);
    }

    //Verifica que se lance una excepción al intentar guardar un comentario con un usuario inexistente
    @Test
    void shouldThrowExceptionWhenSavingComentarioWithNonExistentUsuario() {
        Usuario nonexistentUsuario = new Usuario();
        nonexistentUsuario.setId(999L); // Asegúrate de que este ID no exista en la base de datos

        comentario.setUsuario(nonexistentUsuario);

        assertThrows(DataIntegrityViolationException.class, () -> comentarioRepository.save(comentario));
    }

    //Verifica que se pueda eliminar un comentario de la base de datos
    @Test
    void shouldDeleteComentario() {
        Comentario savedComentario = comentarioRepository.save(comentario);
        comentarioRepository.delete(savedComentario);
        Optional<Comentario> deletedComentario = comentarioRepository.findById(savedComentario.getId());
        assertThat(deletedComentario).isNotPresent();
    }
}
