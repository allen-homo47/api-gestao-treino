package com.Ginasio.Treinos.Service;


import com.Ginasio.Treinos.Repository.RotinaTreinoRepository;
import com.Ginasio.Treinos.entity.RotinaTreino;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RotinaTreinoService {

    private final RotinaTreinoRepository rotinaTreinoRepository;

    public RotinaTreino criarRotina(RotinaTreino rotinaTreino){

       return  rotinaTreinoRepository.save(rotinaTreino);
    }

    public RotinaTreino buscarRotina(Long id){

        RotinaTreino rotina = rotinaTreinoRepository.findById(id).orElseThrow(()-> new RuntimeException("Rotina nao encontrada"));
        return rotina;
    }

    public RotinaTreino actualizarRotina(Long id, RotinaTreino rotinaNova){

        RotinaTreino actual = rotinaTreinoRepository.findById(id).orElseThrow(()-> new RuntimeException("Rotina nao encontrada"));
        actual.setNome(rotinaNova.getNome());
        actual.setDiaSemana(rotinaNova.getDiaSemana());
        return rotinaTreinoRepository.save(actual);
    }
}
