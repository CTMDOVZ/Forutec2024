package com.example.forutec_pt1.Suscripcion.infrastructure;


import com.example.forutec_pt1.Suscripcion.Suscripcion;
import com.example.forutec_pt1.Suscripcion.SuscripcionRepository;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import com.example.forutec_pt1.Categoria.Categoria;
import com.example.forutec_pt1.Categoria.CategoriaRepository;
import com.example.forutec_pt1.Publicacion.Publicacion;
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

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Suscripcion suscripcion;
    private Usuario usuario;
    private Categoria categoria;
    private Publicacion publicacion;

    @BeforeEach
    void setUp() {
        suscripcionRepository.deleteAll();
        usuarioRepository.deleteAll();
        categoriaRepository.deleteAll();
        publicacionRepository.deleteAll();

        usuario = new Usuario();
        usuario.setNombre("Usuario 1");
        usuario.setApellido("Apellido 1");
        usuario.setCorreoInstitucional("usuario1@example.com");
        usuario.setContrasena("password");
        usuario = usuarioRepository.save(usuario);

        categoria = new Categoria();
        categoria.setNombre("Categoria 1");
        categoria = categoriaRepository.save(categoria);

        publicacion = new Publicacion();
        publicacion.setContenido("Contenido de la publicación");
        publicacion.setUsuario(usuario);
        publicacion.setFechaHoraPublicacion(LocalDateTime.now());
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
        assertThat(savedSuscripcion.getUsuario().getNombre()).isEqualTo("Usuario 1");
    }

    @Test
    void shouldFindSuscripcionById() {
        Suscripcion savedSuscripcion = suscripcionRepository.save(suscripcion);
        Optional<Suscripcion> foundSuscripcion = suscripcionRepository.findById(savedSuscripcion.getId());
        assertThat(foundSuscripcion).isPresent();
        assertThat(foundSuscripcion.get().getUsuario().getNombre()).isEqualTo("Usuario 1");
    }

    @Test
    void shouldThrowExceptionWhenSavingSuscripcionWithDuplicateData() {
        suscripcionRepository.save(suscripcion);

        Suscripcion anotherSuscripcion = new Suscripcion();
        anotherSuscripcion.setUsuario(usuario);
        anotherSuscripcion.setCategoria(categoria);
        anotherSuscripcion.setPublicacion(publicacion);

        assertThrows(DataIntegrityViolationException.class, () -> suscripcionRepository.save(anotherSuscripcion));
    }

    @Test
    void shouldDeleteSuscripcion() {
        Suscripcion savedSuscripcion = suscripcionRepository.save(suscripcion);
        suscripcionRepository.delete(savedSuscripcion);
        Optional<Suscripcion> deletedSuscripcion = suscripcionRepository.findById(savedSuscripcion.getId());
        assertThat(deletedSuscripcion).isNotPresent();
    }
}
