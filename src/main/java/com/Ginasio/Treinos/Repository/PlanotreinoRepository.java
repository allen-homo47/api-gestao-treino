package com.Ginasio.Treinos.Repository;

import com.Ginasio.Treinos.entity.PlanoTreino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanotreinoRepository extends JpaRepository<PlanoTreino, Long> {

    List<PlanoTreino> findByAtletaIdOrderByDataInicioDesc(Long atletaId);
}
