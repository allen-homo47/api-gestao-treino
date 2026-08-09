package com.Ginasio.Treinos.entity.DTO;

import com.Ginasio.Treinos.entity.Enum.TipoUser;

import java.util.Timer;

public record UsuarioRequestDTO(String nome, String username, String password, TipoUser tipoUser) {


}
