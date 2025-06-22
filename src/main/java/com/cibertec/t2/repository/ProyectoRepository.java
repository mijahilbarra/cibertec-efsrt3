package com.cibertec.t2.repository;

import com.cibertec.t2.model.Proyecto;
import com.cibertec.t2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    // Spring Data JPA provides basic CRUD operations automatically
    // Custom queries for Proyecto
    List<Proyecto> findByUsuario(Usuario usuario);
    List<Proyecto> findByUsuarioIdUsuario(Long idUsuario);
} 