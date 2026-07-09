package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.PlanotreinoRepository;
import com.Ginasio.Treinos.entity.PlanoTreino;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanoTreinoService {

    private final PlanotreinoRepository planotreinoRepository;

    public PlanoTreino criarPlano(PlanoTreino planoTreino){

        return planotreinoRepository.save(planoTreino);
    }

    public PlanoTreino buscarPlano(Long id){

        return planotreinoRepository.findById(id).orElseThrow(()->new RuntimeException("Plano nao encontrado"));
    }

    public void actualizarPlano(Long id, PlanoTreino planoNovo){

        PlanoTreino actual = planotreinoRepository.findById(id).orElseThrow(()->new RuntimeException("plano nao encontrado"));
        actual.setNomePlano(planoNovo.getNomePlano());
        actual.setDataInicio(planoNovo.getDataInicio());
        actual.setDataFim(planoNovo.getDataFim());

        planotreinoRepository.save(actual);
    }
}
