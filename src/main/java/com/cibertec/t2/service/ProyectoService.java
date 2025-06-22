package com.cibertec.t2.service;

import com.cibertec.t2.model.Proyecto;
import com.cibertec.t2.model.Usuario;
import com.cibertec.t2.repository.ProyectoRepository;
import com.cibertec.t2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProyectoService {
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }
    
    public Optional<Proyecto> findById(Long id) {
        return proyectoRepository.findById(id);
    }
    
    public List<Proyecto> findByUsuario(Usuario usuario) {
        return proyectoRepository.findByUsuario(usuario);
    }
    
    public List<Proyecto> findByUsuarioId(Long idUsuario) {
        return proyectoRepository.findByUsuarioIdUsuario(idUsuario);
    }
    
    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }
    
    public void deleteById(Long id) {
        proyectoRepository.deleteById(id);
    }
    
    public Proyecto update(Long id, Proyecto proyectoDetails) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setNombre(proyectoDetails.getNombre());
            proyecto.setDescripcion(proyectoDetails.getDescripcion());
            proyecto.setImagen(proyectoDetails.getImagen());
            if (proyectoDetails.getUsuario() != null) {
                proyecto.setUsuario(proyectoDetails.getUsuario());
            }
            return proyectoRepository.save(proyecto);
        }
        return null;
    }
} 