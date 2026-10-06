package com.medicenter.medicenter.dto;

import com.medicenter.medicenter.entity.Perfil;
import org.springframework.security.core.Authentication;

public record UsuarioLogadoResponse(
        String login,
        Perfil perfil
) {
    public static UsuarioLogadoResponse de(Authentication autenticacao) {
        String papel = autenticacao.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");
        return new UsuarioLogadoResponse(autenticacao.getName(), Perfil.valueOf(papel));
    }
}