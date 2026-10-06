package com.medicenter.medicenter.controller;

import com.medicenter.medicenter.dto.MedicoRequest;
import com.medicenter.medicenter.dto.MedicoResponse;
import com.medicenter.medicenter.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicoResponse criar(@Valid @RequestBody MedicoRequest dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<MedicoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MedicoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public MedicoResponse atualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequest dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}