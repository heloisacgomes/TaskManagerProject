package com.mycompany.controller;

import com.mycompany.api.TarefaApiService;
import com.mycompany.taskmanager.dto.TarefaRequestDTO;
import com.mycompany.taskmanager.dto.TarefaResponseDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaCadastroController {

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private CheckBox chkConcluida;

    private final TarefaApiService tarefaApiService =
            new TarefaApiService();

    private TarefaResponseDTO tarefaEditando;

    private TelaListagemController telaListagemController;

    public void setTelaListagemController(
            TelaListagemController controller) {

        this.telaListagemController = controller;
    }

    @FXML
    public void salvar() {

        String titulo = txtTitulo.getText();

        if (titulo == null || titulo.trim().isEmpty()) {

            mostrarAviso(
                    "Informe o título da tarefa"
            );

            return;
        }

        try {

            TarefaRequestDTO dto =
                    new TarefaRequestDTO();

            dto.setTitulo(
                    titulo.trim()
            );

            dto.setDescricao(
                    txtDescricao.getText()
            );

            dto.setPrioridade(
                    tarefaEditando != null
                            ? tarefaEditando.getPrioridade()
                            : "MEDIA"
            );

            dto.setConcluida(
                    chkConcluida.isSelected()
            );

            if (tarefaEditando == null) {

                tarefaApiService.criar(dto);

            } else {

                tarefaApiService.atualizar(
                        tarefaEditando.getId(),
                        dto
                );
            }

            if (telaListagemController != null) {

                telaListagemController
                        .atualizarLista();
            }

            fecharJanela();

        } catch (Exception e) {

            mostrarErro(
                    "Não foi possível salvar a tarefa",
                    e
            );
        }
    }

    public void carregarTarefa(
            TarefaResponseDTO tarefa) {

        this.tarefaEditando = tarefa;

        txtTitulo.setText(
                tarefa.getTitulo()
        );

        txtDescricao.setText(
                tarefa.getDescricao()
        );

        chkConcluida.setSelected(
                tarefa.isConcluida()
        );
    }

    private void fecharJanela() {

        Stage stage =
                (Stage) txtTitulo
                        .getScene()
                        .getWindow();

        stage.close();
    }

    private void mostrarAviso(
            String mensagem) {

        Alert alert =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarErro(
            String mensagem,
            Exception e) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle("Erro");
        alert.setHeaderText(mensagem);

        String detalhe =
                e.getMessage();

        if (detalhe == null
                || detalhe.isBlank()) {

            detalhe =
                    "Ocorreu um erro inesperado";
        }

        alert.setContentText(detalhe);
        alert.showAndWait();
    }
}

