package com.Ginasio.Treinos.entity.DTO;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record PlanoTreinoRequestDTO(String nome, LocalDate dataInicio, LocalDate dataFim) {
}
