package com.example.forutec_pt1.Suscripcion.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class SuscripcionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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

    @BeforeEach
    void setup() {
        suscripcionRepository.deleteAll();
        publicacionRepository.deleteAll();
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setNombre("Usuario 1");
        usuario.setApellido("abs");
        usuario.setCorreoInstitucional("alexander.munnoz12@gmail.com");
        usuario.setContrasena("amb");
        usuario = usuarioRepository.save(usuario);

        categoria = new Categoria();
        categoria.setNombre("Categoria 1");
        categoria = categoriaRepository.save(categoria);

        publicacion = new Publicacion();
        publicacion.setContenido("Contenido de la Publicación 1");
        publicacion.setFechaHoraPublicacion(LocalDateTime.parse("2024-05-24T10:00:00"));
        publicacion.setUsuario(usuario);
        publicacion = publicacionRepository.save(publicacion);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateSuscripcion() throws Exception {
        String suscripcionJson = "{ \"usuario\": {\"id\": " + usuario.getId() + "}, \"categoria\": {\"id\": " + categoria.getId() + "}, \"publicacion\": {\"id\": " + publicacion.getId() + "} }";

        mockMvc.perform(post("/api/suscripciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suscripcionJson))
                .andExpect(status().isCreated())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Categoria 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnSuscripcionWhenSuscripcionExists() throws Exception {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setUsuario(usuario);
        suscripcion.setCategoria(categoria);
        suscripcion.setPublicacion(publicacion);
        suscripcion = suscripcionRepository.save(suscripcion);

        mockMvc.perform(get("/api/suscripciones/" + suscripcion.getId()))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Categoria 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteSuscripcion() throws Exception {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setUsuario(usuario);
        suscripcion.setCategoria(categoria);
        suscripcion.setPublicacion(publicacion);
        suscripcion = suscripcionRepository.save(suscripcion);

        mockMvc.perform(delete("/api/suscripciones/" + suscripcion.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/suscripciones/" + suscripcion.getId()))
                .andExpect(status().isNotFound());
    }
}
