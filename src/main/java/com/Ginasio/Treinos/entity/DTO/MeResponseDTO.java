package com.Ginasio.Treinos.entity.DTO;

import com.Ginasio.Treinos.entity.Enum.TipoUser;

public record MeResponseDTO(Long id, String nome, String username, TipoUser tipoUser) {
}
