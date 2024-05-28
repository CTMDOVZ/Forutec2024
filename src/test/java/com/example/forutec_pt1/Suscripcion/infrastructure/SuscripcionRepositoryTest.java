package com.example.forutec_pt1.Suscripcion.infrastructure;

import com.example.forutec_pt1.Categoria.Categoria;
import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Suscripcion.Suscripcion;
import com.example.forutec_pt1.Suscripcion.SuscripcionRepository;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import com.example.forutec_pt1.Categoria.CategoriaRepository;
import com.example.forutec_pt1.Publicacion.PublicacionRepository;
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
public class SuscripcionRepositoryTest {
/*
    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Usuario usuario;
    private Categoria categoria;
    private Publicacion publicacion;
    private Suscripcion suscripcion;

    @BeforeEach
    void setUp() {
        suscripcionRepository.deleteAll();
        publicacionRepository.deleteAll();
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setFirstname("Usuario 1");
        usuario.setLastname("Apellido 1");
        usuario.setEmail("usuario1@example.com");
        usuario.setContrasena("password");
        usuario = usuarioRepository.save(usuario);

        categoria = new Categoria();
        categoria.setNombre("Categoria 1");
        categoria = categoriaRepository.save(categoria);

        publicacion = new Publicacion();
        publicacion.setContenido("Contenido de la publicación");
        publicacion.setFechaHoraPublicacion(LocalDateTime.now());
        publicacion.setUsuario(usuario);
        publicacion = publicacionRepository.save(publicacion);

        suscripcion = new Suscripcion();
        suscripcion.setUsuario(usuario);
        suscripcion.setCategoria(categoria);
        suscripcion.setPublicacion(publicacion);
    }

    @Test
    void shouldSaveSuscripcion() {
        Suscripcion savedSuscripcion = suscripcionRepository.save(suscripcion);
        assertThat(savedSuscripcion).isNotNull();
        assertThat(savedSuscripcion.getId()).isNotNull();
        assertThat(savedSuscripcion.getUsuario()).isEqualTo(usuario);
        assertThat(savedSuscripcion.getCategoria()).isEqualTo(categoria);
        assertThat(savedSuscripcion.getPublicacion()).isEqualTo(publicacion);
    }

    @Test
    void shouldFindSuscripcionById() {
        Suscripcion savedSuscripcion = suscripcionRepository.save(suscripcion);
        Optional<Suscripcion> foundSuscripcion = suscripcionRepository.findById(savedSuscripcion.getId());
        assertThat(foundSuscripcion).isPresent();
        assertThat(foundSuscripcion.get().getUsuario()).isEqualTo(usuario);
        assertThat(foundSuscripcion.get().getCategoria()).isEqualTo(categoria);
        assertThat(foundSuscripcion.get().getPublicacion()).isEqualTo(publicacion);
    }

    @Test
    void shouldThrowExceptionWhenSavingSuscripcionWithNonExistentUsuario() {
        Usuario nonexistentUsuario = new Usuario();
        nonexistentUsuario.setId(999L); // Asegúrate de que este ID no exista en la base de datos

        suscripcion.setUsuario(nonexistentUsuario);

        assertThrows(DataIntegrityViolationException.class, () -> suscripcionRepository.save(suscripcion));
    }

    @Test
    void shouldDeleteSuscripcion() {
        Suscripcion savedSuscripcion = suscripcionRepository.save(suscripcion);
        suscripcionRepository.delete(savedSuscripcion);
        Optional<Suscripcion> deletedSuscripcion = suscripcionRepository.findById(savedSuscripcion.getId());
        assertThat(deletedSuscripcion).isNotPresent();
    }

 */
}
