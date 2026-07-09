package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.UsuarioRepository;
import com.Ginasio.Treinos.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuarios(Usuario usuario){
        return usuarioRepository.save(usuario);


    }

    public List<Usuario> listarUsuarios(){

        List<Usuario> lista = usuarioRepository.findAll();
        if (lista.isEmpty()){
            return null;
        }
        return lista;
    }


    public Usuario buscarPorId(Long id){
      return usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

    }

    public void deletarUsuario(Long id){

        Usuario usuarioActual = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));
        usuarioRepository.delete(usuarioActual);

    }

    public Usuario actualizarDados(Long id, Usuario novoUsuario){

       Usuario usuarioActual =usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

       usuarioActual.setNome(novoUsuario.getNome());
       usuarioActual.setPassword(novoUsuario.getPassword());
       usuarioActual.setTipoUser(novoUsuario.getTipoUser());
         return usuarioRepository.save(usuarioActual);


    }



}
