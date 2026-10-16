package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Interface.BaseInterface;
import com.Ginasio.Treinos.Repository.PlanotreinoRepository;
import com.Ginasio.Treinos.Repository.RotinaTreinoRepository;
import com.Ginasio.Treinos.entity.DTO.RotinaRequestDTO;
import com.Ginasio.Treinos.entity.DTO.RotinaResponseDTO;
import com.Ginasio.Treinos.entity.PlanoTreino;
import com.Ginasio.Treinos.entity.RotinaTreino;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RotinaTreinoService implements BaseInterface<RotinaRequestDTO, RotinaResponseDTO> {

    @Autowired
    private RotinaTreinoRepository rotinaTreinoRepository;

    @Autowired
    private PlanotreinoRepository planotreinoRepository;

    @Override
    @Transactional
    public RotinaResponseDTO create(RotinaRequestDTO dto) {
        RotinaTreino rotina = new RotinaTreino();
        preencher(rotina, dto);

        RotinaTreino salva = rotinaTreinoRepository.save(rotina);
        return toResponse(salva);
    }

    @Override
    @Transactional
    public void updtate(Long id, RotinaRequestDTO dto) {
        RotinaTreino rotina = rotinaTreinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rotina não encontrada: " + id));

        preencher(rotina, dto);
        rotinaTreinoRepository.save(rotina);
    }

    @Override
    @Transactional
    public void changeState(Long id) {
        rotinaTreinoRepository.deleteById(id);
    }

    @Override
    public Page<RotinaResponseDTO> list(Pageable pg) {
        return rotinaTreinoRepository.findAll(pg).map(this::toResponse);
    }

    private void preencher(RotinaTreino rotina, RotinaRequestDTO dto) {
        rotina.setNome(dto.nome());
        rotina.setDiaSemana(dto.diaSemana());

        PlanoTreino plano = null;
        if (dto.planoId() != null) {
            plano = planotreinoRepository.findById(dto.planoId())
                    .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + dto.planoId()));
        }
        rotina.setPlanoTreino(plano);
    }

    private RotinaResponseDTO toResponse(RotinaTreino r) {
        PlanoTreino p = r.getPlanoTreino();
        return new RotinaResponseDTO(r.getId(), r.getNome(), r.getDiaSemana(),
                p != null ? p.getId() : null, p != null ? p.getNomePlano() : null);
    }
}
