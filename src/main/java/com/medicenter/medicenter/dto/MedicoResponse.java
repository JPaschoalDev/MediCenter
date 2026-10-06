package com.medicenter.medicenter.dto;

import com.medicenter.medicenter.entity.Medico;

import java.time.LocalDateTime;

public record MedicoResponse(
        Long id,
        String nome,
        String cpf,
        String crm,
        String especialidade,
        String telefone,
        String email,
        LocalDateTime dataCadastro
) {
    public static MedicoResponse de(Medico m) {
        return new MedicoResponse(
                m.getId(),
                m.getNome(),
                m.getCpf(),
                m.getCrm(),
                m.getEspecialidade(),
                m.getTelefone(),
                m.getEmail(),
                m.getDataCadastro()
        );
    }
}