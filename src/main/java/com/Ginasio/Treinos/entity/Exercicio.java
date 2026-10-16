package com.Ginasio.Treinos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    // Removido o @ManyToOne com RotinaTreino. Opcional:
    @OneToMany(mappedBy = "exercicio")
    private List<ItemTreino> itensTreino;

}
