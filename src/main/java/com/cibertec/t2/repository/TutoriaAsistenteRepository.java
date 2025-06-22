package com.cibertec.t2.repository;

import com.cibertec.t2.model.TutoriaAsistente;
import com.cibertec.t2.model.Tutoria;
import com.cibertec.t2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutoriaAsistenteRepository extends JpaRepository<TutoriaAsistente, Long> {
    // Spring Data JPA provides basic CRUD operations automatically
    // Custom queries for TutoriaAsistente
    List<TutoriaAsistente> findByTutoria(Tutoria tutoria);
    List<TutoriaAsistente> findByUsuario(Usuario usuario);
    Optional<TutoriaAsistente> findByTutoriaAndUsuario(Tutoria tutoria, Usuario usuario);
    boolean existsByTutoriaAndUsuario(Tutoria tutoria, Usuario usuario);
} 