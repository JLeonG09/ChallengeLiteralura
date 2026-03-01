package com.alura_challenges.literalura.repository;

import com.alura_challenges.literalura.model.Autor;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a")
    List<Autor> listarAutores();

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :año AND (a.fechaMuerte IS NULL OR a.fechaMuerte > :año)")
    List<Autor> autoresVivosEn(@Param("año") Integer año);
}