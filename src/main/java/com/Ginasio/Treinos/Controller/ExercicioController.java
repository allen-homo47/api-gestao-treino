package com.Ginasio.Treinos.Controller;


import com.Ginasio.Treinos.Service.ExercicioService;
import com.Ginasio.Treinos.entity.DTO.ExercicioRequestDTO;
import com.Ginasio.Treinos.entity.DTO.ExercicioResponseDTO;
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
    public ResponseEntity<ExercicioResponseDTO> criar(@RequestBody ExercicioRequestDTO requestDTO){

        ExercicioResponseDTO responseDTO = exercicioService.criarExercicio(requestDTO);
        return  ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ExercicioResponseDTO> buscarExercicio(@PathVariable Long id){
        ExercicioResponseDTO responseDTO = exercicioService.buscarExercicio(id);
        return ResponseEntity.ok(responseDTO);

    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody ExercicioRequestDTO requestDTO){
        exercicioService.actualizar(id, requestDTO);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        exercicioService.apagar(id);
        return ResponseEntity.noContent().build();

    }
}
