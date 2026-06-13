package com.sistema.locacao.controllers;

import com.sistema.locacao.models.Cliente;
import com.sistema.locacao.models.Locacao;
import com.sistema.locacao.repositories.LocacaoRepository;
import com.sistema.locacao.repositories.LocadoraRepository;
import com.sistema.locacao.security.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private LocadoraRepository locadoraRepository;

    @GetMapping("/locacoes")
    public String minhasLocacoes(@AuthenticationPrincipal UsuarioDetails userDetails, Model model) {
        Cliente cliente = (Cliente) userDetails.getUsuario();
        model.addAttribute("locacoes", locacaoRepository.findByCliente(cliente));
        return "cliente/locacoes";
    }

    @GetMapping("/locacoes/nova")
    public String novaLocacao(Model model) {
        model.addAttribute("locadoras", locadoraRepository.findAll());
        model.addAttribute("locacao", new Locacao());
        return "cliente/locacao_form";
    }

    @PostMapping("/locacoes/salvar")
    public String salvarLocacao(@ModelAttribute Locacao locacao, @AuthenticationPrincipal UsuarioDetails userDetails, Model model) {
        Cliente cliente = (Cliente) userDetails.getUsuario();
        locacao.setCliente(cliente);
        LocalDateTime horaCheia = locacao.getDataHora().withMinute(0).withSecond(0).withNano(0);
        locacao.setDataHora(horaCheia);

        if (locacaoRepository.existsByClienteAndDataHora(cliente, horaCheia)) {
            model.addAttribute("erro", "Você já possui uma locação neste horário.");
            model.addAttribute("locadoras", locadoraRepository.findAll());
            return "cliente/locacao_form";
        }

        if (locacaoRepository.existsByLocadoraAndDataHora(locacao.getLocadora(), horaCheia)) {
            model.addAttribute("erro", "A locadora já possui uma locação neste horário.");
            model.addAttribute("locadoras", locadoraRepository.findAll());
            return "cliente/locacao_form";
        }

        locacaoRepository.save(locacao);
        System.out.println("E-mail enviado para: " + cliente.getEmail() + " e " + locacao.getLocadora().getEmail());
        return "redirect:/cliente/locacoes";
    }
}