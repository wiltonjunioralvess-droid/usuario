package com.junior.usuario.infraistructure.security.Security.repository;


import com.junior.usuario.infraistructure.security.Security.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonesRepository extends JpaRepository<Telefone, Long> {
}
