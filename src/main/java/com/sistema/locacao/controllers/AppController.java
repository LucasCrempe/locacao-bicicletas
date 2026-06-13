package com.sistema.locacao.controllers;

import com.sistema.locacao.repositories.LocadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    @Autowired
    private LocadoraRepository locadoraRepository;

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String cidade) {
        if (cidade != null && !cidade.isEmpty()) {
            model.addAttribute("locadoras", locadoraRepository.findByCidadeIgnoreCase(cidade));
        } else {
            model.addAttribute("locadoras", locadoraRepository.findAll());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}