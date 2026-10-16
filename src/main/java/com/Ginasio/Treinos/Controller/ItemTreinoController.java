package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.ItemTreinoService;
import com.Ginasio.Treinos.entity.DTO.ItemRequestDTO;
import com.Ginasio.Treinos.entity.DTO.ItemResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens-treino")
public class ItemTreinoController {

    @Autowired
    private ItemTreinoService itemTreinoService;

    @PostMapping
    public ResponseEntity<ItemResponseDTO> create(@RequestBody ItemRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemTreinoService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponseDTO>> list(@PageableDefault(size = 50) Pageable pageable) {
        return ResponseEntity.ok(itemTreinoService.list(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ItemRequestDTO dto) {
        itemTreinoService.updtate(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemTreinoService.changeState(id);
        return ResponseEntity.noContent().build();
    }
}