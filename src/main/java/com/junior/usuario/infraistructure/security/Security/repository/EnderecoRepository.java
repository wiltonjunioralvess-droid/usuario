package com.junior.usuario.infraistructure.security.Security.repository;

import com.junior.teste1.infrastruture.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
