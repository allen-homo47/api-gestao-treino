package com.Ginasio.Treinos.Controller;


import com.Ginasio.Treinos.Service.UsuarioService;
import com.Ginasio.Treinos.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("treino/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/addUsuario")
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario){
        Usuario saved = usuarioService.salvarUsuarios(usuario);
        return ResponseEntity.ok(saved);


    }

    @GetMapping("/listaUsuarios")
    public  ResponseEntity<List<Usuario>> listarTodos(){

        List<Usuario> lista = usuarioService.listarUsuarios();

        if (lista.isEmpty()){
            return null;
        }
        return ResponseEntity.ok(lista);
    }


    @GetMapping("buscar/{id}")
    public  ResponseEntity<Usuario> buscarPorId(@PathVariable  Long id){

        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null){
            return  ResponseEntity.ok(usuario);
        }else{

            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("apagar/{id}")
    public ResponseEntity<Usuario> apagarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){


        usuarioService.deletarUsuario(id);

        return ResponseEntity.ok().build();

    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario novoUsuario){

        Usuario usuarioActual = usuarioService.buscarPorId(id);
        usuarioActual.setNome(novoUsuario.getNome());
        usuarioActual.setPassword(novoUsuario.getPassword());
        usuarioActual.setTipoUser(novoUsuario.getTipoUser());
        Usuario saved = usuarioService.salvarUsuarios(usuarioActual);


        return ResponseEntity.ok(saved);

    }


}
