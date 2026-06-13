package com.sistema.locacao.controllers;

import com.sistema.locacao.models.Cliente;
import com.sistema.locacao.models.Locadora;
import com.sistema.locacao.repositories.ClienteRepository;
import com.sistema.locacao.repositories.LocadoraRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LocadoraRepository locadoraRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "admin/clientes";
    }

    @GetMapping("/clientes/novo")
    public String novoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "admin/cliente_form";
    }

    @PostMapping("/clientes/salvar")
    public String salvarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/cliente_form";
        }
        cliente.setPapel("ROLE_CLIENTE");
        cliente.setSenha(encoder.encode(cliente.getSenha()));
        clienteRepository.save(cliente);
        return "redirect:/admin/clientes";
    }

    @GetMapping("/locadoras")
    public String listarLocadoras(Model model) {
        model.addAttribute("locadoras", locadoraRepository.findAll());
        return "admin/locadoras";
    }

    @GetMapping("/locadoras/nova")
    public String novaLocadora(Model model) {
        model.addAttribute("locadora", new Locadora());
        return "admin/locadora_form";
    }

    @PostMapping("/locadoras/salvar")
    public String salvarLocadora(@Valid @ModelAttribute Locadora locadora, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/locadora_form";
        }
        locadora.setPapel("ROLE_LOCADORA");
        locadora.setSenha(encoder.encode(locadora.getSenha()));
        locadoraRepository.save(locadora);
        return "redirect:/admin/locadoras";
    }
}