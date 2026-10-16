package com.Ginasio.Treinos.Controller;
import com.Ginasio.Treinos.Service.TokenService;
import com.Ginasio.Treinos.entity.DTO.DadosAutenticacaoDTO;
import com.Ginasio.Treinos.entity.DTO.DadosTokenJWT;
import com.Ginasio.Treinos.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacaoDTO dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.username(), dados.password());
        var authentication = authenticationManager.authenticate(authToken);

        // Gera o token JWT com o usuário autenticado
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}