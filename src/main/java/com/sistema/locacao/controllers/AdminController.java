package com.sistema.locacao.controllers;

import com.sistema.locacao.models.Cliente;
import com.sistema.locacao.models.Locadora;
import com.sistema.locacao.repositories.ClienteRepository;
import com.sistema.locacao.repositories.LocadoraRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder encoder;


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

    @GetMapping("/clientes/editar/{id}")
    public String editarCliente(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de cliente inválido: " + id));
        cliente.setSenha(""); 
        model.addAttribute("cliente", cliente);
        return "admin/cliente_form";
    }

    @PostMapping("/clientes/salvar")
    public String salvarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model) {
        // Agora verificamos exatamente ONDE está o erro e mandamos para a tela
        if (result.hasErrors()) {
            if (result.hasFieldErrors("cpf")) {
                model.addAttribute("erro", "Validação falhou: O CPF digitado é INVÁLIDO. Utilize um CPF real.");
            } else if (result.hasFieldErrors("senha")) {
                model.addAttribute("erro", "Validação falhou: A senha é obrigatória.");
            } else if (result.hasFieldErrors("email")) {
                model.addAttribute("erro", "Validação falhou: E-mail inválido.");
            } else {
                model.addAttribute("erro", "Validação falhou: Preencha todos os campos corretamente.");
            }
            return "admin/cliente_form";
        }
        
        try {
            cliente.setPapel("ROLE_CLIENTE");
            cliente.setSenha(encoder.encode(cliente.getSenha()));
            clienteRepository.save(cliente);
            return "redirect:/admin/clientes";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro no banco de dados: Este E-mail ou CPF já está cadastrado.");
            return "admin/cliente_form";
        }
    }

    @GetMapping("/clientes/excluir/{id}")
    public String excluirCliente(@PathVariable("id") Long id) {
        clienteRepository.deleteById(id);
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

    @GetMapping("/locadoras/editar/{id}")
    public String editarLocadora(@PathVariable("id") Long id, Model model) {
        Locadora locadora = locadoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de locadora inválido: " + id));
        locadora.setSenha("");
        model.addAttribute("locadora", locadora);
        return "admin/locadora_form";
    }

    @PostMapping("/locadoras/salvar")
    public String salvarLocadora(@Valid @ModelAttribute Locadora locadora, BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("cnpj")) {
                model.addAttribute("erro", "Validação falhou: O CNPJ digitado é INVÁLIDO. Utilize um CNPJ real.");
            } else if (result.hasFieldErrors("senha")) {
                model.addAttribute("erro", "Validação falhou: A senha é obrigatória.");
            } else {
                model.addAttribute("erro", "Validação falhou: Preencha todos os campos corretamente.");
            }
            return "admin/locadora_form";
        }
        
        try {
            locadora.setPapel("ROLE_LOCADORA");
            locadora.setSenha(encoder.encode(locadora.getSenha()));
            locadoraRepository.save(locadora);
            return "redirect:/admin/locadoras";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro no banco de dados: Este E-mail ou CNPJ já está cadastrado.");
            return "admin/locadora_form";
        }
    }

    @GetMapping("/locadoras/excluir/{id}")
    public String excluirLocadora(@PathVariable("id") Long id) {
        locadoraRepository.deleteById(id);
        return "redirect:/admin/locadoras";
    }
}