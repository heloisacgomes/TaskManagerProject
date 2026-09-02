package com.mycompany.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados retornados de uma tarefa")
public class TarefaResponseDTO {

    @Schema(
            description = "Identificador da tarefa",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Título da tarefa",
            example = "Atividade Java"
    )
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
            description = "Tarefa concluída ou não",
            example = "false"
    )
    private boolean concluida;

    public TarefaResponseDTO() {
    }

    public TarefaResponseDTO(
            Long id,
            String titulo,
            String descricao,
            String prioridade,
            boolean concluida) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.concluida = concluida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}