package com.Ginasio.Treinos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name= "plano_treino")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlanoTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomePlano;
    private  LocalDate dataInicio;
    private LocalDate dataFim;

    @ManyToOne
    @JoinColumn(name= "atleta_id" , nullable= false)
    private Atleta atleta;
}
