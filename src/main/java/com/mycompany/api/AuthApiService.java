package com.mycompany.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.taskmanager.dto.LoginRequestDTO;
import com.mycompany.taskmanager.dto.LoginResponseDTO;
import com.mycompany.taskmanager.dto.UsuarioRegistroDTO;
import com.mycompany.taskmanager.dto.UsuarioResponseDTO;

import java.io.IOException;
import java.net.http.HttpResponse;

public class AuthApiService {

    private final ObjectMapper objectMapper;

    public AuthApiService() {
        this.objectMapper = new ObjectMapper();
    }

    public LoginResponseDTO login(String email, String senha)
            throws IOException, InterruptedException {

        LoginRequestDTO dto = new LoginRequestDTO();

        dto.setEmail(email);
        dto.setSenha(senha);

        String json =
                objectMapper.writeValueAsString(dto);

        HttpResponse<String> resposta =
                ApiClient.post(
                        "/api/auth/login",
                        json
                );

        verificarRespostaLogin(resposta);

        LoginResponseDTO loginResponse =
                objectMapper.readValue(
                        resposta.body(),
                        LoginResponseDTO.class
                );

        ApiClient.setToken(
                loginResponse.getToken()
        );

        return loginResponse;
    }

    public UsuarioResponseDTO registrar(
            String nome,
            String email,
            String senha)
            throws IOException, InterruptedException {

        UsuarioRegistroDTO dto =
                new UsuarioRegistroDTO();

        dto.setNome(nome);
        dto.setEmail(email);
        dto.setSenha(senha);

        String json =
                objectMapper.writeValueAsString(dto);

        HttpResponse<String> resposta =
                ApiClient.post(
                        "/api/auth/register",
                        json
                );

        verificarRespostaCadastro(resposta);

        return objectMapper.readValue(
                resposta.body(),
                UsuarioResponseDTO.class
        );
    }

    public void logout() {
        ApiClient.limparToken();
    }

    public boolean estaAutenticado() {

        return ApiClient.getToken() != null
                && !ApiClient.getToken().isBlank();
    }

    private void verificarRespostaLogin(
            HttpResponse<String> resposta)
            throws IOException {

        int status = resposta.statusCode();

        if (status == 200) {
            return;
        }

        if (status == 400) {
            throw new IOException(
                    "Preencha corretamente o e-mail e a senha"
            );
        }

        if (status == 401) {
            throw new IOException(
                    "E-mail ou senha inválidos"
            );
        }

        throw new IOException(
                "Não foi possível realizar o login. Código HTTP: "
                        + status
        );
    }

    private void verificarRespostaCadastro(
            HttpResponse<String> resposta)
            throws IOException {

        int status = resposta.statusCode();

        if (status == 201) {
            return;
        }

        if (status == 400) {
            throw new IOException(
                    "Verifique os dados informados"
            );
        }

        if (status == 409) {
            throw new IOException(
                    "E-mail já cadastrado"
            );
        }

        throw new IOException(
                "Não foi possível realizar o cadastro. Código HTTP: "
                        + status
        );
    }
}
