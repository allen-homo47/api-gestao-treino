package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.ExercicioRepository;
import com.Ginasio.Treinos.entity.DTO.ExercicioRequestDTO;
import com.Ginasio.Treinos.entity.DTO.ExercicioResponseDTO;
import com.Ginasio.Treinos.entity.Exercicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExercicioService {
    private final ExercicioRepository exercicioRepository;

    public ExercicioResponseDTO criarExercicio(ExercicioRequestDTO requestDTO){

        Exercicio exercicio = new Exercicio();
        exercicio.setNomeExercicio(requestDTO.nomeExercicio());
        exercicio.setGrupoMuscular(requestDTO.grupoMuscular());
        exercicio.setUrlVideoDemonstrativo(requestDTO.urlVideoDemonstrativo());
        exercicioRepository.save(exercicio);


        ExercicioResponseDTO responseDTO = new ExercicioResponseDTO(exercicio.getId(), exercicio.getNomeExercicio(), exercicio.getGrupoMuscular(), exercicio.getUrlVideoDemonstrativo());
        return responseDTO;
    }

    public ExercicioResponseDTO buscarExercicio(Long id){

        Exercicio exercicio =   exercicioRepository.findById(id).orElseThrow(()-> new RuntimeException("Exercicio nao encontrado"));



        ExercicioResponseDTO responseDTO = new ExercicioResponseDTO(exercicio.getId(), exercicio.getNomeExercicio(), exercicio.getGrupoMuscular(), exercicio.getUrlVideoDemonstrativo());
        return responseDTO;
    }

    public void actualizar(Long id, ExercicioRequestDTO requestDTO){

        Exercicio actual = exercicioRepository.findById(id).orElseThrow(()-> new RuntimeException("Exercicio nao encontrado"));
        actual.setNomeExercicio(requestDTO.nomeExercicio());
        actual.setGrupoMuscular(requestDTO.grupoMuscular());
        actual.setUrlVideoDemonstrativo(requestDTO.urlVideoDemonstrativo());
        exercicioRepository.save(actual);
    }

   public void apagar(Long id){


       Exercicio actual = exercicioRepository.findById(id).orElseThrow(()-> new RuntimeException("Exercicio nao encontrado"));
       exercicioRepository.delete(actual);
   }
}
