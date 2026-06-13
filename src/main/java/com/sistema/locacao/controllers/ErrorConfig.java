package com.sistema.locacao.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorConfig implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        System.err.println("Ocorreu um erro na aplicação.");
        return "error";
    }
}