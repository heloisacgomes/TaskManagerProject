package com.mycompany.taskmanager.controller;

import com.mycompany.taskmanager.dto.TarefaRequestDTO;
import com.mycompany.taskmanager.dto.TarefaResponseDTO;
import com.mycompany.taskmanager.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tarefas")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tarefas", description = "Gerenciamento das tarefas do usuário")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Operation(
            summary = "Listar tarefas",
            description = "Lista todas as tarefas pertencentes ao usuário"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Tarefas listadas com sucesso"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuário não autenticado"
        )
    })
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TarefaResponseDTO>> listarTodas(
            Authentication authentication) {

        List<TarefaResponseDTO> tarefas =
                tarefaService.listarTodas(authentication);

        return ResponseEntity.ok(tarefas);
    }

    @Operation(
            summary = "Buscar tarefa por ID",
            description = "Busca uma tarefa pertencente ao usuário"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Tarefa encontrada"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuário não autenticado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Usuário não autorizado"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Tarefa não encontrada"
        )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    
    public ResponseEntity<TarefaResponseDTO> buscarPorId(
            @PathVariable Long id,
            Authentication authentication) {

        TarefaResponseDTO tarefa =
                tarefaService.buscarPorId(
                        id,
                        authentication
                );

        return ResponseEntity.ok(tarefa);
    }

    @Operation(
            summary = "Criar tarefa",
            description = "Cria uma nova tarefa e associa ao usuário"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Tarefa criada com sucesso"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Dados da tarefa inválidos"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuário não autenticado"
        )
    })
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TarefaResponseDTO> criar(
            @Valid @RequestBody TarefaRequestDTO dto,
            Authentication authentication) {

        TarefaResponseDTO tarefaCriada =
                tarefaService.criar(
                        dto,
                        authentication
                );

        URI location = URI.create(
                "/api/v1/tarefas/" + tarefaCriada.getId()
        );

        return ResponseEntity
                .created(location)
                .body(tarefaCriada);
    }

    @Operation(
            summary = "Atualizar tarefa",
            description = "Atualiza uma tarefa pertencente ao usuário"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Tarefa atualizada com sucesso"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Dados da tarefa inválidos"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuário não autenticado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Usuário não autorizado"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Tarefa não encontrada"
        )
    })
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    
    public ResponseEntity<TarefaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TarefaRequestDTO dto,
            Authentication authentication) {

        TarefaResponseDTO tarefaAtualizada =
                tarefaService.atualizar(
                        id,
                        dto,
                        authentication
                );

        return ResponseEntity.ok(tarefaAtualizada);
    }

    @Operation(
            summary = "Excluir tarefa",
            description = "Exclui uma tarefa pertencente ao usuário "
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "204",
                description = "Tarefa excluída com sucesso"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuário não autenticado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Usuário não autorizado"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Tarefa não encontrada"
        )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            Authentication authentication) {

        tarefaService.excluir(
                id,
                authentication
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}