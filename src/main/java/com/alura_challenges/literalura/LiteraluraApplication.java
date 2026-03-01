package com.alura_challenges.literalura;

import com.alura_challenges.literalura.principal.Principal;
import com.alura_challenges.literalura.repository.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiteraluraApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Bean
    CommandLineRunner run(LibroRepository libroRepo, AutorRepository autorRepo) {
        return args -> {
            Principal principal = new Principal(libroRepo, autorRepo);
            principal.muestraMenu();
        };
    }
}