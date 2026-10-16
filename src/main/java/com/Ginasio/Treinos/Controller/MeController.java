package com.Ginasio.Treinos.Controller;

import com.Ginasio.Treinos.Service.AtletaService;
import com.Ginasio.Treinos.Service.MeService;
import com.Ginasio.Treinos.entity.Atleta;
import com.Ginasio.Treinos.entity.DTO.AtletaResponseDTO;
import com.Ginasio.Treinos.entity.DTO.MeResponseDTO;
import com.Ginasio.Treinos.entity.DTO.MudarPasswordDTO;
import com.Ginasio.Treinos.entity.DTO.PlanoDetalheDTO;
import com.Ginasio.Treinos.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me")
public class MeController {

    @Autowired
    private MeService meService;

    @Autowired
    private AtletaService atletaService;

    @GetMapping
    public ResponseEntity<MeResponseDTO> perfil(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(meService.perfil(usuario));
    }

    @GetMapping("/atleta")
    public ResponseEntity<AtletaResponseDTO> atleta(@AuthenticationPrincipal Usuario usuario) {
        if (!(usuario instanceof Atleta)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atletaService.findById(usuario.getId()));
    }

    @GetMapping("/planos")
    public ResponseEntity<List<PlanoDetalheDTO>> planos(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(meService.planos(usuario));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> mudarPassword(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid MudarPasswordDTO dto) {
        meService.mudarPassword(usuario, dto);
        return ResponseEntity.noContent().build();
    }
}
