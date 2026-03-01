package com.alura_challenges.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Integer fechaNacimiento;
    private Integer fechaMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libros;

    public Autor() {}

    public Autor(String nombre, Integer fechaNacimiento, Integer fechaMuerte) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaMuerte = fechaMuerte;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public Integer getFechaNacimiento() { return fechaNacimiento; }

    public Integer getFechaMuerte() { return fechaMuerte; }

    public List<Libro> getLibros() { return libros; }

    public void setId(Long id) { this.id = id; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setFechaNacimiento(Integer fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public void setFechaMuerte(Integer fechaMuerte) { this.fechaMuerte = fechaMuerte; }

    public void setLibros(List<Libro> libros) { this.libros = libros; }
}