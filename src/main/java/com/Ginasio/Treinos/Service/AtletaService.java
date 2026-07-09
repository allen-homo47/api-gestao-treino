package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.AtletaRepository;
import com.Ginasio.Treinos.entity.Atleta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtletaService {

    private final AtletaRepository atletaRepository;


    public Atleta criarAtleta(Atleta atleta){

        return atletaRepository.save(atleta);
    }


    public List<Atleta> listarAtletas(){

        List<Atleta> lista = atletaRepository.findAll();
        return lista;
    }

    public Atleta buscarAtleta(Long id){
        return atletaRepository.findById(id).orElseThrow(()-> new RuntimeException("Atleta nao encontrado"));

    }

    public void actualizarDados(Long id , Atleta dadosNovos){
        Atleta actual = atletaRepository.findById(id).orElseThrow(()-> new RuntimeException("Atleta nao encontrado"));
        actual.setPeso(dadosNovos.getPeso());
        actual.setAltura(dadosNovos.getAltura());
        actual.setIdade(dadosNovos.getIdade());
        actual.setObjectivo(dadosNovos.getObjectivo());

        atletaRepository.save(actual);


    }

}
