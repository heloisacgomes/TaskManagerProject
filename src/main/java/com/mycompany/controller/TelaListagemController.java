package com.mycompany.controller;

import com.mycompany.api.TarefaApiService;
import com.mycompany.taskmanager.dto.TarefaRequestDTO;
import com.mycompany.taskmanager.dto.TarefaResponseDTO;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaListagemController implements Initializable {

    @FXML
    private TableView<TarefaResponseDTO> tabelaTarefas;

    @FXML
    private TableColumn<TarefaResponseDTO, String> colTitulo;

    @FXML
    private TableColumn<TarefaResponseDTO, String> colDescricao;

    private final TarefaApiService tarefaApiService =
            new TarefaApiService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colTitulo.setCellValueFactory(
                new PropertyValueFactory<>("titulo")
        );

        colDescricao.setCellValueFactory(
                new PropertyValueFactory<>("descricao")
        );

        colTitulo.setCellFactory(
                col -> new TableCell<TarefaResponseDTO, String>() {

                    @Override
                    protected void updateItem(
                            String item,
                            boolean empty) {

                        super.updateItem(item, empty);

                        if (empty || item == null) {

                            setGraphic(null);

                        } else {

                            TarefaResponseDTO tarefa =
                                    getTableView()
                                            .getItems()
                                            .get(getIndex());

                            Text texto =
                                    new Text(item);

                            if (tarefa.isConcluida()) {

                                texto.setStrikethrough(true);

                                texto.setStyle(
                                        "-fx-fill: #d48bab;"
                                );

                            } else {

                                texto.setStyle(
                                        "-fx-fill: #6b4c5c;"
                                );
                            }

                            setGraphic(texto);
                        }
                    }
                }
        );

        carregarTarefas();
    }

    @FXML
    public void abrirCadastro() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/TelaCadastro.fxml"
                                    )
                    );

            Stage stage =
                    new Stage();

            stage.setScene(
                    new Scene(loader.load())
            );

            TelaCadastroController controller =
                    loader.getController();

            controller.setTelaListagemController(
                    this
            );

            stage.setTitle(
                    "Nova Tarefa"
            );

            stage.show();

        } catch (Exception e) {

            mostrarErro(
                    "Não foi possível abrir a tela de cadastro.",
                    e
            );
        }
    }

    private void carregarTarefas() {

        try {

            tabelaTarefas
                    .getItems()
                    .clear();

            tabelaTarefas
                    .getItems()
                    .addAll(
                            tarefaApiService.listar()
                    );

        } catch (Exception e) {

            mostrarErro(
                    "Não foi possível carregar as tarefas.",
                    e
            );
        }
    }

    public void atualizarLista() {

        carregarTarefas();
    }

    @FXML
    public void removerTarefa() {

        TarefaResponseDTO selecionada =
                tabelaTarefas
                        .getSelectionModel()
                        .getSelectedItem();

        if (selecionada == null) {

            mostrarAviso(
                    "Selecione uma tarefa para excluir."
            );

            return;
        }

        Alert alert =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        alert.setTitle(
                "Confirmar Exclusão"
        );

        alert.setHeaderText(
                "Deseja remover esta tarefa?"
        );

        alert.setContentText(
                selecionada.getTitulo()
        );

        Optional<ButtonType> resultado =
                alert.showAndWait();

        if (resultado.isPresent()
                && resultado.get()
                == ButtonType.OK) {

            try {

                tarefaApiService.excluir(
                        selecionada.getId()
                );

                atualizarLista();

            } catch (Exception e) {

                mostrarErro(
                        "Não foi possível excluir a tarefa.",
                        e
                );
            }
        }
    }

    @FXML
    public void concluirTarefa() {

        TarefaResponseDTO selecionada =
                tabelaTarefas
                        .getSelectionModel()
                        .getSelectedItem();

        if (selecionada == null) {

            mostrarAviso(
                    "Selecione uma tarefa."
            );

            return;
        }

        try {

            TarefaRequestDTO dto =
                    new TarefaRequestDTO();

            dto.setTitulo(
                    selecionada.getTitulo()
            );

            dto.setDescricao(
                    selecionada.getDescricao()
            );

            dto.setPrioridade(
                    selecionada.getPrioridade()
            );

            dto.setConcluida(
                    !selecionada.isConcluida()
            );

            tarefaApiService.atualizar(
                    selecionada.getId(),
                    dto
            );

            atualizarLista();

        } catch (Exception e) {

            mostrarErro(
                    "Não foi possível alterar o status da tarefa.",
                    e
            );
        }
    }

    @FXML
    public void editarTarefa() {

        TarefaResponseDTO selecionada =
                tabelaTarefas
                        .getSelectionModel()
                        .getSelectedItem();

        if (selecionada == null) {

            mostrarAviso(
                    "Selecione uma tarefa para editar."
            );

            return;
        }

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/TelaCadastro.fxml"
                                    )
                    );

            Stage stage =
                    new Stage();

            stage.setScene(
                    new Scene(loader.load())
            );

            TelaCadastroController controller =
                    loader.getController();

            controller.setTelaListagemController(
                    this
            );

            controller.carregarTarefa(
                    selecionada
            );

            stage.setTitle(
                    "Editar Tarefa"
            );

            stage.show();

        } catch (Exception e) {

            mostrarErro(
                    "Não foi possível abrir a tarefa para edição.",
                    e
            );
        }
    }

    private void mostrarAviso(
            String mensagem) {

        Alert alert =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alert.setTitle(
                "Atenção"
        );

        alert.setHeaderText(
                null
        );

        alert.setContentText(
                mensagem
        );

        alert.showAndWait();
    }

    private void mostrarErro(
            String mensagem,
            Exception e) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle(
                "Erro"
        );

        alert.setHeaderText(
                mensagem
        );

        String detalhe =
                e.getMessage();

        if (detalhe == null
                || detalhe.isBlank()) {

            detalhe =
                    "Ocorreu um erro inesperado.";
        }

        alert.setContentText(
                detalhe
        );

        alert.showAndWait();
    }
}