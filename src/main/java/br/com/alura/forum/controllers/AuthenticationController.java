package br.com.alura.forum.controllers;

import br.com.alura.forum.infra.security.TokenService;
import br.com.alura.forum.infra.security.dto.TokenDTO;
import br.com.alura.forum.models.User;
import br.com.alura.forum.models.dto.AutenticationDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AutenticationDTO data){

        UsernamePasswordAuthenticationToken dataLogin = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = authManager.authenticate(dataLogin);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        }
}

