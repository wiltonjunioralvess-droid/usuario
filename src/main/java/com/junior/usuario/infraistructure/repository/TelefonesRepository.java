package com.junior.usuario.infraistructure.repository;

import com.junior.usuario.infraistructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonesRepository extends JpaRepository<Telefone, Long> {
}
