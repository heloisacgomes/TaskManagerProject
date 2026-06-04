package com.mycompany.controller;

import com.mycompany.dao.TaskDAO;
import com.mycompany.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;

public class TelaCadastroController {

    @FXML
    private TextField txtTitulo;

    @FXML
    
    private TextArea txtDescricao;
    private final TaskDAO dao = new TaskDAO();
    private Task tarefaEditando;
    private TelaListagemController telaListagemController;
    
    @FXML
    private CheckBox chkConcluida;

public void setTelaListagemController(
        TelaListagemController controller) {

    this.telaListagemController = controller;
}

   @FXML
public void salvar() {

    Task task;

    if (tarefaEditando == null) {

        task = new Task();

    } else {

        task = tarefaEditando;
    }

    task.setTitulo(txtTitulo.getText());
    task.setDescricao(txtDescricao.getText());
    task.setConcluida(
        chkConcluida.isSelected()
);

    if (tarefaEditando == null) {

        dao.salvar(task);

    } else {

        dao.atualizar(task);
    }

    if (telaListagemController != null) {
        telaListagemController.atualizarLista();
    }

    Stage stage =
            (Stage) txtTitulo.getScene().getWindow();

    stage.close();
}
    
public void carregarTarefa(Task task) {

    this.tarefaEditando = task;

    txtTitulo.setText(task.getTitulo());
    txtDescricao.setText(task.getDescricao());
    chkConcluida.setSelected(
        task.isConcluida()
);
}
}

