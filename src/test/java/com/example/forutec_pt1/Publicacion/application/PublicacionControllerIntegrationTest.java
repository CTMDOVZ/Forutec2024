package com.example.forutec_pt1.Publicacion.application;


import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Publicacion.PublicacionRepository;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PublicacionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Publicacion publicacion;

    @BeforeEach
    void setup() {
        publicacionRepository.deleteAll();
        usuarioRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario 1");
        usuario.setApellido("Apellido 1");
        usuario.setCorreoInstitucional("usuario1@example.com");
        usuario.setContrasena("password");
        usuario = usuarioRepository.save(usuario);

        publicacion = new Publicacion();
        publicacion.setContenido("Contenido de la publicación");
        publicacion.setFechaHoraPublicacion(LocalDateTime.now());
        publicacion.setUsuario(usuario);
        publicacion = publicacionRepository.save(publicacion);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreatePublicacion() throws Exception {
        String publicacionJson = "{ \"contenido\": \"Nueva publicación\", \"usuarioId\": " + publicacion.getUsuario().getId() + " }";

        mockMvc.perform(post("/api/publicaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(publicacionJson))
                .andExpect(status().isCreated())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Nueva publicación"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnPublicacionWhenPublicacionExists() throws Exception {
        mockMvc.perform(get("/api/publicaciones/" + publicacion.getId()))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Contenido de la publicación"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeletePublicacion() throws Exception {
        mockMvc.perform(delete("/api/publicaciones/" + publicacion.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/publicaciones/" + publicacion.getId()))
                .andExpect(status().isNotFound());
    }
}
