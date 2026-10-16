package com.Ginasio.Treinos.entity;

import com.Ginasio.Treinos.entity.Enum.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "Atleta")
@DiscriminatorValue("atleta") // Manteve exatamente igual ao valor 'Atleta' gravado no UPDATE do banco
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atleta extends Usuario {

    private String contacto;
    private Double peso;
    private Double altura;
    private String dataNascimento;
    private String cidade;
    private String pais;
    private String objectivo;
    private String email;

    @Enumerated(EnumType.STRING)
    private Genero genero;


    @ManyToOne
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;


    @OneToMany(mappedBy = "atleta")
    private List<PlanoTreino> planoTreino;

}