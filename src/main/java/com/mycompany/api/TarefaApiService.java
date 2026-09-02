package com.mycompany.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.taskmanager.dto.TarefaRequestDTO;
import com.mycompany.taskmanager.dto.TarefaResponseDTO;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class TarefaApiService {

    private final ObjectMapper objectMapper;

    public TarefaApiService() {
        this.objectMapper = new ObjectMapper();
    }

    public List<TarefaResponseDTO> listar()
            throws IOException, InterruptedException {

        HttpResponse<String> resposta =
                ApiClient.get("/api/v1/tarefas");

        verificarResposta(resposta);

        return objectMapper.readValue(
                resposta.body(),
                new TypeReference<List<TarefaResponseDTO>>() {
                }
        );
    }

    public TarefaResponseDTO buscarPorId(Long id)
            throws IOException, InterruptedException {

        HttpResponse<String> resposta =
                ApiClient.get("/api/v1/tarefas/" + id);

        verificarResposta(resposta);

        return objectMapper.readValue(
                resposta.body(),
                TarefaResponseDTO.class
        );
    }

    public TarefaResponseDTO criar(TarefaRequestDTO dto)
            throws IOException, InterruptedException {

        String json = objectMapper.writeValueAsString(dto);

        HttpResponse<String> resposta =
                ApiClient.post(
                        "/api/v1/tarefas",
                        json
                );

        verificarResposta(resposta);

        return objectMapper.readValue(
                resposta.body(),
                TarefaResponseDTO.class
        );
    }

    public TarefaResponseDTO atualizar(
            Long id,
            TarefaRequestDTO dto)
            throws IOException, InterruptedException {

        String json = objectMapper.writeValueAsString(dto);

        HttpResponse<String> resposta =
                ApiClient.put(
                        "/api/v1/tarefas/" + id,
                        json
                );

        verificarResposta(resposta);

        return objectMapper.readValue(
                resposta.body(),
                TarefaResponseDTO.class
        );
    }

    public void excluir(Long id)
            throws IOException, InterruptedException {

        HttpResponse<String> resposta =
                ApiClient.delete(
                        "/api/v1/tarefas/" + id
                );

        verificarResposta(resposta);
    }

    private void verificarResposta(
            HttpResponse<String> resposta)
            throws IOException {

        int status = resposta.statusCode();

        if (status >= 200 && status < 300) {
            return;
        }

        if (status == 401) {
            throw new IOException(
                    "Usuário não autenticado. Faça login novamente"
            );
        }

        if (status == 403) {
            throw new IOException(
                    "Usuário sem autorização"
            );
        }

        if (status == 404) {
            throw new IOException(
                    "Tarefa não encontrada"
            );
        }

        throw new IOException(
                "Erro ao acessar a API. Código HTTP: " + status
        );
    }
}
