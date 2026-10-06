package com.medicenter.medicenter.dto;

import com.medicenter.medicenter.entity.Funcionario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FuncionarioResponse(
        Long id,
        String nome,
        String cpf,
        String cargo,
        LocalDate dataAdmissao,
        String telefone,
        String email,
        LocalDateTime dataCadastro
) {
    public static FuncionarioResponse de(Funcionario f) {
        return new FuncionarioResponse(
                f.getId(),
                f.getNome(),
                f.getCpf(),
                f.getCargo(),
                f.getDataAdmissao(),
                f.getTelefone(),
                f.getEmail(),
                f.getDataCadastro()
        );
    }
}