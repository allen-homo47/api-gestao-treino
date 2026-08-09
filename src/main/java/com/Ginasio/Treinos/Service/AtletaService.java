package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.AtletaRepository;
import com.Ginasio.Treinos.entity.Atleta;
import com.Ginasio.Treinos.entity.DTO.AtletaListaDTO;
import com.Ginasio.Treinos.entity.DTO.AtletaRequestDTO;
import com.Ginasio.Treinos.entity.DTO.AtletaResponseDTO;
import com.Ginasio.Treinos.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtletaService {

    private final AtletaRepository atletaRepository;


    public AtletaResponseDTO criarAtleta(AtletaRequestDTO atletaRequestDTO){

        Atleta atleta = new Atleta();
        atleta.setNome(atletaRequestDTO.nome());
        atleta.setContacto(atletaRequestDTO.contacto());
        atleta.setEmail(atletaRequestDTO.email());
        atleta.setDataNasimento(atletaRequestDTO.dataNascimento());
        atleta.setGenero(atletaRequestDTO.genero());
        atleta.setPais(atletaRequestDTO.pais());
        atleta.setCidade(atletaRequestDTO.cidade());
        atleta.setPeso(atletaRequestDTO.peso());
        atleta.setAltura(atletaRequestDTO.altura());
        atleta.setObjectivo(atletaRequestDTO.objectivo());
        atletaRepository.save(atleta);
        return responseDTO(atleta);



    }



    public List<AtletaListaDTO> listarAtletas(){

        List<Atleta> lista = atletaRepository.findAll();
        return lista.stream().map(atleta->new AtletaListaDTO(atleta.getId(), atleta.getNome())).toList();
    }

    public AtletaResponseDTO buscarAtleta(Long id){
        Atleta atleta = atletaRepository.findById(id).orElseThrow(()-> new RuntimeException("Atleta com " + id+ " nao encontrado"));
        return responseDTO(atleta);

    }

    public void actualizarDados(Long id , AtletaRequestDTO atletaRequestDTO){
        Atleta atleta = atletaRepository.findById(id).orElseThrow(()-> new RuntimeException("Atleta nao encontrado"));
        atleta.setNome(atletaRequestDTO.nome());
        atleta.setContacto(atletaRequestDTO.contacto());
        atleta.setEmail(atletaRequestDTO.email());
        atleta.setDataNasimento(atletaRequestDTO.dataNascimento());
        atleta.setGenero(atletaRequestDTO.genero());
        atleta.setPais(atletaRequestDTO.pais());
        atleta.setCidade(atletaRequestDTO.cidade());
        atleta.setPeso(atletaRequestDTO.peso());
        atleta.setAltura(atletaRequestDTO.altura());
        atleta.setObjectivo(atletaRequestDTO.objectivo());

        atletaRepository.save(atleta);


    }

    public AtletaResponseDTO responseDTO(Atleta atleta){

        return new AtletaResponseDTO(
                atleta.getId(),
                atleta.getNome(),
                atleta.getEmail(),
                atleta.getContacto(),
                atleta.getPais(),
                atleta.getCidade(),
                atleta.getDataNasimento(),
                atleta.getGenero(),
                atleta.getAltura(),
                atleta.getPeso(),
                atleta.getObjectivo()
        );
    }

}
