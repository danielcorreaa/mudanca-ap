package com.mudanca.controller;

import com.mudanca.model.Usuario;
import com.mudanca.repository.UsuarioRepository;
import com.mudanca.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticação", description = "Endpoints para registo e login de utilizadores")
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final AuthService authService;

    public AuthController(UsuarioRepository repository, PasswordEncoder encoder, AuthService authService) {
        this.repository = repository;
        this.encoder = encoder;
        this.authService = authService;
    }

    @Operation(summary = "Registar utilizador", description = "Cria uma nova conta de utilizador no sistema")
    @PostMapping("/register")
    public String register(@RequestBody Usuario user) {
        if(repository.findByUsername(user.getUsername()).isPresent()) return "Usuário já existe";
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return "Usuário cadastrado com sucesso";
    }

    @Operation(summary = "Login", description = "Autentica o utilizador e retorna um token JWT")
    @PostMapping("/login")
    public String login(@RequestBody Usuario user) {
        return repository.findByUsername(user.getUsername())
                .filter(u -> encoder.matches(user.getPassword(), u.getPassword()))
                .map(u -> authService.generateToken(u.getUsername()))
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
    }
}