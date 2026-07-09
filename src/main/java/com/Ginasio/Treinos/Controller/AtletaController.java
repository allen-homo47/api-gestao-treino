package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.AtletaService;
import com.Ginasio.Treinos.entity.Atleta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atletas")
@RequiredArgsConstructor
public class AtletaController {

    private final AtletaService atletaService;

    @PostMapping("/ciarAtleta")
    public ResponseEntity<Atleta> criarAlteta(@RequestBody Atleta atleta){

        Atleta saved = atletaService.criarAtleta(atleta);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Atleta> buscarAtleta(@PathVariable Long id){
        Atleta achado = atletaService.buscarAtleta(id);
        return ResponseEntity.ok(achado);

    }

    @PutMapping("/actualizar")
    public ResponseEntity<Void> ActualizarDados(@PathVariable Long id, @RequestBody Atleta novosDados){

        Atleta actual = atletaService.buscarAtleta(id);
        actual.setObjectivo(novosDados.getObjectivo());
        actual.setPeso(novosDados.getPeso());
        actual.setIdade(novosDados.getIdade());
        actual.setAltura(novosDados.getAltura());

        atletaService.criarAtleta(actual);

        return ResponseEntity.ok().build();
    }



}
