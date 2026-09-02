package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dto.LoginRequestDTO;
import com.mycompany.taskmanager.dto.LoginResponseDTO;
import com.mycompany.taskmanager.dto.UsuarioRegistroDTO;
import com.mycompany.taskmanager.dto.UsuarioResponseDTO;
import com.mycompany.taskmanager.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Cadastro e autenticação de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Cadastrar usuário",
            description = "Cadastra um novo usuário no sistema."
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Usuário cadastrado com sucesso"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Dados de cadastro inválidos"
        ),
        @ApiResponse(
                responseCode = "409",
                description = "E-mail já cadastrado"
        )
    })
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registrar(
            @Valid @RequestBody UsuarioRegistroDTO dto) {

        UsuarioResponseDTO usuario =
                usuarioService.registrar(dto);

        URI location = URI.create(
                "/api/v1/usuarios/" + usuario.getId()
        );

        return ResponseEntity
                .created(location)
                .body(usuario);
    }

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário e retorna um token JWT."
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Login realizado com sucesso"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Dados de login inválidos"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "E-mail ou senha inválidos"
        )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        LoginResponseDTO resposta =
                usuarioService.login(dto);

        return ResponseEntity.ok(resposta);
    }
}