package com.mycompany.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados utilizados para fazer login")
public class LoginRequestDTO {

    @Schema(
            description = "E-mail do usuário",
            example = "usuario@email.com"
    )
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    private String email;

    @Schema(
            description = "Senha do usuário",
            example = "123456"
    )
    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    public LoginRequestDTO() {
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