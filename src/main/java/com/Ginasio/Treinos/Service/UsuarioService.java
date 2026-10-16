package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.UsuarioRepository;
import com.Ginasio.Treinos.entity.DTO.UsuarioRequestDTO;
import com.Ginasio.Treinos.entity.DTO.UsuarioRequestPasswordDTO;
import com.Ginasio.Treinos.entity.DTO.UsuarioResponseDTO;
import com.Ginasio.Treinos.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO salvarUsuarios(UsuarioRequestDTO usuarioRequestDTO){

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequestDTO.nome());
        usuario.setUsername(usuarioRequestDTO.username());
        usuario.setPassword(passwordEncoder.encode(usuarioRequestDTO.password()));
        usuario.setTipoUser(usuarioRequestDTO.tipoUser());

        usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getTipoUser());
    }

    public List<UsuarioResponseDTO> listarUsuarios(){

        List<Usuario> usuarios = usuarioRepository.findAll();

       return usuarios.stream().map(usuario -> new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(),usuario.getTipoUser())).toList();

    }


    public UsuarioResponseDTO buscarPorId(Long id){
      Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

      return  new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getTipoUser());


    }

    public void deletarUsuario(Long id){

        Usuario usuarioActual = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));
        usuarioRepository.delete(usuarioActual);

    }

    public UsuarioRequestDTO actualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO){

        Usuario usuarioActual =usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

        usuarioActual.setNome(usuarioRequestDTO.nome());
        usuarioActual.setUsername(usuarioRequestDTO.username());
        usuarioActual.setTipoUser(usuarioRequestDTO.tipoUser());
        // A password só é alterada se vier preenchida
        if (usuarioRequestDTO.password() != null && !usuarioRequestDTO.password().isBlank()) {
            usuarioActual.setPassword(passwordEncoder.encode(usuarioRequestDTO.password()));
        }

        usuarioRepository.save(usuarioActual);


        return usuarioRequestDTO;
    }




    public void mudarPassword(UsuarioRequestPasswordDTO passwordDTO){

        Usuario usuario = usuarioRepository.findById(passwordDTO.id()).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

        usuario.setPassword(passwordEncoder.encode(passwordDTO.password()));

        usuarioRepository.save(usuario);


    }



}
