package com.mycompany.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados enviados para criar ou atualizar uma tarefa")
public class TarefaRequestDTO {

    @Schema(
            description = "Título da tarefa",
            example = "Atividade 3"
    )
    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @Schema(
            description = "Descrição da tarefa",
            example = "Etapa 3 e final"
    )
    private String descricao;

    @Schema(
            description = "Prioridade da tarefa",
            example = "ALTA"
    )
    private String prioridade;

    @Schema(
            description = "Tarefa foi concluída ou não",
            example = "false"
    )
    private Boolean concluida;

    public TarefaRequestDTO() {
    }

    public TarefaRequestDTO(
            String titulo,
            String descricao,
            String prioridade,
            Boolean concluida) {

        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.concluida = concluida;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }
}
