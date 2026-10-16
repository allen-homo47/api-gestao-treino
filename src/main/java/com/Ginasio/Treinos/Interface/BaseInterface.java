package com.Ginasio.Treinos.Interface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseInterface<RQ,RS>{

   RS create(RQ dto);
   void updtate(Long id, RQ dto);
   void changeState(Long id);
   Page<RS> list(Pageable pg);

}
