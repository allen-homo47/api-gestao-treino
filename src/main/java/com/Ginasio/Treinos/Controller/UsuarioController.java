package com.Ginasio.Treinos.Controller;


import com.Ginasio.Treinos.Service.UsuarioService;
import com.Ginasio.Treinos.entity.DTO.UsuarioRequestDTO;
import com.Ginasio.Treinos.entity.DTO.UsuarioRequestPasswordDTO;
import com.Ginasio.Treinos.entity.DTO.UsuarioResponseDTO;
import com.Ginasio.Treinos.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/addUsuario")
    public ResponseEntity<UsuarioResponseDTO> addUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO responseDTO= usuarioService.salvarUsuarios(usuarioRequestDTO);
        return ResponseEntity.ok(responseDTO);


    }

    @GetMapping("/listaUsuarios")
    public  ResponseEntity<List<UsuarioResponseDTO>> listarTodos(){

        List<UsuarioResponseDTO> lista = usuarioService.listarUsuarios();

        return ResponseEntity.ok(lista);
    }


    @GetMapping("buscar/{id}")
    public  ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable  Long id){

        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
        if (usuario != null){
            return  ResponseEntity.ok(usuario);
        }else{

            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("apagar/{id}")
    public ResponseEntity<Void> apagarUsuario(@PathVariable Long id){


        usuarioService.deletarUsuario(id);

        return ResponseEntity.ok().build();

    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO novoUsuario){

        UsuarioRequestDTO usuario = usuarioService.actualizarUsuario(id, novoUsuario);

        return ResponseEntity.noContent().build();


    }
    @PatchMapping("/mudarSenha")
    public ResponseEntity<Void> mudarSenha(UsuarioRequestPasswordDTO novoPassword){

        usuarioService.mudarPassword(novoPassword);
        return  ResponseEntity.ok().build();
    }



}
