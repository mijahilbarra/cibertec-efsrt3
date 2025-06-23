package com.cibertec.t2.service;

import com.cibertec.t2.model.Tutoria;
import com.cibertec.t2.model.TutoriaAsistente;
import com.cibertec.t2.model.Usuario;
import com.cibertec.t2.repository.TutoriaRepository;
import com.cibertec.t2.repository.TutoriaAsistenteRepository;
import com.cibertec.t2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TutoriaService {
    
    @Autowired
    private TutoriaRepository tutoriaRepository;
    
    @Autowired
    private TutoriaAsistenteRepository tutoriaAsistenteRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Tutoria> findAll() {
        return tutoriaRepository.findAll();
    }
    
    public Optional<Tutoria> findById(Long id) {
        return tutoriaRepository.findById(id);
    }
    
    public List<Tutoria> findByCreadoPor(Usuario creadoPor) {
        return tutoriaRepository.findByCreadoPor(creadoPor);
    }
    
    public List<Tutoria> findByCreadorId(Long idUsuario) {
        return tutoriaRepository.findByCreadoPorIdUsuario(idUsuario);
    }
    
    public List<Tutoria> findByTema(String tema) {
        return tutoriaRepository.findByTemaContainingIgnoreCase(tema);
    }
    
    public Tutoria save(Tutoria tutoria) {
        return tutoriaRepository.save(tutoria);
    }
    
    public void deleteById(Long id) {
        tutoriaRepository.deleteById(id);
    }
    
    public Tutoria update(Long id, Tutoria tutoriaDetails) {
        Optional<Tutoria> optionalTutoria = tutoriaRepository.findById(id);
        if (optionalTutoria.isPresent()) {
            Tutoria tutoria = optionalTutoria.get();
            tutoria.setTitulo(tutoriaDetails.getTitulo());
            tutoria.setTema(tutoriaDetails.getTema());
            tutoria.setImagen(tutoriaDetails.getImagen());
            tutoria.setHorario(tutoriaDetails.getHorario());
            if (tutoriaDetails.getCreadoPor() != null) {
                tutoria.setCreadoPor(tutoriaDetails.getCreadoPor());
            }
            return tutoriaRepository.save(tutoria);
        }
        return null;
    }
    
    // Methods for managing assistants
    public TutoriaAsistente inscribirAsistente(Long tutoriaId, Long usuarioId, String horarioSeleccionado) {
        Optional<Tutoria> tutoriaOpt = tutoriaRepository.findById(tutoriaId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        
        if (tutoriaOpt.isPresent() && usuarioOpt.isPresent()) {
            Tutoria tutoria = tutoriaOpt.get();
            Usuario usuario = usuarioOpt.get();
            
            // Check if already enrolled
            if (!tutoriaAsistenteRepository.existsByTutoriaAndUsuario(tutoria, usuario)) {
                TutoriaAsistente asistente = new TutoriaAsistente();
                asistente.setTutoria(tutoria);
                asistente.setUsuario(usuario);
                asistente.setHorarioSeleccionado(horarioSeleccionado);
                return tutoriaAsistenteRepository.save(asistente);
            }
        }
        return null;
    }
    
    // Keep the old method for backward compatibility
    public TutoriaAsistente inscribirAsistente(Long tutoriaId, Long usuarioId) {
        return inscribirAsistente(tutoriaId, usuarioId, null);
    }
    
    public void desinscribirAsistente(Long tutoriaId, Long usuarioId) {
        Optional<Tutoria> tutoriaOpt = tutoriaRepository.findById(tutoriaId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        
        if (tutoriaOpt.isPresent() && usuarioOpt.isPresent()) {
            Tutoria tutoria = tutoriaOpt.get();
            Usuario usuario = usuarioOpt.get();
            
            Optional<TutoriaAsistente> asistenteOpt = tutoriaAsistenteRepository.findByTutoriaAndUsuario(tutoria, usuario);
            asistenteOpt.ifPresent(tutoriaAsistenteRepository::delete);
        }
    }
    
    public List<TutoriaAsistente> getAsistentesByTutoria(Long tutoriaId) {
        Optional<Tutoria> tutoriaOpt = tutoriaRepository.findById(tutoriaId);
        if (tutoriaOpt.isPresent()) {
            return tutoriaAsistenteRepository.findByTutoria(tutoriaOpt.get());
        }
        return List.of();
    }
} 