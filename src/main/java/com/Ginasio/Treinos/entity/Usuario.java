package com.Ginasio.Treinos.entity;

import com.Ginasio.Treinos.entity.Enum.TipoUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario") // Tabela no plural para coincidir com o phpMyAdmin
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private TipoUser tipoUser;

    // --- Métodos obrigatórios da interface UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define a role/permissão padrão do utilizador
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Garante que a conta não está expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Garante que a conta não está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Garante que as credenciais não estão expiradas
    }

    @Override
    public boolean isEnabled() {
        return true; // Garante que o utilizador está ativo
    }
}