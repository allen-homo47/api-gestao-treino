package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.ItemTreinoService;
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
    public ResponseEntity<ItemTreino> criarItem(@RequestBody ItemTreino itemTreino){

        ItemTreino novo = itemTreinoService.criarItem(itemTreino);

        return ResponseEntity.ok(novo);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ItemTreino> buscarItem(@PathVariable Long id){

        ItemTreino achado = itemTreinoService.buscarItem(id);
        return  ResponseEntity.ok(achado);
    }


    @PutMapping("actulizar/{i}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody ItemTreino novosItens ){

        itemTreinoService.actualizar(id, novosItens);

        return  ResponseEntity.ok().build();

    }
}
