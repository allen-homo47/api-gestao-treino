package com.Ginasio.Treinos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @JoinColumn(name = "atleta_id")
    private Atleta atleta;

    @ManyToOne
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;

    // MappedBy corrigido para o atributo em RotinaTreino
    @OneToMany(mappedBy = "planoTreino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RotinaTreino> rotinaTreino;

}
