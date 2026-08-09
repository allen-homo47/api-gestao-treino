package com.Ginasio.Treinos.entity.DTO;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacaoDTO(@NotBlank String username, @NotBlank String password) {
}
