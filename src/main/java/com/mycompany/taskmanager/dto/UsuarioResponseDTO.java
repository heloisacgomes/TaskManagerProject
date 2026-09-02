package com.mycompany.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados retornados de um usuário")
public class UsuarioResponseDTO {

    @Schema(
            description = "ID identificador do usuário",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nome do usuário",
            example = "Olga Heloisa"
    )
    private String nome;

    @Schema(
            description = "E-mail do usuário",
            example = "olga@email.com"
    )
    private String email;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(
            Long id,
            String nome,
            String email) {

        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}