package com.Ginasio.Treinos.entity.DTO;

import com.Ginasio.Treinos.entity.Enum.Genero;

import java.time.LocalDate;
import java.util.Date;

public record AtletaRequestDTO(String nome, String email, String contacto, String pais, String cidade, String dataNascimento, Double altura, Double peso,Genero genero, String objectivo) {




}
