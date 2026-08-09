package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.PlanoTreinoService;
import com.Ginasio.Treinos.entity.DTO.PlanoTreinoRequestDTO;
import com.Ginasio.Treinos.entity.DTO.PlanoTreinoresponseDTO;
import com.Ginasio.Treinos.entity.PlanoTreino;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/planoTreino")
public class PlanoTreinoController {

    private final PlanoTreinoService planoTreinoService;

    @PostMapping("/criarPlano")
    public ResponseEntity<PlanoTreinoresponseDTO> criarPlano(@RequestBody PlanoTreinoRequestDTO planoRequest){

        PlanoTreinoresponseDTO planoResponse =  planoTreinoService.criarPlano(planoRequest);


        return ResponseEntity.ok(planoResponse);
    }

    @GetMapping("/buscarPlano/{id}")
    public ResponseEntity<PlanoTreinoresponseDTO> buscarPlano(@PathVariable Long id){

        PlanoTreinoresponseDTO responseDTO = planoTreinoService.buscarPlano(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody PlanoTreinoRequestDTO planoRequestDTO){

        PlanoTreinoresponseDTO responseDTO = planoTreinoService.buscarPlano(id);

      planoTreinoService.actualizarPlano(id, planoRequestDTO);
      return ResponseEntity.ok().build();
    }
}
