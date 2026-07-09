package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.RotinaTreinoService;
import com.Ginasio.Treinos.entity.RotinaTreino;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rotinaTreino")
@RequiredArgsConstructor
public class RotinaTreinoController {

    private final RotinaTreinoService rotinaTreinoService;

    @PostMapping("/criarRotina")
    public ResponseEntity<RotinaTreino> criarRotina(@RequestBody RotinaTreino rotina){

        RotinaTreino novaRotina = rotinaTreinoService.criarRotina(rotina);
        return ResponseEntity.ok(novaRotina);
    }

    @GetMapping("/buscarRotina/{id}")
    public ResponseEntity<RotinaTreino> buscarRotina(@PathVariable Long id){

        RotinaTreino achado = rotinaTreinoService.buscarRotina(id);
        return ResponseEntity.ok(achado);
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizarRotina(@PathVariable Long id, @RequestBody RotinaTreino novaRotina){

        RotinaTreino actual = rotinaTreinoService.buscarRotina(id);
        RotinaTreino nova = rotinaTreinoService.actualizarRotina(id, novaRotina);
        return ResponseEntity.ok().build();
    }
}
