package com.mycompany.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cadastrar um novo usuário")
public class UsuarioRegistroDTO {

    @Schema(
            description = "Nome do usuário",
            example = "Olga Heloisa"
    )
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Schema(
            description = "E-mail do usuário",
            example = "olga@email.com"
    )
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String email;

    @Schema(
            description = "Senha do usuário",
            example = "123456"
    )
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    public UsuarioRegistroDTO() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}