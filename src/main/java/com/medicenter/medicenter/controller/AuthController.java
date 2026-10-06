package com.medicenter.medicenter.controller;

import com.medicenter.medicenter.dto.CadastroPacienteRequest;
import com.medicenter.medicenter.dto.LoginRequest;
import com.medicenter.medicenter.dto.UsuarioLogadoResponse;
import com.medicenter.medicenter.dto.UsuarioResponse;
import com.medicenter.medicenter.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @PostMapping("/login")
    public UsuarioLogadoResponse login(@Valid @RequestBody LoginRequest dados,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        Authentication autenticacao = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(dados.login(), dados.senha()));

        if (request.getSession(false) != null) {
            request.changeSessionId();
        }

        SecurityContext contexto = SecurityContextHolder.createEmptyContext();
        contexto.setAuthentication(autenticacao);
        SecurityContextHolder.setContext(contexto);
        securityContextRepository.saveContext(contexto, request, response);

        return UsuarioLogadoResponse.de(autenticacao);
    }

    @GetMapping("/me")
    public UsuarioLogadoResponse me(Authentication autenticacao) {
        return UsuarioLogadoResponse.de(autenticacao);
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@Valid @RequestBody CadastroPacienteRequest dados) {
        return usuarioService.cadastrarPaciente(dados);
    }
}