package com.junior.usuario.infraistructure.security.Security.repository;


import com.junior.usuario.infraistructure.security.Security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Object> findByEmail(String email);

    void deleteByEmail(String email);
}
