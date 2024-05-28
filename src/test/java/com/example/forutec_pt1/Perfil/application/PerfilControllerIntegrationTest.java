package com.example.forutec_pt1.Perfil.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.forutec_pt1.Perfil.Perfil;
import com.example.forutec_pt1.Perfil.PerfilRepository;
import com.example.forutec_pt1.Usuario.Usuario;
import com.example.forutec_pt1.Usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PerfilControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Perfil perfil;
    private Usuario usuario;

    @BeforeEach
    void setup() {
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
        perfil.setInformacionAdicional("Informacion adicional");
        perfil = perfilRepository.save(perfil);
    }



    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreatePerfil() throws Exception {
        // Crear un nuevo usuario para evitar duplicados
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Usuario 2");
        nuevoUsuario.setApellido("Apellido 2");
        nuevoUsuario.setCorreoInstitucional("usuario2@example.com");
        nuevoUsuario.setContrasena("password2");
        nuevoUsuario = usuarioRepository.save(nuevoUsuario);

        String perfilJson = "{ \"usuarioId\": " + nuevoUsuario.getId() + ", \"informacionAdicional\": \"Nueva Informacion\" }";

        mockMvc.perform(post("/api/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(perfilJson))
                .andExpect(status().isCreated())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Nueva Informacion"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnPerfilWhenPerfilExists() throws Exception {
        mockMvc.perform(get("/api/perfiles/" + perfil.getId()))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Informacion adicional"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeletePerfil() throws Exception {
        mockMvc.perform(delete("/api/perfiles/" + perfil.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/perfiles/" + perfil.getId()))
                .andExpect(status().isNotFound());
    }
}
