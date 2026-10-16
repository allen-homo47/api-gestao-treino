package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.RotinaTreinoService;
import com.Ginasio.Treinos.entity.DTO.RotinaRequestDTO;
import com.Ginasio.Treinos.entity.DTO.RotinaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rotinas")
public class RotinaTreinoController {

    @Autowired
    private RotinaTreinoService rotinaTreinoService;

    @PostMapping
    public ResponseEntity<RotinaResponseDTO> create(@RequestBody RotinaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rotinaTreinoService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<RotinaResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(rotinaTreinoService.list(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody RotinaRequestDTO dto) {
        rotinaTreinoService.updtate(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rotinaTreinoService.changeState(id);
        return ResponseEntity.noContent().build();
    }
}