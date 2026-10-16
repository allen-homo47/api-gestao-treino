package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Interface.BaseInterface;
import com.Ginasio.Treinos.Repository.ExercicioRepository;
import com.Ginasio.Treinos.Repository.ItemTreinoRepository;
import com.Ginasio.Treinos.Repository.RotinaTreinoRepository;
import com.Ginasio.Treinos.entity.DTO.ItemRequestDTO;
import com.Ginasio.Treinos.entity.DTO.ItemResponseDTO;
import com.Ginasio.Treinos.entity.Exercicio;
import com.Ginasio.Treinos.entity.ItemTreino;
import com.Ginasio.Treinos.entity.RotinaTreino;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemTreinoService implements BaseInterface<ItemRequestDTO, ItemResponseDTO> {

    @Autowired
    private ItemTreinoRepository itemTreinoRepository;

    @Autowired
    private RotinaTreinoRepository rotinaTreinoRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Override
    @Transactional
    public ItemResponseDTO create(ItemRequestDTO dto) {
        ItemTreino item = new ItemTreino();
        preencher(item, dto);

        ItemTreino salvo = itemTreinoRepository.save(item);
        return toResponse(salvo);
    }

    @Override
    @Transactional
    public void updtate(Long id, ItemRequestDTO dto) {
        ItemTreino item = itemTreinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de treino não encontrado: " + id));

        preencher(item, dto);
        itemTreinoRepository.save(item);
    }

    @Override
    @Transactional
    public void changeState(Long id) {
        itemTreinoRepository.deleteById(id);
    }

    @Override
    public Page<ItemResponseDTO> list(Pageable pg) {
        return itemTreinoRepository.findAll(pg).map(this::toResponse);
    }

    private void preencher(ItemTreino item, ItemRequestDTO dto) {
        item.setSeries(dto.series());
        item.setRepeticoes(dto.repeticoes());
        item.setCarga(dto.carga());
        item.setDescanso(dto.descanso());

        RotinaTreino rotina = null;
        if (dto.rotinaId() != null) {
            rotina = rotinaTreinoRepository.findById(dto.rotinaId())
                    .orElseThrow(() -> new RuntimeException("Rotina não encontrada: " + dto.rotinaId()));
        }
        item.setRotinaTreino(rotina);

        Exercicio exercicio = null;
        if (dto.exercicioId() != null) {
            exercicio = exercicioRepository.findById(dto.exercicioId())
                    .orElseThrow(() -> new RuntimeException("Exercício não encontrado: " + dto.exercicioId()));
        }
        item.setExercicio(exercicio);
    }

    private ItemResponseDTO toResponse(ItemTreino i) {
        RotinaTreino r = i.getRotinaTreino();
        Exercicio e = i.getExercicio();
        return new ItemResponseDTO(i.getId(), i.getSeries(), i.getRepeticoes(), i.getCarga(), i.getDescanso(),
                r != null ? r.getId() : null, r != null ? r.getNome() : null,
                e != null ? e.getId() : null, e != null ? e.getNomeExercicio() : null);
    }
}
