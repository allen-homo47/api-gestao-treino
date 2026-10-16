package com.Ginasio.Treinos.config;

import com.Ginasio.Treinos.Repository.UsuarioRepository;
import com.Ginasio.Treinos.entity.Enum.TipoUser;
import com.Ginasio.Treinos.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Cria um administrador inicial quando a base de dados não tem nenhum utilizador
@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${api.admin.username:admin}")
    private String adminUsername;

    @Value("${api.admin.password:admin123}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setNome("Administrador");
        admin.setUsername(adminUsername);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setTipoUser(TipoUser.ADMINISTRADOR);
        usuarioRepository.save(admin);
    }
}
