package com.example.forutec_pt1.Perfil.domain;

import com.example.forutec_pt1.Perfil.Perfil;
import com.example.forutec_pt1.Usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PerfilTest {

    private Usuario usuario;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setFirstname("Usuario 1");
        usuario.setLastname("Apellido 1");
        usuario.setEmail("usuario1@example.com");
        usuario.setContrasena("password");

        perfil = new Perfil();
        perfil.setId(1L);
        perfil.setUsuario(usuario);
        perfil.setInformacionAdicional("Información adicional");
    }

    //Verifica que se pueda crear un Perfil con datos válidos
    @Test
    void shouldCreatePerfilWithValidData() {
        assertThat(perfil.getUsuario()).isEqualTo(usuario);
        assertThat(perfil.getInformacionAdicional()).isEqualTo("Información adicional");
    }

    //Verifica que se pueda actualizar la información adicional de un perfil
    @Test
    void shouldUpdateInformacionAdicional() {
        perfil.setInformacionAdicional("Información actualizada");
        assertThat(perfil.getInformacionAdicional()).isEqualTo("Información actualizada");
    }

    //Verifica que se pueda obtener la información del usuario asociado al perfil
    @Test
    void shouldReturnUsuarioInformation() {
        assertThat(perfil.getUsuario().getFirstname()).isEqualTo("Usuario 1");
        assertThat(perfil.getUsuario().getLastname()).isEqualTo("Apellido 1");
        assertThat(perfil.getUsuario().getEmail()).isEqualTo("usuario1@example.com");
    }
}
