package com.Ginasio.Treinos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "itemTreino")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer series;
    private String repeticoes;
    private String carga;
    private Integer descanso;

    @ManyToOne
    @JoinColumn(name = "rotinatreino_id")
    private RotinaTreino rotinaTreino;


    @ManyToOne
    @JoinColumn(name = "exercicio_id")
    private Exercicio exercicio;
}
