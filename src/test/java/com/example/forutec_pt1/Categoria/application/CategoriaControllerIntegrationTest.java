package com.example.forutec_pt1.Categoria.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.forutec_pt1.Categoria.Categoria;
import com.example.forutec_pt1.Categoria.CategoriaRepository;
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
public class CategoriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;

    @BeforeEach
    void setup() {
        categoriaRepository.deleteAll();

        categoria = new Categoria();
        categoria.setNombre("Categoria 1");
        categoria = categoriaRepository.save(categoria);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateCategoria() throws Exception {
        String categoriaJson = "{ \"nombre\": \"Nueva Categoria\" }";

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoriaJson))
                .andExpect(status().isCreated())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Nueva Categoria"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnCategoriaWhenCategoriaExists() throws Exception {
        mockMvc.perform(get("/api/categorias/" + categoria.getId()))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Categoria 1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldUpdateCategoria() throws Exception {
        String updatedCategoriaJson = "{ \"nombre\": \"Categoria Actualizada\" }";

        mockMvc.perform(put("/api/categorias/" + categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoriaJson))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Categoria Actualizada"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteCategoria() throws Exception {
        mockMvc.perform(delete("/api/categorias/" + categoria.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/categorias/" + categoria.getId()))
                .andExpect(status().isNotFound());
    }
}
