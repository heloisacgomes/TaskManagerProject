package com.mycompany.taskmanager.service;

import com.mycompany.taskmanager.dto.UsuarioRegistroDTO;
import com.mycompany.taskmanager.dto.UsuarioResponseDTO;
import com.mycompany.taskmanager.entity.Usuario;
import com.mycompany.taskmanager.exception.EmailCadastradoException;
import com.mycompany.taskmanager.dto.LoginRequestDTO;
import com.mycompany.taskmanager.dto.LoginResponseDTO;
import com.mycompany.taskmanager.exception.CredenciaisInvalidasException;
import com.mycompany.taskmanager.security.JwtService;
import com.mycompany.taskmanager.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

private final UsuarioRepository usuarioRepository;
private final PasswordEncoder passwordEncoder;
private final JwtService jwtService;

public UsuarioService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService) {

    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
}

public UsuarioResponseDTO registrar(UsuarioRegistroDTO dto) {
        
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailCadastradoException();
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(
                passwordEncoder.encode(dto.getSenha())
        );

        Usuario usuarioSalvo =
                usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail()
        );
    }

public LoginResponseDTO login(LoginRequestDTO dto) {

    Usuario usuario = usuarioRepository
            .findByEmail(dto.getEmail())
            .orElseThrow(CredenciaisInvalidasException::new);

    boolean senhaCorreta = passwordEncoder.matches(
            dto.getSenha(),
            usuario.getSenha()
    );

    if (!senhaCorreta) {
        throw new CredenciaisInvalidasException();
    }

    String token = jwtService.gerarToken(
            usuario.getEmail()
    );

    return new LoginResponseDTO(token);
}

}
