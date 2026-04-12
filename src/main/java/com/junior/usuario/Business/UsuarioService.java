package com.junior.usuario.Business;

import com.junior.usuario.Business.Converter.UsuarioConverter;
import com.junior.usuario.Business.Dto.UsuarioDTO;
import com.junior.usuario.infraistructure.entity.Usuario;
import com.junior.usuario.infraistructure.exepcion.ConflictException;
import com.junior.usuario.infraistructure.exepcion.ResourceNotFoundException;
import com.junior.usuario.infraistructure.repository.UsuarioRepository;
import com.junior.usuario.infraistructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = VerificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("email já cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("email já cadastrado" + e.getCause());
        }
    }

    public boolean VerificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extrairEmaildoToken(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);


        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("email não localizado"));

        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);


        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

}
