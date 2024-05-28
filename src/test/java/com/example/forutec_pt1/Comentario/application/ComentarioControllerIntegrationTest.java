package com.example.forutec_pt1.Comentario.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.forutec_pt1.Comentario.Comentario;
import com.example.forutec_pt1.Comentario.ComentarioRepository;
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
import java.time.ZonedDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class ComentarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Usuario usuario;
    private Publicacion publicacion;

    @BeforeEach
    void setup() {
        // Limpieza de repositorios para evitar conflictos
        comentarioRepository.deleteAll();
        publicacionRepository.deleteAll();
        usuarioRepository.deleteAll();

        // Creación de usuario y publicación de prueba
        usuario = new Usuario();
        usuario.setNombre("Usuario 1");
        usuario.setContrasena("abs");
        usuario.setCorreoInstitucional("alexander.munnoz12@gmail.com");
        usuario.setContrasena("amb");
        usuario = usuarioRepository.save(usuario);

        publicacion = new Publicacion();
        publicacion.setContenido("Contenido de la Publicación 1");
        publicacion.setFechaHoraPublicacion(LocalDateTime.parse("2024-05-24T10:00:00")); // cambie el zoned
        publicacion.setUsuario(usuario);
        publicacion = publicacionRepository.save(publicacion);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateComentario() throws Exception {
        String comentarioJson = "{ \"contenido\": \"Este es un comentario de prueba\", \"fechaHoraComentario\": \"2024-05-24T10:00:00\", \"usuarioId\": " + usuario.getId() + ", \"publicacionId\": " + publicacion.getId() + " }";

        mockMvc.perform(post("/api/comentarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comentarioJson))
                .andExpect(status().isCreated())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Este es un comentario de prueba"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnComentarioWhenComentarioExists() throws Exception {
        Comentario comentario = new Comentario();
        comentario.setContenido("Este es un comentario de prueba");
        comentario.setFechaHoraComentario(LocalDateTime.parse("2024-05-24T10:00:00"));
        comentario.setUsuario(usuario);
        comentario.setPublicacion(publicacion);
        comentario = comentarioRepository.save(comentario);

        mockMvc.perform(get("/api/comentarios/" + comentario.getId()))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Este es un comentario de prueba"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteComentario() throws Exception {
        Comentario comentario = new Comentario();
        comentario.setContenido("Este es un comentario de prueba");
        comentario.setFechaHoraComentario(LocalDateTime.parse("2024-05-24T10:00:00"));
        comentario.setUsuario(usuario);
        comentario.setPublicacion(publicacion);
        comentario = comentarioRepository.save(comentario);

        mockMvc.perform(delete("/api/comentarios/" + comentario.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/comentarios/" + comentario.getId()))
                .andExpect(status().isNotFound());
    }
}
