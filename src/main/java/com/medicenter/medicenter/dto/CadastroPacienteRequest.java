package com.medicenter.medicenter.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroPacienteRequest(

        @NotBlank(message = "Login é obrigatório")
        @Size(min = 4, max = 60, message = "Login deve ter entre 4 e 60 caracteres")
        @Pattern(regexp = "[A-Za-z0-9._@-]+", message = "Login só pode ter letras, números e . _ @ -")
        String login,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 72, message = "Senha deve ter entre 6 e 72 caracteres")
        String senha,

        @NotNull(message = "Dados do paciente são obrigatórios")
        @Valid
        PacienteRequest paciente
) {
}