package com.sistema.locacao.controllers;

import com.sistema.locacao.models.Locadora;
import com.sistema.locacao.repositories.LocacaoRepository;
import com.sistema.locacao.security.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/locadora")
public class LocadoraController {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @GetMapping("/locacoes")
    public String locacoesRecebidas(@AuthenticationPrincipal UsuarioDetails userDetails, Model model) {
        Locadora locadora = (Locadora) userDetails.getUsuario();
        model.addAttribute("locacoes", locacaoRepository.findByLocadora(locadora));
        return "locadora/locacoes";
    }
}