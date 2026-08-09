package com.Ginasio.Treinos.entity;

import com.Ginasio.Treinos.entity.Enum.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name ="atleta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atleta extends Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    private String objectivo;
    private Double peso;
    private Double altura;
    private Genero genero;
    private String contacto;
    private String email;
    private String pais;
    private String cidade;
    private String dataNasimento;

}
