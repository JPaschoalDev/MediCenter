package com.medicenter.medicenter.config;

import com.medicenter.medicenter.entity.Perfil;
import com.medicenter.medicenter.entity.Usuario;
import com.medicenter.medicenter.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInicial implements CommandLineRunner {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;

    @Value("${medicenter.admin.senha:admin123}")
    private String senhaAdmin;

    @Override
    public void run(String... args) {
        if (usuarios.existsByLogin("admin")) {
            return;
        }
        Usuario admin = new Usuario();
        admin.setLogin("admin");
        admin.setSenha(encoder.encode(senhaAdmin));
        admin.setPerfil(Perfil.FUNCIONARIO);
        usuarios.save(admin);
    }
}