package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Interface.BaseInterface;
import com.Ginasio.Treinos.Repository.AtletaRepository;
import com.Ginasio.Treinos.entity.Atleta;
import com.Ginasio.Treinos.entity.DTO.AtletaRequestDTO;
import com.Ginasio.Treinos.entity.DTO.AtletaResponseDTO;
import com.Ginasio.Treinos.entity.Enum.TipoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtletaService implements BaseInterface<AtletaRequestDTO, AtletaResponseDTO> {

    @Autowired
    private AtletaRepository atletaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AtletaResponseDTO create(AtletaRequestDTO dto) {
        Atleta atleta = new Atleta();
        atleta.setNome(dto.nome());
        atleta.setEmail(dto.email());
        atleta.setUsername(dto.email());
        atleta.setPassword(passwordEncoder.encode("123456"));
        atleta.setTipoUser(TipoUser.ATLETA);
        atleta.setContacto(dto.contacto());
        atleta.setPais(dto.pais());
        atleta.setCidade(dto.cidade());
        atleta.setDataNascimento(dto.dataNascimento());
        atleta.setAltura(dto.altura());
        atleta.setPeso(dto.peso());
        atleta.setGenero(dto.genero());
        atleta.setObjectivo(dto.objectivo());

        Atleta salvo = atletaRepository.save(atleta);
        return toResponse(salvo);
    }

    @Override
    @Transactional
    public void updtate(Long id, AtletaRequestDTO dto) {
        Atleta atleta = atletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atleta não encontrado com id: " + id));

        atleta.setNome(dto.nome());
        atleta.setEmail(dto.email());
        atleta.setContacto(dto.contacto());
        atleta.setPais(dto.pais());
        atleta.setCidade(dto.cidade());
        atleta.setDataNascimento(dto.dataNascimento());
        atleta.setAltura(dto.altura());
        atleta.setPeso(dto.peso());
        atleta.setGenero(dto.genero());
        atleta.setObjectivo(dto.objectivo());

        atletaRepository.save(atleta);
    }

    @Override
    @Transactional
    public void changeState(Long id) {
        atletaRepository.deleteById(id);
    }

    @Override
    public Page<AtletaResponseDTO> list(Pageable pg) {
        return atletaRepository.findAll(pg).map(this::toResponse);
    }

    public AtletaResponseDTO findById(Long id) {
        return atletaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Atleta não encontrado com id: " + id));
    }

    private AtletaResponseDTO toResponse(Atleta a) {
        return new AtletaResponseDTO(
                a.getId(),
                a.getNome(),
                a.getEmail(),
                a.getContacto(),
                a.getPais(),
                a.getCidade(),
                a.getDataNascimento(),
                a.getGenero(),
                a.getAltura(),
                a.getPeso(),
                a.getObjectivo()
        );
    }
}