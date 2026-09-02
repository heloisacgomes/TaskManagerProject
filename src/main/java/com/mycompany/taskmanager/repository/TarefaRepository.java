package com.mycompany.taskmanager.repository;

import com.mycompany.taskmanager.entity.Tarefa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByUsuarioId(Long usuarioId);

    List<Tarefa> findByUsuarioIdAndConcluida(
            Long usuarioId,
            boolean concluida
    );

    @Query("""
           SELECT t
           FROM Tarefa t
           WHERE t.usuario.id = :usuarioId
           ORDER BY t.id DESC
           """)
    List<Tarefa> buscarTarefasDoUsuario(
            @Param("usuarioId") Long usuarioId
    );
}