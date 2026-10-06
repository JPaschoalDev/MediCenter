package com.medicenter.medicenter.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record FuncionarioRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 números, sem pontos e traço")
        String cpf,

        @NotBlank(message = "Cargo é obrigatório")
        @Size(max = 80, message = "Cargo deve ter no máximo 80 caracteres")
        String cargo,

        @NotNull(message = "Data de admissão é obrigatória")
        @PastOrPresent(message = "Data de admissão não pode ser futura")
        LocalDate dataAdmissao,

        @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
        String telefone,

        @Email(message = "E-mail inválido")
        @Size(max = 120, message = "E-mail deve ter no máximo 120 caracteres")
        String email
) {
}