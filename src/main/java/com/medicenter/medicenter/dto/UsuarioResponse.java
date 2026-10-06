package com.medicenter.medicenter.dto;

import com.medicenter.medicenter.entity.Perfil;
import com.medicenter.medicenter.entity.Usuario;

public record UsuarioResponse(
        Long id,
        String login,
        Perfil perfil,
        boolean ativo
) {
    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getLogin(), u.getPerfil(), u.isAtivo());
    }
}