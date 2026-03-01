package com.alura_challenges.literalura.repository;

import com.alura_challenges.literalura.model.Libro;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l")
    List<Libro> listarLibros();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> buscarPorIdioma(@Param("idioma") String idioma);
}