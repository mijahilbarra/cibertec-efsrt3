package com.cibertec.t2.controller;

import com.cibertec.t2.model.Tutoria;
import com.cibertec.t2.model.TutoriaAsistente;
import com.cibertec.t2.model.Usuario;
import com.cibertec.t2.service.TutoriaService;
import com.cibertec.t2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tutorias")
public class TutoriaController {
    
    @Autowired
    private TutoriaService tutoriaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarTutorias(Model model) {
        List<Tutoria> tutorias = tutoriaService.findAll();
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("tutorias", tutorias);
        model.addAttribute("usuarios", usuarios);
        return "tutorias-cards";
    }
    
    @GetMapping("/gestion")
    public String gestionTutorias(Model model) {
        List<Tutoria> tutorias = tutoriaService.findAll();
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("tutorias", tutorias);
        model.addAttribute("usuarios", usuarios);
        return "tutorias";
    }
    
    @PostMapping("/guardar")
    public String guardarTutoria(@ModelAttribute Tutoria tutoria, @RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        try {
            Optional<Usuario> usuario = usuarioService.findById(usuarioId);
            if (usuario.isPresent()) {
                tutoria.setCreadoPor(usuario.get());
                tutoriaService.save(tutoria);
                redirectAttributes.addFlashAttribute("success", "Tutoría guardada exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar tutoría: " + e.getMessage());
        }
        return "redirect:/tutorias/gestion";
    }
    
    @PostMapping("/actualizar")
    public String actualizarTutoria(@RequestParam Long id, @ModelAttribute Tutoria tutoria, @RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        try {
            Optional<Usuario> usuario = usuarioService.findById(usuarioId);
            if (usuario.isPresent()) {
                tutoria.setCreadoPor(usuario.get());
                Tutoria updated = tutoriaService.update(id, tutoria);
                if (updated != null) {
                    redirectAttributes.addFlashAttribute("success", "Tutoría actualizada exitosamente");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Tutoría no encontrada");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar tutoría: " + e.getMessage());
        }
        return "redirect:/tutorias/gestion";
    }
    
    @PostMapping("/eliminar")
    public String eliminarTutoria(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            tutoriaService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Tutoría eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar tutoría: " + e.getMessage());
        }
        return "redirect:/tutorias/gestion";
    }
    
    @PostMapping("/inscribir")
    public String inscribirAsistente(@RequestParam Long tutoriaId, @RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        try {
            TutoriaAsistente asistente = tutoriaService.inscribirAsistente(tutoriaId, usuarioId);
            if (asistente != null) {
                redirectAttributes.addFlashAttribute("success", "Usuario inscrito exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "El usuario ya está inscrito o no se encontró la tutoría/usuario");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al inscribir usuario: " + e.getMessage());
        }
        return "redirect:/tutorias";
    }
    
    @PostMapping("/desinscribir")
    public String desinscribirAsistente(@RequestParam Long tutoriaId, @RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        try {
            tutoriaService.desinscribirAsistente(tutoriaId, usuarioId);
            redirectAttributes.addFlashAttribute("success", "Usuario desinscrito exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al desinscribir usuario: " + e.getMessage());
        }
        return "redirect:/tutorias";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Tutoria> obtenerTutoria(@PathVariable Long id) {
        return tutoriaService.findById(id);
    }
    
    @GetMapping("/{id}/asistentes")
    @ResponseBody
    public List<TutoriaAsistente> obtenerAsistentes(@PathVariable Long id) {
        return tutoriaService.getAsistentesByTutoria(id);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @ResponseBody
    public List<Tutoria> obtenerTutoriasPorUsuario(@PathVariable Long usuarioId) {
        return tutoriaService.findByCreadorId(usuarioId);
    }
} 