package com.mycompany.controller;

import com.mycompany.dao.TaskDAO;
import com.mycompany.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

public class TelaListagemController implements Initializable {

    @FXML
    private TableView<Task> tabelaTarefas;

    @FXML
    private TableColumn<Task, String> colTitulo;

    @FXML
    private TableColumn<Task, String> colDescricao;

    private final TaskDAO dao = new TaskDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colTitulo.setCellValueFactory(
                new PropertyValueFactory<>("titulo"));

        colDescricao.setCellValueFactory(
                new PropertyValueFactory<>("descricao"));

        colTitulo.setCellValueFactory(
        new PropertyValueFactory<>("titulo"));
        
        colTitulo.setCellFactory(col -> new TableCell<Task, String>() {

    @Override
    protected void updateItem(String item, boolean empty) {

        super.updateItem(item, empty);

        if (empty || item == null) {

            setGraphic(null);

        } else {

            Task task =
                    getTableView().getItems().get(getIndex());

            Text texto = new Text(item);

            if (task.isConcluida()) {

                texto.setStrikethrough(true);
                texto.setStyle("-fx-fill: #d48bab;");

            } else {

                texto.setStyle("-fx-fill: #6b4c5c;");
            }

            setGraphic(texto);
        }
    }
});
        carregarTarefas();
    }

    @FXML
    public void abrirCadastro() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/TelaCadastro.fxml"));

            Stage stage = new Stage();

            stage.setScene(new Scene(loader.load()));

            TelaCadastroController controller =
                    loader.getController();

            controller.setTelaListagemController(this);

            stage.setTitle("Nova Tarefa");

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarTarefas() {

        tabelaTarefas.getItems().clear();

        tabelaTarefas.getItems().addAll(
                dao.listar()
        );
    }

    public void atualizarLista() {
        carregarTarefas();
    }

    @FXML
    public void removerTarefa() {

        Task selecionada =
                tabelaTarefas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Deseja remover esta tarefa?");
        alert.setContentText(selecionada.getTitulo());

        Optional<ButtonType> resultado =
                alert.showAndWait();

        if (resultado.isPresent()
                && resultado.get() == ButtonType.OK) {

            dao.excluir(selecionada.getId());

            atualizarLista();
        }
    }

    @FXML
    public void concluirTarefa() {

        Task selecionada =
                tabelaTarefas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            return;
        }

        dao.concluir(selecionada.getId());

        atualizarLista();
    }

    @FXML
    public void editarTarefa() {

        Task selecionada =
                tabelaTarefas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            return;
        }

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/TelaCadastro.fxml"));

            Stage stage = new Stage();

            stage.setScene(new Scene(loader.load()));

            TelaCadastroController controller =
                    loader.getController();

            controller.setTelaListagemController(this);

            controller.carregarTarefa(selecionada);

            stage.setTitle("Editar Tarefa");

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}