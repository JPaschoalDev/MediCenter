package com.medicenter.medicenter.service;

import com.medicenter.medicenter.dto.FuncionarioRequest;
import com.medicenter.medicenter.dto.FuncionarioResponse;
import com.medicenter.medicenter.entity.Funcionario;
import com.medicenter.medicenter.exception.RecursoNaoEncontradoException;
import com.medicenter.medicenter.exception.RegraDeNegocioException;
import com.medicenter.medicenter.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;

    @Transactional
    public FuncionarioResponse criar(FuncionarioRequest dados) {
        if (repository.existsByCpf(dados.cpf())) {
            throw new RegraDeNegocioException("Já existe um funcionário cadastrado com este CPF");
        }
        Funcionario funcionario = new Funcionario();
        copiarDados(dados, funcionario);
        return FuncionarioResponse.de(repository.save(funcionario));
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponse> listar() {
        return repository.findAll().stream()
                .map(FuncionarioResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public FuncionarioResponse buscarPorId(Long id) {
        return FuncionarioResponse.de(obter(id));
    }

    @Transactional
    public FuncionarioResponse atualizar(Long id, FuncionarioRequest dados) {
        Funcionario funcionario = obter(id);
        if (repository.existsByCpfAndIdNot(dados.cpf(), id)) {
            throw new RegraDeNegocioException("Já existe outro funcionário com este CPF");
        }
        copiarDados(dados, funcionario);
        return FuncionarioResponse.de(repository.save(funcionario));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(obter(id));
    }

    private Funcionario obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado: " + id));
    }

    private void copiarDados(FuncionarioRequest dados, Funcionario funcionario) {
        funcionario.setNome(dados.nome());
        funcionario.setCpf(dados.cpf());
        funcionario.setCargo(dados.cargo());
        funcionario.setDataAdmissao(dados.dataAdmissao());
        funcionario.setTelefone(dados.telefone());
        funcionario.setEmail(dados.email());
    }
}