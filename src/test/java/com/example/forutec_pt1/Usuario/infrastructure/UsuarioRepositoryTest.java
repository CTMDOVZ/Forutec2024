package com.example.forutec_pt1.Usuario.infrastructure;

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
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setFirstname("Usuario 1");
        usuario.setLastname("Apellido 1");
        usuario.setEmail("usuario1@example.com");
        usuario.setContrasena("password");
    }

    //Verifica que se pueda guardar un usuario en la base de datos
    @Test
    void shouldSaveUsuario() {
        Usuario savedUsuario = usuarioRepository.save(usuario);
        assertThat(savedUsuario).isNotNull();
        assertThat(savedUsuario.getId()).isNotNull();
        assertThat(savedUsuario.getFirstname()).isEqualTo("Usuario 1");
        assertThat(savedUsuario.getLastname()).isEqualTo("Apellido 1");
    }

    //Verifica que se pueda encontrar un usuario por su ID
    @Test
    void shouldFindUsuarioById() {
        Usuario savedUsuario = usuarioRepository.save(usuario);
        Optional<Usuario> foundUsuario = usuarioRepository.findById(savedUsuario.getId());
        assertThat(foundUsuario).isPresent();
        assertThat(foundUsuario.get().getFirstname()).isEqualTo("Usuario 1");
        assertThat(foundUsuario.get().getLastname()).isEqualTo("Apellido 1");
    }

    //Verifica que se lance una excepción al intentar guardar un usuario con un correo institucional duplicado
    @Test
    void shouldThrowExceptionWhenSavingUsuarioWithDuplicateCorreoInstitucional() {
        usuarioRepository.save(usuario);

        Usuario anotherUsuario = new Usuario();
        anotherUsuario.setFirstname("Usuario 2");
        anotherUsuario.setLastname("Apellido 2");
        anotherUsuario.setEmail("usuario1@example.com");
        anotherUsuario.setContrasena("password2");

        assertThrows(DataIntegrityViolationException.class, () -> usuarioRepository.save(anotherUsuario));
    }

    //Verifica que se pueda eliminar un usuario de la base de datos.
    @Test
    void shouldDeleteUsuario() {
        Usuario savedUsuario = usuarioRepository.save(usuario);
        usuarioRepository.delete(savedUsuario);
        Optional<Usuario> deletedUsuario = usuarioRepository.findById(savedUsuario.getId());
        assertThat(deletedUsuario).isNotPresent();
    }
}
