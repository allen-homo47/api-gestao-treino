package com.Ginasio.Treinos.entity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MudarPasswordDTO(@NotBlank String passwordActual, @NotBlank @Size(min = 6) String novaPassword) {
}
