package com.example.forutec_pt1.Categoria.domain;

import com.example.forutec_pt1.Categoria.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriaTest {

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoria Test");
    }

    @Test
    void shouldCreateCategoriaWithValidData() {
        assertThat(categoria.getNombre()).isEqualTo("Categoria Test");
    }

    @Test
    void shouldUpdateNombre() {
        categoria.setNombre("Categoria Actualizada");
        assertThat(categoria.getNombre()).isEqualTo("Categoria Actualizada");
    }

    @Test
    void shouldReturnCategoriaInformation() {
        assertThat(categoria.getId()).isEqualTo(1L);
        assertThat(categoria.getNombre()).isEqualTo("Categoria Test");
    }
}
