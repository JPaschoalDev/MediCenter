package com.medicenter.medicenter.service;

import com.medicenter.medicenter.dto.MedicoRequest;
import com.medicenter.medicenter.dto.MedicoResponse;
import com.medicenter.medicenter.entity.Medico;
import com.medicenter.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.medicenter.exception.RegraDeNegocioException;
import com.medicenter.medicenter.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository repository;

    @Transactional
    public MedicoResponse criar(MedicoRequest dados) {
        if (repository.existsByCpf(dados.cpf())) {
            throw new RegraDeNegocioException("Já existe um médico cadastrado com este CPF");
        }
        if (repository.existsByCrm(dados.crm())) {
            throw new RegraDeNegocioException("Já existe um médico cadastrado com este CRM");
        }
        Medico medico = new Medico();
        copiarDados(dados, medico);
        return MedicoResponse.de(repository.save(medico));
    }

    @Transactional(readOnly = true)
    public List<MedicoResponse> listar() {
        return repository.findAll().stream()
                .map(MedicoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public MedicoResponse buscarPorId(Long id) {
        return MedicoResponse.de(obter(id));
    }

    @Transactional
    public MedicoResponse atualizar(Long id, MedicoRequest dados) {
        Medico medico = obter(id);
        if (repository.existsByCpfAndIdNot(dados.cpf(), id)) {
            throw new RegraDeNegocioException("Já existe outro médico com este CPF");
        }
        if (repository.existsByCrmAndIdNot(dados.crm(), id)) {
            throw new RegraDeNegocioException("Já existe outro médico com este CRM");
        }
        copiarDados(dados, medico);
        return MedicoResponse.de(repository.save(medico));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(obter(id));
    }

    private Medico obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado: " + id));
    }

    private void copiarDados(MedicoRequest dados, Medico medico) {
        medico.setNome(dados.nome());
        medico.setCpf(dados.cpf());
        medico.setCrm(dados.crm());
        medico.setEspecialidade(dados.especialidade());
        medico.setTelefone(dados.telefone());
        medico.setEmail(dados.email());
    }
}