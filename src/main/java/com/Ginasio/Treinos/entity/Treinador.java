package com.Ginasio.Treinos.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "treinadores")
@AllArgsConstructor
@NoArgsConstructor
public class Treinador extends Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;


    @OneToMany(mappedBy = "treinador")
    private List<Atleta> listaAtleta;
}
