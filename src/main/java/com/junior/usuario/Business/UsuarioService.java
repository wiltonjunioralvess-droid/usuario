package com.junior.usuario.Business;

import com.junior.usuario.Business.Converter.UsuarioConverter;
import com.junior.usuario.Business.Dto.EnderecoDTO;
import com.junior.usuario.Business.Dto.TelefoneDTO;
import com.junior.usuario.Business.Dto.UsuarioDTO;
import com.junior.usuario.infraistructure.entity.Endereco;
import com.junior.usuario.infraistructure.entity.Telefone;
import com.junior.usuario.infraistructure.entity.Usuario;
import com.junior.usuario.infraistructure.exepcion.ConflictException;
import com.junior.usuario.infraistructure.exepcion.ResourceNotFoundException;
import com.junior.usuario.infraistructure.repository.EnderecoRepository;
import com.junior.usuario.infraistructure.repository.TelefoneRepository;
import com.junior.usuario.infraistructure.repository.UsuarioRepository;
import com.junior.usuario.infraistructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;


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


    public UsuarioDTO buscarUsuarioEmail(String email) {
        try {

            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException
                            ("email não encontrado " + email)));

        }catch (ResourceNotFoundException e){
                throw new ResourceNotFoundException("Email não encontrado " + email);
        }
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

    public EnderecoDTO atualizaDadosUsuario(Long idEndereco, EnderecoDTO enderecoDTO) {

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);
        enderecoRepository.save(endereco);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto) {

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("ID não encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
