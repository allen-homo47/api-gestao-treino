package com.Ginasio.Treinos.Service;


import com.Ginasio.Treinos.Repository.ItemTreinoRepository;
import com.Ginasio.Treinos.entity.ItemTreino;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemTreinoService {


    private final ItemTreinoRepository itemTreinoRepository;


    public ItemTreino criarItem(ItemTreino item){
        return  itemTreinoRepository.save(item);
    }

    public  ItemTreino buscarItem(Long id){
        ItemTreino achado = itemTreinoRepository.findById(id).orElseThrow(()-> new RuntimeException("Item nao encontrado"));
        return achado;
    }

    public void actualizar(Long id, ItemTreino novoItem){

        ItemTreino actual = itemTreinoRepository.findById(id).orElseThrow(()-> new RuntimeException("Item nao encontrado"));
        actual.setSeries(novoItem.getSeries());
        actual.setRepeticoes(novoItem.getRepeticoes());
        actual.setCarga(novoItem.getCarga());
        actual.setDescanso(novoItem.getDescanso());
        itemTreinoRepository.save(actual);
    }
}
