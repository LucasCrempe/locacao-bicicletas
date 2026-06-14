package com.sistema.locacao;

import com.sistema.locacao.models.Usuario;
import com.sistema.locacao.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LocacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocacaoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.findByEmail("admin@admin.com") == null) {
                Usuario admin = new Usuario();
                admin.setEmail("admin@admin.com");
                admin.setSenha(new BCryptPasswordEncoder().encode("admin"));
                admin.setPapel("ROLE_ADMIN");
                usuarioRepository.save(admin);
            }
        };
    }
}