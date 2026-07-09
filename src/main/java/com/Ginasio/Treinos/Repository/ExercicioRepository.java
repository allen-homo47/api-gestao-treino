package com.Ginasio.Treinos.Repository;

import com.Ginasio.Treinos.entity.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
}
