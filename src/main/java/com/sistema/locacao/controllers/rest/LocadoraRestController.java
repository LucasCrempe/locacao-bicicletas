package com.sistema.locacao.controllers.rest;

import com.sistema.locacao.models.Locadora;
import com.sistema.locacao.repositories.LocadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locadoras")
public class LocadoraRestController {

    @Autowired
    private LocadoraRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<Locadora> create(@RequestBody Locadora locadora) {
        locadora.setPapel("ROLE_LOCADORA");
        locadora.setSenha(encoder.encode(locadora.getSenha()));
        return ResponseEntity.ok(repository.save(locadora));
    }

    @GetMapping
    public ResponseEntity<List<Locadora>> readAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locadora> read(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cidades/{nome}")
    public ResponseEntity<List<Locadora>> readByCidade(@PathVariable String nome) {
        return ResponseEntity.ok(repository.findByCidadeContainingIgnoreCase(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locadora> update(@PathVariable Long id, @RequestBody Locadora locadoraDetails) {
        return repository.findById(id).map(locadora -> {
            locadora.setNome(locadoraDetails.getNome());
            locadora.setCnpj(locadoraDetails.getCnpj());
            locadora.setCidade(locadoraDetails.getCidade());
            locadora.setEmail(locadoraDetails.getEmail());
            if (locadoraDetails.getSenha() != null && !locadoraDetails.getSenha().isEmpty()) {
                locadora.setSenha(encoder.encode(locadoraDetails.getSenha()));
            }
            return ResponseEntity.ok(repository.save(locadora));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}