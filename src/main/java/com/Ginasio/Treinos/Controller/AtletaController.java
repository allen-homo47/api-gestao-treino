package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.AtletaService;
import com.Ginasio.Treinos.entity.Atleta;
import com.Ginasio.Treinos.entity.DTO.AtletaRequestDTO;
import com.Ginasio.Treinos.entity.DTO.AtletaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atletas")
@RequiredArgsConstructor
public class AtletaController {

    private final AtletaService atletaService;

    @PostMapping("/ciarAtleta")
    public ResponseEntity<AtletaResponseDTO> criarAlteta(@RequestBody AtletaRequestDTO atletaDTO){

       AtletaResponseDTO saved = atletaService.criarAtleta(atletaDTO);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<AtletaResponseDTO> buscarAtleta(@PathVariable Long id){
        AtletaResponseDTO achado = atletaService.buscarAtleta(id);
        return ResponseEntity.ok(achado);

    }

    @PutMapping("/actualizar")
    public ResponseEntity<Void> ActualizarDados(@PathVariable Long id, @RequestBody AtletaRequestDTO novosDados){


        AtletaResponseDTO achado = atletaService.buscarAtleta(id);
        atletaService.actualizarDados(id, novosDados);

        return ResponseEntity.ok().build();
    }



}
