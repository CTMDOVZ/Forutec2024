package com.example.forutec_pt1.Publicacion.infrastructure;


import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Publicacion.PublicacionRepository;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class PublicacionRepositoryTest {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Publicacion publicacion;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        publicacionRepository.deleteAll();
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setNombre("Usuario 1");
        usuario.setApellido("Apellido 1");
        usuario.setCorreoInstitucional("usuario1@example.com");
        usuario.setContrasena("password");
        usuario = usuarioRepository.save(usuario);

        publicacion = new Publicacion();
        publicacion.setContenido("Contenido de la publicación");
        publicacion.setFechaHoraPublicacion(LocalDateTime.now());
        publicacion.setUsuario(usuario);
    }

    @Test
    void shouldSavePublicacion() {
        Publicacion savedPublicacion = publicacionRepository.save(publicacion);
        assertThat(savedPublicacion).isNotNull();
        assertThat(savedPublicacion.getId()).isNotNull();
        assertThat(savedPublicacion.getContenido()).isEqualTo("Contenido de la publicación");
    }

    @Test
    void shouldFindPublicacionById() {
        Publicacion savedPublicacion = publicacionRepository.save(publicacion);
        Optional<Publicacion> foundPublicacion = publicacionRepository.findById(savedPublicacion.getId());
        assertThat(foundPublicacion).isPresent();
        assertThat(foundPublicacion.get().getContenido()).isEqualTo("Contenido de la publicación");
    }

    @Test
    void shouldThrowExceptionWhenSavingPublicacionWithDuplicateContenido() {
        publicacionRepository.save(publicacion);

        Publicacion anotherPublicacion = new Publicacion();
        anotherPublicacion.setContenido("Contenido de la publicación");
        anotherPublicacion.setFechaHoraPublicacion(LocalDateTime.now());
        anotherPublicacion.setUsuario(usuario);

        assertThrows(DataIntegrityViolationException.class, () -> publicacionRepository.save(anotherPublicacion));
    }

    @Test
    void shouldDeletePublicacion() {
        Publicacion savedPublicacion = publicacionRepository.save(publicacion);
        publicacionRepository.delete(savedPublicacion);
        Optional<Publicacion> deletedPublicacion = publicacionRepository.findById(savedPublicacion.getId());
        assertThat(deletedPublicacion).isNotPresent();
    }
}
