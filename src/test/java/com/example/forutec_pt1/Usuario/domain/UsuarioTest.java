package com.example.forutec_pt1.Usuario.domain;

import com.example.forutec_pt1.Usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setFirstname("Usuario 1");
        usuario.setLastname("Apellido 1");
        usuario.setEmail("usuario1@example.com");
        usuario.setContrasena("password");
    }

    //Verifica que se pueda crear un usuario con datos válidos
    @Test
    void shouldCreateUsuarioWithValidData() {
        assertThat(usuario.getFirstname()).isEqualTo("Usuario 1");
        assertThat(usuario.getLastname()).isEqualTo("Apellido 1");
        assertThat(usuario.getEmail()).isEqualTo("usuario1@example.com");
        assertThat(usuario.getContrasena()).isEqualTo("password");
    }

    //Verifica que se pueda actualizar el correo institucional de un usuario
    @Test
    void shouldUpdateCorreoInstitucional() {
        usuario.setEmail("nuevo_correo@example.com");
        assertThat(usuario.getEmail()).isEqualTo("nuevo_correo@example.com");
    }

    //Verifica que se pueda actualizar el nombre de un usuario
    @Test
    void shouldReturnNombre() {
        assertThat(usuario.getFirstname()).isEqualTo("Usuario 1");
    }

    //Verifica que se pueda obtener el apellido de un usuario
    @Test
    void shoulReturnApellido() {
        assertThat(usuario.getLastname()).isEqualTo("Apellido 1");
    }
}
