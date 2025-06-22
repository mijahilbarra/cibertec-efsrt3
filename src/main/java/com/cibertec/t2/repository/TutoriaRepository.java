package com.cibertec.t2.repository;

import com.cibertec.t2.model.Tutoria;
import com.cibertec.t2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutoriaRepository extends JpaRepository<Tutoria, Long> {
    // Spring Data JPA provides basic CRUD operations automatically
    // Custom queries for Tutoria
    List<Tutoria> findByCreadoPor(Usuario creadoPor);
    List<Tutoria> findByCreadoPorIdUsuario(Long idUsuario);
    List<Tutoria> findByTemaContainingIgnoreCase(String tema);
} 