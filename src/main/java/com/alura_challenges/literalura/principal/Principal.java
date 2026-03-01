package com.alura_challenges.literalura.principal;

import com.alura_challenges.literalura.model.*;
import com.alura_challenges.literalura.repository.*;
import com.alura_challenges.literalura.service.*;

import java.util.*;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    private ConsumoAPI consumo = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    1 - Buscar libro por título
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en determinado año
                    5 - Mostrar libros por idioma
                    
                    0 - Salir
                    """);

            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> mostrarLibros();
                case 3 -> mostrarAutores();
                case 4 -> autoresVivos();
                case 5 -> librosPorIdioma();
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Ingrese el título:");
        String tituloBusqueda = teclado.nextLine();

        String json = consumo.obtenerDatos(URL_BASE + tituloBusqueda.replace(" ", "%20"));
        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.resultados().isEmpty()) {
            System.out.println("No se encontraron resultados.");
            return;
        }

        DatosLibro datos = respuesta.resultados().get(0);

        if (datos.idiomas().isEmpty() || datos.autores().isEmpty()) {
            System.out.println("Datos incompletos.");
            return;
        }

        String idioma = datos.idiomas().get(0).toUpperCase();

        if (!idioma.equals("EN") && !idioma.equals("ES")) {
            System.out.println("Idioma no permitido.");
            return;
        }

        Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(datos.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("Libro ya registrado.");
            return;
        }

        DatosAutor datosAutor = datos.autores().get(0);

        Autor autor = autorRepository.findByNombreIgnoreCase(datosAutor.nombre())
                .orElseGet(() -> autorRepository.save(
                        new Autor(datosAutor.nombre(), datosAutor.fechaNacimiento(), datosAutor.fechaMuerte())
                ));

        Libro libro = new Libro(datos.titulo(), idioma, datos.numeroDescargas(), autor);
        libroRepository.save(libro);

        System.out.println("Libro guardado correctamente.");
    }

    private void mostrarLibros() {
        libroRepository.listarLibros()
                .forEach(l -> System.out.println(l.getTitulo() + " - " + l.getIdioma()));
    }

    private void mostrarAutores() {
        autorRepository.listarAutores()
                .forEach(a -> System.out.println(a.getNombre()));
    }

    private void autoresVivos() {
        System.out.println("Ingrese año:");
        String entrada = teclado.nextLine();

        while (!entrada.matches("\\d+")) {
            System.out.println("Ingrese solo números:");
            entrada = teclado.nextLine();
        }

        Integer anio = Integer.parseInt(entrada);

        autorRepository.autoresVivosEn(anio)
                .forEach(a -> System.out.println(a.getNombre()));
    }

    private void librosPorIdioma() {
        System.out.println("Ingrese idioma (EN/ES):");
        String idioma = teclado.nextLine().toUpperCase();

        if (!idioma.equals("EN") && !idioma.equals("ES")) {
            System.out.println("Idioma inválido.");
            return;
        }

        List<Libro> libros = libroRepository.buscarPorIdioma(idioma);

        libros.forEach(l -> System.out.println(l.getTitulo()));

        System.out.println("Cantidad: " + libros.size());
    }
}