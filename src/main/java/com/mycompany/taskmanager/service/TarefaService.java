package com.mycompany.taskmanager.service;

import com.mycompany.taskmanager.dto.TarefaRequestDTO;
import com.mycompany.taskmanager.dto.TarefaResponseDTO;
import com.mycompany.taskmanager.entity.Tarefa;
import com.mycompany.taskmanager.entity.Usuario;
import com.mycompany.taskmanager.exception.TarefaNaoEncontradaException;
import com.mycompany.taskmanager.exception.UsuarioNaoAutorizadoException;
import com.mycompany.taskmanager.repository.TarefaRepository;
import com.mycompany.taskmanager.repository.UsuarioRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public TarefaService(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository,
            ModelMapper modelMapper) {

        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<TarefaResponseDTO> listarTodas(
            Authentication authentication) {

        Usuario usuario = buscarUsuarioAutenticado(authentication);

        return tarefaRepository
                .findByUsuarioId(usuario.getId())
                .stream()
                .map(tarefa -> modelMapper.map(
                        tarefa,
                        TarefaResponseDTO.class
                ))
                .toList();
    }

    public TarefaResponseDTO buscarPorId(
            Long id,
            Authentication authentication) {

        Tarefa tarefa = buscarTarefa(id);

        verificarProprietario(tarefa, authentication);

        return modelMapper.map(
                tarefa,
                TarefaResponseDTO.class
        );
    }

    public TarefaResponseDTO criar(
            TarefaRequestDTO dto,
            Authentication authentication) {

        Usuario usuario = buscarUsuarioAutenticado(authentication);

        Tarefa tarefa = modelMapper.map(
                dto,
                Tarefa.class
        );

        tarefa.setConcluida(false);
        tarefa.setUsuario(usuario);

        Tarefa tarefaSalva =
                tarefaRepository.save(tarefa);

        return modelMapper.map(
                tarefaSalva,
                TarefaResponseDTO.class
        );
    }

    public TarefaResponseDTO atualizar(
            Long id,
            TarefaRequestDTO dto,
            Authentication authentication) {

        Tarefa tarefa = buscarTarefa(id);

        verificarProprietario(tarefa, authentication);

        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setPrioridade(dto.getPrioridade());
        
        if (dto.getConcluida() != null) {
    tarefa.setConcluida(dto.getConcluida());
        }
        
        Tarefa tarefaAtualizada =
                tarefaRepository.save(tarefa);

        return modelMapper.map(
                tarefaAtualizada,
                TarefaResponseDTO.class
        );
    }

    public void excluir(
            Long id,
            Authentication authentication) {

        Tarefa tarefa = buscarTarefa(id);

        verificarProprietario(tarefa, authentication);

        tarefaRepository.delete(tarefa);
    }

    public boolean isOwner(
            Authentication authentication,
            Long id) {

        if (authentication == null
                || !authentication.isAuthenticated()) {
            return false;
        }

        return tarefaRepository.findById(id)
                .map(tarefa ->
                        tarefa.getUsuario()
                                .getEmail()
                                .equals(authentication.getName())
                )
                .orElse(false);
    }

    private Tarefa buscarTarefa(Long id) {

        return tarefaRepository.findById(id)
                .orElseThrow(() ->
                        new TarefaNaoEncontradaException(id)
                );
    }

    private Usuario buscarUsuarioAutenticado(
            Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new UsuarioNaoAutorizadoException();
        }

        return usuarioRepository
                .findByEmail(authentication.getName())
                .orElseThrow(
                        UsuarioNaoAutorizadoException::new
                );
    }

    private void verificarProprietario(
            Tarefa tarefa,
            Authentication authentication) {

        Usuario usuario =
                buscarUsuarioAutenticado(authentication);

        if (!tarefa.getUsuario()
                .getId()
                .equals(usuario.getId())) {

            throw new UsuarioNaoAutorizadoException();
        }
    }
}