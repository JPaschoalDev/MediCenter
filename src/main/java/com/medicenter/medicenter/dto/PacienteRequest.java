package com.medicenter.medicenter.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PacienteRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 números, sem pontos e traço")
        String cpf,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNascimento,

        @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
        String telefone,

        @Email(message = "E-mail inválido")
        @Size(max = 120, message = "E-mail deve ter no máximo 120 caracteres")
        String email,

        @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
        String endereco
) {
}