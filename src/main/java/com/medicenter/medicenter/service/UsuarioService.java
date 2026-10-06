package com.medicenter.medicenter.service;

import com.medicenter.medicenter.dto.CadastroPacienteRequest;
import com.medicenter.medicenter.dto.UsuarioRequest;
import com.medicenter.medicenter.dto.UsuarioResponse;
import com.medicenter.medicenter.entity.*;
import com.medicenter.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.medicenter.exception.RegraDeNegocioException;
import com.medicenter.medicenter.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarios;
    private final PacienteRepository pacientes;
    private final MedicoRepository medicos;
    private final FuncionarioRepository funcionarios;
    private final PacienteService pacienteService;
    private final PasswordEncoder encoder;

    // Auto-cadastro público: cria o usuário e o paciente juntos
    @Transactional
    public UsuarioResponse cadastrarPaciente(CadastroPacienteRequest dados) {
        Usuario usuario = novoUsuario(dados.login(), dados.senha(), Perfil.PACIENTE);
        Long pacienteId = pacienteService.criar(dados.paciente()).id();
        Paciente paciente = pacientes.findById(pacienteId).orElseThrow();
        paciente.setUsuario(usuario);
        return UsuarioResponse.de(usuario);
    }

    // Funcionário cria o acesso de alguém que já está cadastrado
    @Transactional
    public UsuarioResponse criarAcesso(UsuarioRequest dados) {
        Usuario usuario = switch (dados.perfil()) {
            case PACIENTE -> vincularPaciente(dados);
            case MEDICO -> vincularMedico(dados);
            case FUNCIONARIO -> vincularFuncionario(dados);
        };
        return UsuarioResponse.de(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarios.findAll().stream()
                .map(UsuarioResponse::de)
                .toList();
    }

    private Usuario vincularPaciente(UsuarioRequest dados) {
        Paciente pessoa = pacientes.findById(dados.pessoaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado: " + dados.pessoaId()));
        if (pessoa.getUsuario() != null) {
            throw new RegraDeNegocioException("Este paciente já possui acesso");
        }
        Usuario usuario = novoUsuario(dados.login(), dados.senha(), Perfil.PACIENTE);
        pessoa.setUsuario(usuario);
        return usuario;
    }

    private Usuario vincularMedico(UsuarioRequest dados) {
        Medico pessoa = medicos.findById(dados.pessoaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado: " + dados.pessoaId()));
        if (pessoa.getUsuario() != null) {
            throw new RegraDeNegocioException("Este médico já possui acesso");
        }
        Usuario usuario = novoUsuario(dados.login(), dados.senha(), Perfil.MEDICO);
        pessoa.setUsuario(usuario);
        return usuario;
    }

    private Usuario vincularFuncionario(UsuarioRequest dados) {
        Funcionario pessoa = funcionarios.findById(dados.pessoaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado: " + dados.pessoaId()));
        if (pessoa.getUsuario() != null) {
            throw new RegraDeNegocioException("Este funcionário já possui acesso");
        }
        Usuario usuario = novoUsuario(dados.login(), dados.senha(), Perfil.FUNCIONARIO);
        pessoa.setUsuario(usuario);
        return usuario;
    }

    private Usuario novoUsuario(String login, String senha, Perfil perfil) {
        if (usuarios.existsByLogin(login)) {
            throw new RegraDeNegocioException("Este login já está em uso");
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(encoder.encode(senha));
        usuario.setPerfil(perfil);
        return usuarios.save(usuario);
    }
}