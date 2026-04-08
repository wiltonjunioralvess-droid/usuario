package com.junior.usuario.Business;

import com.junior.usuario.Business.Converter.UsuarioConverter;
import com.junior.usuario.Business.Dto.UsuarioDTO;
import com.junior.usuario.infraistructure.security.Security.entity.Usuario;
import com.junior.usuario.infraistructure.security.Security.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
       return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
