package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.PlanotreinoRepository;
import com.Ginasio.Treinos.entity.DTO.PlanoTreinoRequestDTO;
import com.Ginasio.Treinos.entity.DTO.PlanoTreinoresponseDTO;
import com.Ginasio.Treinos.entity.PlanoTreino;
import com.Ginasio.Treinos.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanoTreinoService {

    private final PlanotreinoRepository planotreinoRepository;

    public PlanoTreinoresponseDTO criarPlano(PlanoTreinoRequestDTO requestDTO){
        PlanoTreino plano = new PlanoTreino();
        plano.setNomePlano(requestDTO.nome());
        plano.setDataInicio(requestDTO.dataInicio());
        plano.setDataFim(requestDTO.dataFim());
        planotreinoRepository.save(plano);

        PlanoTreinoresponseDTO responseDTo = new PlanoTreinoresponseDTO(plano.getId(), plano.getNomePlano(), plano.getDataInicio(), plano.getDataFim());

        return responseDTo;
    }

    public PlanoTreinoresponseDTO buscarPlano(Long id){

        PlanoTreino plano = planotreinoRepository.findById(id).orElseThrow(()->new RuntimeException("Plano nao encontrado"));

        PlanoTreinoresponseDTO responseDTO = new PlanoTreinoresponseDTO(plano.getId(), plano.getNomePlano(), plano.getDataInicio(), plano.getDataFim());
        return responseDTO;
    }

    public void actualizarPlano(Long id, PlanoTreinoRequestDTO planoRequest){

        PlanoTreino actual = planotreinoRepository.findById(id).orElseThrow(()->new RuntimeException("plano nao encontrado"));
        actual.setNomePlano(planoRequest.nome());
        actual.setDataInicio(planoRequest.dataInicio());
        actual.setDataFim(planoRequest.dataFim());

        planotreinoRepository.save(actual);




    }
}
