package com.Ginasio.Treinos.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "rotina_treino")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RotinaTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String diaSemana;

    @ManyToOne
    @JoinColumn(name = "plano_treino_id" , nullable= false)
    private PlanoTreino planoTreino;
}
