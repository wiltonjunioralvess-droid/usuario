package com.junior.usuario.Controller;

import com.junior.usuario.Business.Dto.UsuarioDTO;
import com.junior.usuario.Business.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    @PostMapping
    public ResponseEntity <UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));

    }
}
