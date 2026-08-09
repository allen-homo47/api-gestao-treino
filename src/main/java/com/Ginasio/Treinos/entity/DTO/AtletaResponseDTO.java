package com.Ginasio.Treinos.entity.DTO;

import com.Ginasio.Treinos.entity.Enum.Genero;

import java.time.LocalDate;

public record AtletaResponseDTO(Long id, String nome, String email, String contacto, String pais, String cidade, String dataNascimento, Genero genero, Double altura, Double peso, String objectivo) {
}
