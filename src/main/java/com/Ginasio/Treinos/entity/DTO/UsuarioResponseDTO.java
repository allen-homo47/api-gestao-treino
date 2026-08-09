package com.Ginasio.Treinos.entity.DTO;

import com.Ginasio.Treinos.entity.Enum.TipoUser;

public record UsuarioResponseDTO(Long id, String username, TipoUser tipoUser) {
}
