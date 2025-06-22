package com.cibertec.t2.repository;

import com.cibertec.t2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring Data JPA provides basic CRUD operations automatically
    // Custom queries for Usuario
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
} 