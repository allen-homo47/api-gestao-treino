package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.ItemTreinoService;
import com.Ginasio.Treinos.entity.DTO.ItemRequestDTO;
import com.Ginasio.Treinos.entity.DTO.ItemResponseDTO;
import com.Ginasio.Treinos.entity.ItemTreino;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itemTreino")
@RequiredArgsConstructor
public class ItemTreinoController {

    private final ItemTreinoService itemTreinoService;


    @PostMapping("/criarItem")
    public ResponseEntity<ItemResponseDTO> criarItem(@RequestBody ItemRequestDTO requestDTO){


       ItemResponseDTO responseDTO= itemTreinoService.criarItem(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ItemResponseDTO> buscarItem(@PathVariable Long id){

        ItemResponseDTO responseDTO = itemTreinoService.buscarItem(id);
        return  ResponseEntity.ok(responseDTO);
    }


    @PutMapping("actulizar/{i}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody ItemRequestDTO requestDTO ){

        itemTreinoService.actualizar(id, requestDTO);

        return  ResponseEntity.ok().build();

    }
}
