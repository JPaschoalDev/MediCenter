package com.medicenter.medicenter.dto;

import com.medicenter.medicenter.entity.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PacienteResponse(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String telefone,
        String email,
        String endereco,
        LocalDateTime dataCadastro
) {
    public static PacienteResponse de(Paciente p) {
        return new PacienteResponse(
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getDataNascimento(),
                p.getTelefone(),
                p.getEmail(),
                p.getEndereco(),
                p.getDataCadastro()
        );
    }
}