package com.cibertec.t2.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tutoria_asistentes")
public class TutoriaAsistente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tutoria", nullable = false)
    @JsonBackReference
    private Tutoria tutoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "horario_seleccionado")
    private String horarioSeleccionado;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Tutoria getTutoria() { return tutoria; }
    public void setTutoria(Tutoria tutoria) { this.tutoria = tutoria; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getHorarioSeleccionado() { return horarioSeleccionado; }
    public void setHorarioSeleccionado(String horarioSeleccionado) { this.horarioSeleccionado = horarioSeleccionado; }
} 