package com.sistema.locacao.repositories;
import com.sistema.locacao.models.Locadora;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface LocadoraRepository extends JpaRepository<Locadora, Long> {
    List<Locadora> findByCidadeContainingIgnoreCase(String cidade);
}