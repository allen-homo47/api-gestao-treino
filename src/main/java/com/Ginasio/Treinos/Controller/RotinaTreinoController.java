package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.RotinaTreinoService;
import com.Ginasio.Treinos.entity.DTO.RotinaRequestDTO;
import com.Ginasio.Treinos.entity.DTO.RotinaResponseDTO;
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
    public ResponseEntity<RotinaResponseDTO> criarRotina(@RequestBody RotinaRequestDTO requestDTO){

        RotinaResponseDTO responseDTO = rotinaTreinoService.criarRotina(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/buscarRotina/{id}")
    public ResponseEntity<RotinaResponseDTO> buscarRotina(@PathVariable Long id){

        RotinaResponseDTO rotinaResponseDTO = rotinaTreinoService.buscarRotina(id);
        return ResponseEntity.ok(rotinaResponseDTO);
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> actualizarRotina(@PathVariable Long id, @RequestBody RotinaRequestDTO requestDTO){

        rotinaTreinoService.actualizarRotina(id, requestDTO);
        return ResponseEntity.ok().build();
    }
}
