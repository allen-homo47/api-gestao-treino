package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Interface.BaseInterface;
import com.Ginasio.Treinos.Repository.ExercicioRepository;
import com.Ginasio.Treinos.entity.DTO.ExercicioRequestDTO;
import com.Ginasio.Treinos.entity.DTO.ExercicioResponseDTO;
import com.Ginasio.Treinos.entity.Exercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExercicioService implements BaseInterface<ExercicioRequestDTO, ExercicioResponseDTO> {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Override
    @Transactional
    public ExercicioResponseDTO create(ExercicioRequestDTO dto) {
        Exercicio ex = new Exercicio();
        ex.setNomeExercicio(dto.nomeExercicio());
        ex.setGrupoMuscular(dto.grupoMuscular());
        ex.setUrlVideoDemonstrativo(dto.urlVideoDemonstrativo());

        Exercicio salvo = exercicioRepository.save(ex);
        return new ExercicioResponseDTO(salvo.getId(), salvo.getNomeExercicio(), salvo.getGrupoMuscular(), salvo.getUrlVideoDemonstrativo());
    }

    @Override
    @Transactional
    public void updtate(Long id, ExercicioRequestDTO dto) {
        Exercicio ex = exercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado: " + id));

        ex.setNomeExercicio(dto.nomeExercicio());
        ex.setGrupoMuscular(dto.grupoMuscular());
        ex.setUrlVideoDemonstrativo(dto.urlVideoDemonstrativo());
        exercicioRepository.save(ex);
    }

    @Override
    @Transactional
    public void changeState(Long id) {
        exercicioRepository.deleteById(id);
    }

    @Override
    public Page<ExercicioResponseDTO> list(Pageable pg) {
        return exercicioRepository.findAll(pg)
                .map(e -> new ExercicioResponseDTO(e.getId(), e.getNomeExercicio(), e.getGrupoMuscular(), e.getUrlVideoDemonstrativo()));
    }
}