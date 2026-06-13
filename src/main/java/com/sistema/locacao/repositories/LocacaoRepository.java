package com.sistema.locacao.repositories;
import com.sistema.locacao.models.Cliente;
import com.sistema.locacao.models.Locacao;
import com.sistema.locacao.models.Locadora;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
    List<Locacao> findByCliente(Cliente cliente);
    List<Locacao> findByLocadora(Locadora locadora);
    boolean existsByClienteAndDataHora(Cliente cliente, LocalDateTime dataHora);
    boolean existsByLocadoraAndDataHora(Locadora locadora, LocalDateTime dataHora);
}