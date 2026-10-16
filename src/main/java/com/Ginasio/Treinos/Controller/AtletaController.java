package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.AtletaService;
import com.Ginasio.Treinos.entity.DTO.AtletaRequestDTO;
import com.Ginasio.Treinos.entity.DTO.AtletaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

    @PostMapping
    public ResponseEntity<AtletaResponseDTO> create(@RequestBody AtletaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atletaService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<AtletaResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(atletaService.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtletaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(atletaService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AtletaRequestDTO dto) {
        atletaService.updtate(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        atletaService.changeState(id);
        return ResponseEntity.noContent().build();
    }
}