package com.example.forutec_pt1.Perfil.infrastructure;

import com.example.forutec_pt1.Perfil.Perfil;
import com.example.forutec_pt1.Perfil.PerfilRepository;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class PerfilRepositoryTest {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfilRepository.deleteAll();
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setNombre("Usuario 1");
        usuario.setApellido("Apellido 1");
        usuario.setCorreoInstitucional("usuario1@example.com");
        usuario.setContrasena("password");
        usuario = usuarioRepository.save(usuario);

        perfil = new Perfil();
        perfil.setUsuario(usuario);
        perfil.setInformacionAdicional("Información adicional");
    }

    //Verifica que se pueda guardar un perfil en la base de datos
    @Test
    void shouldSavePerfil() {
        Perfil savedPerfil = perfilRepository.save(perfil);
        assertThat(savedPerfil).isNotNull();
        assertThat(savedPerfil.getId()).isNotNull();
        assertThat(savedPerfil.getUsuario()).isEqualTo(usuario);
        assertThat(savedPerfil.getInformacionAdicional()).isEqualTo("Información adicional");
    }

    //Verifica que se pueda encontrar un perfil por su ID
    @Test
    void shouldFindPerfilById() {
        Perfil savedPerfil = perfilRepository.save(perfil);
        Optional<Perfil> foundPerfil = perfilRepository.findById(savedPerfil.getId());
        assertThat(foundPerfil).isPresent();
        assertThat(foundPerfil.get().getUsuario()).isEqualTo(usuario);
        assertThat(foundPerfil.get().getInformacionAdicional()).isEqualTo("Información adicional");
    }

    //Verifica que se lance una excepción al intentar guardar un perfil con un usuario inexistente
    @Test
    void shouldThrowExceptionWhenSavingPerfilWithNonExistentUsuario() {
        Usuario nonexistentUsuario = new Usuario();
        nonexistentUsuario.setId(999L);

        perfil.setUsuario(nonexistentUsuario);

        assertThrows(DataIntegrityViolationException.class, () -> perfilRepository.save(perfil));
    }

    //Verifica que se pueda eliminar un perfil de la base de datos
    @Test
    void shouldDeletePerfil() {
        Perfil savedPerfil = perfilRepository.save(perfil);
        perfilRepository.delete(savedPerfil);
        Optional<Perfil> deletedPerfil = perfilRepository.findById(savedPerfil.getId());
        assertThat(deletedPerfil).isNotPresent();
    }
}
