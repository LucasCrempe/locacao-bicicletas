package com.sistema.locacao.controllers.rest;

import com.sistema.locacao.models.Cliente;
import com.sistema.locacao.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        cliente.setPapel("ROLE_CLIENTE");
        cliente.setSenha(encoder.encode(cliente.getSenha()));
        return ResponseEntity.ok(repository.save(cliente));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> readAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> read(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        return repository.findById(id).map(cliente -> {
            cliente.setNome(clienteDetails.getNome());
            cliente.setCpf(clienteDetails.getCpf());
            cliente.setTelefone(clienteDetails.getTelefone());
            cliente.setSexo(clienteDetails.getSexo());
            cliente.setDataNascimento(clienteDetails.getDataNascimento());
            cliente.setEmail(clienteDetails.getEmail());
            
            if (clienteDetails.getSenha() != null && !clienteDetails.getSenha().isEmpty()) {
                cliente.setSenha(encoder.encode(clienteDetails.getSenha()));
            }
            return ResponseEntity.ok(repository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        try {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}