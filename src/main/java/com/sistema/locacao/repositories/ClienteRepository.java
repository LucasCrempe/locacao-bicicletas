package com.sistema.locacao.repositories;
import com.sistema.locacao.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {}