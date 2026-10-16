package com.Ginasio.Treinos.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("TREINADOR")
@AllArgsConstructor
@NoArgsConstructor
public class Treinador extends Usuario {

    // O id já é herdado de Usuario! Não precisa declarar aqui.

    private String descricao;

    @OneToMany(mappedBy = "treinador")
    private List<Atleta> listaAtleta;

    @OneToMany(mappedBy = "treinador") // corrigido o espaço extra
    private List<PlanoTreino> planoTreino;
}