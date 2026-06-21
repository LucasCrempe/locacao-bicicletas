package com.sistema.locacao.controllers.rest;

import com.sistema.locacao.models.Locacao;
import com.sistema.locacao.repositories.ClienteRepository;
import com.sistema.locacao.repositories.LocacaoRepository;
import com.sistema.locacao.repositories.LocadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locacoes")
public class LocacaoRestController {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LocadoraRepository locadoraRepository;

    @GetMapping
    public ResponseEntity<List<Locacao>> readAll() {
        return ResponseEntity.ok(locacaoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locacao> read(@PathVariable Long id) {
        return locacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<List<Locacao>> readByCliente(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok(locacaoRepository.findByCliente(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/locadoras/{id}")
    public ResponseEntity<List<Locacao>> readByLocadora(@PathVariable Long id) {
        return locadoraRepository.findById(id)
                .map(locadora -> ResponseEntity.ok(locacaoRepository.findByLocadora(locadora)))
                .orElse(ResponseEntity.notFound().build());
    }
}