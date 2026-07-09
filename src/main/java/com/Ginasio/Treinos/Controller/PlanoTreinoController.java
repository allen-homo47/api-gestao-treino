package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.PlanoTreinoService;
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
    public ResponseEntity<PlanoTreino> criarPlano(@RequestBody PlanoTreino planoTreino){

        PlanoTreino saved = planoTreinoService.criarPlano(planoTreino);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/buscarPlano/{id}")
    public ResponseEntity<PlanoTreino> buscarPlano(@PathVariable Long id){

        PlanoTreino achado = planoTreinoService.buscarPlano(id);
        return ResponseEntity.ok(achado);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody PlanoTreino novoPlano){

        PlanoTreino actual = planoTreinoService.buscarPlano(id);
        actual.setNomePlano(novoPlano.getNomePlano());
        actual.setDataFim(novoPlano.getDataFim());
        actual.setDataInicio(novoPlano.getDataInicio());
      planoTreinoService.actualizarPlano(id,actual);
      return ResponseEntity.ok().build();
    }
}
