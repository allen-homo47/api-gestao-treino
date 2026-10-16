package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Interface.BaseInterface;
import com.Ginasio.Treinos.Repository.AtletaRepository;
import com.Ginasio.Treinos.Repository.PlanotreinoRepository;
import com.Ginasio.Treinos.entity.Atleta;
import com.Ginasio.Treinos.entity.DTO.PlanoTreinoRequestDTO;
import com.Ginasio.Treinos.entity.DTO.PlanoTreinoresponseDTO;
import com.Ginasio.Treinos.entity.PlanoTreino;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanoTreinoService implements BaseInterface<PlanoTreinoRequestDTO, PlanoTreinoresponseDTO> {

    @Autowired
    private PlanotreinoRepository planotreinoRepository;

    @Autowired
    private AtletaRepository atletaRepository;

    @Override
    @Transactional
    public PlanoTreinoresponseDTO create(PlanoTreinoRequestDTO dto) {
        PlanoTreino plano = new PlanoTreino();
        preencher(plano, dto);

        PlanoTreino salvo = planotreinoRepository.save(plano);
        return toResponse(salvo);
    }

    @Override
    @Transactional
    public void updtate(Long id, PlanoTreinoRequestDTO dto) {
        PlanoTreino plano = planotreinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + id));

        preencher(plano, dto);
        planotreinoRepository.save(plano);
    }

    @Override
    @Transactional
    public void changeState(Long id) {
        planotreinoRepository.deleteById(id);
    }

    @Override
    public Page<PlanoTreinoresponseDTO> list(Pageable pg) {
        return planotreinoRepository.findAll(pg).map(this::toResponse);
    }

    private void preencher(PlanoTreino plano, PlanoTreinoRequestDTO dto) {
        plano.setNomePlano(dto.nome());
        plano.setDataInicio(dto.dataInicio());
        plano.setDataFim(dto.dataFim());

        Atleta atleta = null;
        if (dto.atletaId() != null) {
            atleta = atletaRepository.findById(dto.atletaId())
                    .orElseThrow(() -> new RuntimeException("Atleta não encontrado com id: " + dto.atletaId()));
        }
        plano.setAtleta(atleta);
    }

    private PlanoTreinoresponseDTO toResponse(PlanoTreino p) {
        Atleta a = p.getAtleta();
        return new PlanoTreinoresponseDTO(p.getId(), p.getNomePlano(), p.getDataInicio(), p.getDataFim(),
                a != null ? a.getId() : null, a != null ? a.getNome() : null);
    }
}
