package com.sistema.locacao;

import com.sistema.locacao.models.Cliente;
import com.sistema.locacao.models.Locacao;
import com.sistema.locacao.models.Locadora;
import com.sistema.locacao.repositories.ClienteRepository;
import com.sistema.locacao.repositories.LocacaoRepository;
import com.sistema.locacao.repositories.LocadoraRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class LocacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocacaoApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ClienteRepository clienteRepo, LocadoraRepository locadoraRepo, LocacaoRepository locacaoRepo) {
        return args -> {
            
            Cliente cliente1 = new Cliente("joao@email.com", "senha123", "111.222.333-44", "Joao Silva", "99999-9999", "Masculino", LocalDate.of(1990, 5, 20));
            clienteRepo.save(cliente1);

            Locadora locadora1 = new Locadora("contato@bikes.com", "senha123", "12.345.678/0001-90", "Super Bikes", "Sao Carlos");
            locadoraRepo.save(locadora1);

            Locacao locacao1 = new Locacao(cliente1, locadora1, LocalDateTime.of(2023, 10, 25, 14, 0));
            locacaoRepo.save(locacao1);

            System.out.println("Leitura Cliente: " + clienteRepo.findById(cliente1.getId()).get().getNome());
            System.out.println("Leitura Locadora: " + locadoraRepo.findById(locadora1.getId()).get().getNome());

            Cliente clienteUpdate = clienteRepo.findById(cliente1.getId()).get();
            clienteUpdate.setNome("Joao Silva Atualizado");
            clienteRepo.save(clienteUpdate);

            System.out.println("Cliente Atualizado: " + clienteRepo.findById(cliente1.getId()).get().getNome());

            locacaoRepo.delete(locacao1);
            System.out.println("Qtd Locacoes apos Delete: " + locacaoRepo.count());
        };
    }
}