package com.medicenter.medicenter.controller;

import com.medicenter.medicenter.dto.UsuarioRequest;
import com.medicenter.medicenter.dto.UsuarioResponse;
import com.medicenter.medicenter.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse criarAcesso(@Valid @RequestBody UsuarioRequest dados) {
        return service.criarAcesso(dados);
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listar();
    }
}