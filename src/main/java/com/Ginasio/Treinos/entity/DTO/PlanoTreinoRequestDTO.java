package com.Ginasio.Treinos.entity.DTO;

import java.time.LocalDate;

public record PlanoTreinoRequestDTO(String nome, LocalDate dataInicio, LocalDate dataFim, Long atletaId) {
}
