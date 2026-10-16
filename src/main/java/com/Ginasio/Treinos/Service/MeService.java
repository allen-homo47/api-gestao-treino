package com.Ginasio.Treinos.Service;

import com.Ginasio.Treinos.Repository.PlanotreinoRepository;
import com.Ginasio.Treinos.Repository.UsuarioRepository;
import com.Ginasio.Treinos.entity.DTO.MeResponseDTO;
import com.Ginasio.Treinos.entity.DTO.MudarPasswordDTO;
import com.Ginasio.Treinos.entity.DTO.PlanoDetalheDTO;
import com.Ginasio.Treinos.entity.ItemTreino;
import com.Ginasio.Treinos.entity.PlanoTreino;
import com.Ginasio.Treinos.entity.RotinaTreino;
import com.Ginasio.Treinos.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Operações sobre o utilizador autenticado (usado pela área do atleta)
@Service
public class MeService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanotreinoRepository planotreinoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MeResponseDTO perfil(Usuario usuario) {
        return new MeResponseDTO(usuario.getId(), usuario.getNome(), usuario.getUsername(), usuario.getTipoUser());
    }

    @Transactional(readOnly = true)
    public List<PlanoDetalheDTO> planos(Usuario usuario) {
        return planotreinoRepository.findByAtletaIdOrderByDataInicioDesc(usuario.getId())
                .stream().map(this::toDetalhe).toList();
    }

    @Transactional
    public void mudarPassword(Usuario usuario, MudarPasswordDTO dto) {
        Usuario actual = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        if (!passwordEncoder.matches(dto.passwordActual(), actual.getPassword())) {
            throw new RuntimeException("A password actual está incorrecta");
        }

        actual.setPassword(passwordEncoder.encode(dto.novaPassword()));
        usuarioRepository.save(actual);
    }

    private PlanoDetalheDTO toDetalhe(PlanoTreino p) {
        List<RotinaTreino> rotinas = p.getRotinaTreino() != null ? p.getRotinaTreino() : List.of();
        return new PlanoDetalheDTO(p.getId(), p.getNomePlano(), p.getDataInicio(), p.getDataFim(),
                rotinas.stream().map(this::toDetalhe).toList());
    }

    private PlanoDetalheDTO.Rotina toDetalhe(RotinaTreino r) {
        List<ItemTreino> itens = r.getItemTreino() != null ? r.getItemTreino() : List.of();
        return new PlanoDetalheDTO.Rotina(r.getId(), r.getNome(), r.getDiaSemana(),
                itens.stream().map(this::toDetalhe).toList());
    }

    private PlanoDetalheDTO.Item toDetalhe(ItemTreino i) {
        var e = i.getExercicio();
        return new PlanoDetalheDTO.Item(i.getId(),
                e != null ? e.getNomeExercicio() : null,
                e != null ? e.getGrupoMuscular() : null,
                e != null ? e.getUrlVideoDemonstrativo() : null,
                i.getSeries(), i.getRepeticoes(), i.getCarga(), i.getDescanso());
    }
}
