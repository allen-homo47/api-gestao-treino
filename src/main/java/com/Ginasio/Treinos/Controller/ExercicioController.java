package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.ExercicioService;
import com.Ginasio.Treinos.entity.DTO.ExercicioRequestDTO;
import com.Ginasio.Treinos.entity.DTO.ExercicioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioService exercicioService;

    @PostMapping
    public ResponseEntity<ExercicioResponseDTO> create(@RequestBody ExercicioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exercicioService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ExercicioResponseDTO>> list(@PageableDefault(size = 50) Pageable pageable) {
        return ResponseEntity.ok(exercicioService.list(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ExercicioRequestDTO dto) {
        exercicioService.updtate(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exercicioService.changeState(id);
        return ResponseEntity.noContent().build();
    }
}