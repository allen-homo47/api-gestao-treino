package com.Ginasio.Treinos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exercicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeExercicio;
    private String grupoMuscular;
    private String urlVideoDemonstrativo;

    @ManyToOne
    @JoinColumn(name ="rotina_treino_id", nullable = false)
    private RotinaTreino rotinaTreino;

}
