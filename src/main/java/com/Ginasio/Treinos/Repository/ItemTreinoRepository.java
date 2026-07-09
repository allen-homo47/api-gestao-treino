package com.Ginasio.Treinos.Repository;

import com.Ginasio.Treinos.entity.ItemTreino;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTreinoRepository extends JpaRepository<ItemTreino, Long> {
}
