package com.medicenter.medicenter.controller;

import com.medicenter.medicenter.dto.PacienteRequest;
import com.medicenter.medicenter.dto.PacienteResponse;
import com.medicenter.medicenter.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponse criar(@Valid @RequestBody PacienteRequest dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<PacienteResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PacienteResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public PacienteResponse atualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequest dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}