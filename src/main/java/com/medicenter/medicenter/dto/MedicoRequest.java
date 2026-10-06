package com.medicenter.medicenter.dto;

import jakarta.validation.constraints.*;

public record MedicoRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 números, sem pontos e traço")
        String cpf,

        @NotBlank(message = "CRM é obrigatório")
        @Size(max = 20, message = "CRM deve ter no máximo 20 caracteres")
        String crm,

        @NotBlank(message = "Especialidade é obrigatória")
        @Size(max = 80, message = "Especialidade deve ter no máximo 80 caracteres")
        String especialidade,

        @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
        String telefone,

        @Email(message = "E-mail inválido")
        @Size(max = 120, message = "E-mail deve ter no máximo 120 caracteres")
        String email
) {
}