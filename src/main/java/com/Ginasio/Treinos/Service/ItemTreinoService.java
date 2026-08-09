package com.Ginasio.Treinos.Service;


import com.Ginasio.Treinos.Repository.ItemTreinoRepository;
import com.Ginasio.Treinos.entity.DTO.ItemRequestDTO;
import com.Ginasio.Treinos.entity.DTO.ItemResponseDTO;
import com.Ginasio.Treinos.entity.ItemTreino;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemTreinoService {


    private final ItemTreinoRepository itemTreinoRepository;


    public ItemResponseDTO criarItem(ItemRequestDTO requestDTO){

        ItemTreino item = new ItemTreino();
        item.setSeries(requestDTO.series());
        item.setRepeticoes(requestDTO.repeticoes());
        item.setCarga(requestDTO.carga());
        item.setDescanso(requestDTO.descanso());
         itemTreinoRepository.save(item);

        ItemResponseDTO responseDTO = new ItemResponseDTO(item.getId(), item.getSeries(), item.getRepeticoes(), item.getCarga(), item.getDescanso());
        return  responseDTO;
    }

    public  ItemResponseDTO buscarItem(Long id){
        ItemTreino item= itemTreinoRepository.findById(id).orElseThrow(()-> new RuntimeException("Item nao encontrado"));

        ItemResponseDTO responseDTO = new ItemResponseDTO(item.getId(), item.getSeries(), item.getRepeticoes(), item.getCarga(), item.getDescanso());

        return responseDTO;
    }

    public void actualizar(Long id, ItemRequestDTO requestDTO){

        ItemTreino actual = itemTreinoRepository.findById(id).orElseThrow(()-> new RuntimeException("Item nao encontrado"));
        actual.setSeries(requestDTO.series());
        actual.setRepeticoes(requestDTO.repeticoes());;
        actual.setCarga(requestDTO.carga());
        actual.setDescanso(requestDTO.descanso());
        itemTreinoRepository.save(actual);
    }
}
