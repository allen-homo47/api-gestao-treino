package com.Ginasio.Treinos.Service;


import com.Ginasio.Treinos.Repository.RotinaTreinoRepository;
import com.Ginasio.Treinos.entity.DTO.RotinaRequestDTO;
import com.Ginasio.Treinos.entity.DTO.RotinaResponseDTO;
import com.Ginasio.Treinos.entity.RotinaTreino;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RotinaTreinoService {

    private final RotinaTreinoRepository rotinaTreinoRepository;

    public RotinaResponseDTO criarRotina(RotinaRequestDTO rotinaRequest){

        RotinaTreino rotina = new RotinaTreino();
        rotina.setNome(rotinaRequest.nome());
        rotina.setDiaSemana(rotinaRequest.diaSemana());

        rotinaTreinoRepository.save(rotina);

        RotinaResponseDTO rotinaResponse = new RotinaResponseDTO(rotina.getId(), rotina.getNome(), rotina.getDiaSemana());
        return rotinaResponse;

    }

    public RotinaResponseDTO buscarRotina(Long id){

        RotinaTreino rotina = rotinaTreinoRepository.findById(id).orElseThrow(()-> new RuntimeException("Rotina nao encontrada"));
        RotinaResponseDTO responseDTO = new RotinaResponseDTO(rotina.getId(), rotina.getNome(), rotina.getDiaSemana());
        return  responseDTO;
    }

    public void actualizarRotina(Long id, RotinaRequestDTO requestDTO){

        RotinaTreino actual = rotinaTreinoRepository.findById(id).orElseThrow(()-> new RuntimeException("Rotina nao encontrada"));
        actual.setNome(requestDTO.nome());;
        actual.setDiaSemana(requestDTO.diaSemana());

        rotinaTreinoRepository.save(actual);


    }
}
