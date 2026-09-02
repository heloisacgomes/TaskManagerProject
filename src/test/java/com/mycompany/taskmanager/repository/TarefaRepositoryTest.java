package com.mycompany.taskmanager.repository;

import com.mycompany.taskmanager.entity.Tarefa;
import com.mycompany.taskmanager.entity.Usuario;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveSalvarTarefa() {

        Usuario usuario = criarUsuario(
                "Usuário Teste"
        );

        Tarefa tarefa = criarTarefa(
                "Atividade Java",
                "Etapa 3",
                "ALTA",
                usuario
        );

        Tarefa tarefaSalva =
                tarefaRepository.save(tarefa);

        assertNotNull(
                tarefaSalva.getId()
        );

        assertEquals(
                "Atividade Java",
                tarefaSalva.getTitulo()
        );

        assertEquals(
                usuario.getId(),
                tarefaSalva.getUsuario().getId()
        );
    }

    @Test
    void deveListarTarefasDoUsuario() {

        Usuario usuario1 = criarUsuario(
                "Usuário Um"
        );

        Usuario usuario2 = criarUsuario(
                "Usuário Dois"
        );

        tarefaRepository.save(
                criarTarefa(
                        "Tarefa 1",
                        "Primeira tarefa",
                        "ALTA",
                        usuario1
                )
        );

        tarefaRepository.save(
                criarTarefa(
                        "Tarefa 2",
                        "Segunda tarefa",
                        "BAIXA",
                        usuario2
                )
        );

        List<Tarefa> tarefas =
                tarefaRepository
                        .findByUsuarioId(
                                usuario1.getId()
                        );

        assertEquals(
                1,
                tarefas.size()
        );

        assertEquals(
                "Tarefa 1",
                tarefas.get(0).getTitulo()
        );
    }

    @Test
    void deveAtualizarTarefa() {

        Usuario usuario = criarUsuario(
                "Atualização Usuário"
        );

        Tarefa tarefa =
                tarefaRepository.save(
                        criarTarefa(
                                "Título antigo",
                                "Descrição antiga",
                                "BAIXA",
                                usuario
                        )
                );

        tarefa.setTitulo(
                "Título atualizado"
        );

        tarefa.setPrioridade(
                "ALTA"
        );

        Tarefa atualizada =
                tarefaRepository.save(tarefa);

        assertEquals(
                "Título atualizado",
                atualizada.getTitulo()
        );

        assertEquals(
                "ALTA",
                atualizada.getPrioridade()
        );
    }

    @Test
    void deveExcluirTarefa() {

        Usuario usuario = criarUsuario(
                "Exclusão Usuário"
        );

        Tarefa tarefa =
                tarefaRepository.save(
                        criarTarefa(
                                "Excluir tarefa",
                                "A tarefa será excluída",
                                "MEDIA",
                                usuario
                        )
                );

        Long id =
                tarefa.getId();

        tarefaRepository.delete(
                tarefa
        );

        tarefaRepository.flush();

        assertFalse(
                tarefaRepository.existsById(id)
        );
    }

    private Usuario criarUsuario(
            String nome) {

        Usuario usuario =
                new Usuario();

        usuario.setNome(
                nome
        );

        usuario.setEmail(
                gerarEmailUnico()
        );

        usuario.setSenha(
                "senha-teste"
        );

        return usuarioRepository.save(
                usuario
        );
    }

    private String gerarEmailUnico() {

        return "teste-"
                + UUID.randomUUID()
                + "@taskmanager.com";
    }

    private Tarefa criarTarefa(
            String titulo,
            String descricao,
            String prioridade,
            Usuario usuario) {

        Tarefa tarefa =
                new Tarefa();

        tarefa.setTitulo(
                titulo
        );

        tarefa.setDescricao(
                descricao
        );

        tarefa.setPrioridade(
                prioridade
        );

        tarefa.setConcluida(
                false
        );

        tarefa.setUsuario(
                usuario
        );

        return tarefa;
    }
}
