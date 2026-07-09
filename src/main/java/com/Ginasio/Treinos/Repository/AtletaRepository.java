package com.Ginasio.Treinos.Repository;

import com.Ginasio.Treinos.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {
}
