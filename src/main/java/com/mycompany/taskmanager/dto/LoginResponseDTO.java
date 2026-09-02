package com.mycompany.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta após autenticação")
public class LoginResponseDTO {

    @Schema(
            description = "Token JWT usado para acessar os endpoints protegidos"
    )
    private String token;

    @Schema(
            description = "Tipo do token",
            example = "Bearer"
    )
    private String tipo;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token) {
        this.token = token;
        this.tipo = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
