package com.Ginasio.Treinos.entity.DTO;

import java.time.LocalDate;
import java.util.List;

// Plano completo do atleta: plano -> rotinas -> itens (com o exercício)
public record PlanoDetalheDTO(Long id, String nome, LocalDate dataInicio, LocalDate dataFim, List<Rotina> rotinas) {

    public record Rotina(Long id, String nome, String diaSemana, List<Item> itens) {
    }

    public record Item(Long id, String exercicio, String grupoMuscular, String urlVideoDemonstrativo,
                       Integer series, String repeticoes, String carga, Integer descanso) {
    }
}
