package com.Ginasio.Treinos.entity.DTO;

public record ItemResponseDTO(Long id, Integer series, String repeticoes, String carga, Integer descanso,
                              Long rotinaId, String rotinaNome, Long exercicioId, String exercicioNome) {
}
