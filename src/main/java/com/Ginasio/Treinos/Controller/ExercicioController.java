package com.Ginasio.Treinos.Controller;


import com.Ginasio.Treinos.Service.ExercicioService;
import com.Ginasio.Treinos.entity.Exercicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercicio")
@RequiredArgsConstructor
public class ExercicioController {
    private final ExercicioService exercicioService;

    @PostMapping("/criarExercicio")
    public ResponseEntity<Exercicio> criar(@RequestBody Exercicio exercicio){

        Exercicio saved = exercicioService.criarExercicio(exercicio);
        return  ResponseEntity.ok(saved);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Exercicio> buscarExercicio(@PathVariable Long id){
        Exercicio achado = exercicioService.buscarExercicio(id);
        return ResponseEntity.ok(achado);

    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody Exercicio exercicioNovo){
        exercicioService.actualizar(id, exercicioNovo);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        exercicioService.apagar(id);
        return ResponseEntity.noContent().build();

    }
}
