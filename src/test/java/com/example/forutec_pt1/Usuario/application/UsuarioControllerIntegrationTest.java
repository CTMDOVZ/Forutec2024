package com.example.forutec_pt1.Usuario.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setNombre("Usuario 1");
        usuario.setApellido("abs");
        usuario.setCorreoInstitucional("alexander.munnoz12@gmail.com");
        usuario.setContrasena("amb");
        usuario = usuarioRepository.save(usuario);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateUsuario() throws Exception {
        String usuarioJson = "{ \"nombre\": \"Usuario 2\", \"apellido\": \"xyz\", \"correoInstitucional\": \"nuevo.usuario@gmail.com\", \"contrasena\": \"xyz123\" }";

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(status().isCreated())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Usuario 2"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnUsuarioWhenUsuarioExists() throws Exception {
        mockMvc.perform(get("/api/usuarios/" + usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Usuario 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/" + usuario.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/usuarios/" + usuario.getId()))
                .andExpect(status().isNotFound());
    }
}
