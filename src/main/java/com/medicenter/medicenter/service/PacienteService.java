package com.medicenter.medicenter.service;

import com.medicenter.medicenter.dto.PacienteRequest;
import com.medicenter.medicenter.dto.PacienteResponse;
import com.medicenter.medicenter.entity.Paciente;
import com.medicenter.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.medicenter.exception.RegraDeNegocioException;
import com.medicenter.medicenter.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;

    @Transactional
    public PacienteResponse criar(PacienteRequest dados) {
        if (repository.existsByCpf(dados.cpf())) {
            throw new RegraDeNegocioException("Já existe um paciente cadastrado com este CPF");
        }
        Paciente paciente = new Paciente();
        copiarDados(dados, paciente);
        return PacienteResponse.de(repository.save(paciente));
    }

    @Transactional(readOnly = true)
    public List<PacienteResponse> listar() {
        return repository.findAll().stream()
                .map(PacienteResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscarPorId(Long id) {
        return PacienteResponse.de(obter(id));
    }

    @Transactional
    public PacienteResponse atualizar(Long id, PacienteRequest dados) {
        Paciente paciente = obter(id);
        if (repository.existsByCpfAndIdNot(dados.cpf(), id)) {
            throw new RegraDeNegocioException("Já existe outro paciente com este CPF");
        }
        copiarDados(dados, paciente);
        return PacienteResponse.de(repository.save(paciente));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(obter(id));
    }

    private Paciente obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado: " + id));
    }

    private void copiarDados(PacienteRequest dados, Paciente paciente) {
        paciente.setNome(dados.nome());
        paciente.setCpf(dados.cpf());
        paciente.setDataNascimento(dados.dataNascimento());
        paciente.setTelefone(dados.telefone());
        paciente.setEmail(dados.email());
        paciente.setEndereco(dados.endereco());
    }
}