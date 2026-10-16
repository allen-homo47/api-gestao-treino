package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.PlanoTreinoService;
import com.Ginasio.Treinos.entity.DTO.PlanoTreinoRequestDTO;
import com.Ginasio.Treinos.entity.DTO.PlanoTreinoresponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planos")
public class PlanoTreinoController {

    @Autowired
    private PlanoTreinoService planoTreinoService;

    @PostMapping
    public ResponseEntity<PlanoTreinoresponseDTO> create(@RequestBody PlanoTreinoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planoTreinoService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<PlanoTreinoresponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(planoTreinoService.list(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PlanoTreinoRequestDTO dto) {
        planoTreinoService.updtate(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planoTreinoService.changeState(id);
        return ResponseEntity.noContent().build();
    }
}