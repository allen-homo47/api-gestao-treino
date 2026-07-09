package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.ExercicioRepository;
import com.Ginasio.Treinos.entity.Exercicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExercicioService {
    private final ExercicioRepository exercicioRepository;

    public Exercicio criarExercicio(Exercicio exercicio){

        return exercicioRepository.save(exercicio);
    }

    public Exercicio buscarExercicio(Long id){

        Exercicio achado = exercicioRepository.findById(id).orElseThrow(()-> new RuntimeException("Exercicio nao encontrado"));
        return achado;
    }

    public void actualizar(Long id, Exercicio novoExercicio){

        Exercicio actual = exercicioRepository.findById(id).orElseThrow(()-> new RuntimeException("Exercicio nao encontrado"));
        actual.setNomeExercicio(novoExercicio.getNomeExercicio());
        actual.setGrupoMuscular(novoExercicio.getGrupoMuscular());
        actual.setUrlVideoDemonstrativo(novoExercicio.getUrlVideoDemonstrativo());
        exercicioRepository.save(actual);
    }

   public void apagar(Long id){


       Exercicio actual = exercicioRepository.findById(id).orElseThrow(()-> new RuntimeException("Exercicio nao encontrado"));
       exercicioRepository.delete(actual);
   }
}
