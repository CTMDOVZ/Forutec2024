package com.example.forutec_pt1.Categoria.infrastructure;

import com.example.forutec_pt1.Categoria.Categoria;
import com.example.forutec_pt1.Categoria.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import jakarta.validation.ConstraintViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoriaRepository.deleteAll();

        categoria = new Categoria();
        categoria.setNombre("Categoria Test");
    }

    @Test
    void shouldSaveCategoria() {
        Categoria savedCategoria = categoriaRepository.save(categoria);
        assertThat(savedCategoria).isNotNull();
        assertThat(savedCategoria.getId()).isNotNull();
        assertThat(savedCategoria.getNombre()).isEqualTo("Categoria Test");
    }

    @Test
    void shouldFindCategoriaById() {
        Categoria savedCategoria = categoriaRepository.save(categoria);
        Optional<Categoria> foundCategoria = categoriaRepository.findById(savedCategoria.getId());
        assertThat(foundCategoria).isPresent();
        assertThat(foundCategoria.get().getNombre()).isEqualTo("Categoria Test");
    }

    @Test
    void shouldThrowExceptionWhenSavingCategoriaWithNullNombre() {
        categoria.setNombre(null);
        assertThrows(ConstraintViolationException.class, () -> categoriaRepository.save(categoria));
    }

    @Test
    void shouldDeleteCategoria() {
        Categoria savedCategoria = categoriaRepository.save(categoria);
        categoriaRepository.delete(savedCategoria);
        Optional<Categoria> deletedCategoria = categoriaRepository.findById(savedCategoria.getId());
        assertThat(deletedCategoria).isNotPresent();
    }
}
