package com.cibertec.t2.controller;

import com.cibertec.t2.model.Proyecto;
import com.cibertec.t2.model.Usuario;
import com.cibertec.t2.service.ProyectoService;
import com.cibertec.t2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {
    
    @Autowired
    private ProyectoService proyectoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarProyectos(Model model) {
        List<Proyecto> proyectos = proyectoService.findAll();
        model.addAttribute("proyectos", proyectos);
        return "proyectos-cards";
    }
    
    @GetMapping("/gestion")
    public String gestionProyectos(Model model) {
        List<Proyecto> proyectos = proyectoService.findAll();
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("usuarios", usuarios);
        return "proyectos";
    }
    
    @PostMapping("/guardar")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto, @RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        try {
            Optional<Usuario> usuario = usuarioService.findById(usuarioId);
            if (usuario.isPresent()) {
                proyecto.setUsuario(usuario.get());
                proyectoService.save(proyecto);
                redirectAttributes.addFlashAttribute("success", "Proyecto guardado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar proyecto: " + e.getMessage());
        }
        return "redirect:/proyectos/gestion";
    }
    
    @PostMapping("/actualizar")
    public String actualizarProyecto(@RequestParam Long id, @ModelAttribute Proyecto proyecto, @RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        try {
            Optional<Usuario> usuario = usuarioService.findById(usuarioId);
            if (usuario.isPresent()) {
                proyecto.setUsuario(usuario.get());
                Proyecto updated = proyectoService.update(id, proyecto);
                if (updated != null) {
                    redirectAttributes.addFlashAttribute("success", "Proyecto actualizado exitosamente");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Proyecto no encontrado");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar proyecto: " + e.getMessage());
        }
        return "redirect:/proyectos/gestion";
    }
    
    @PostMapping("/eliminar")
    public String eliminarProyecto(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            proyectoService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Proyecto eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar proyecto: " + e.getMessage());
        }
        return "redirect:/proyectos/gestion";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Proyecto> obtenerProyecto(@PathVariable Long id) {
        return proyectoService.findById(id);
    }
    
    @GetMapping("/{id}/detalle")
    @ResponseBody
    public Proyecto obtenerDetalleProyecto(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.findById(id);
        return proyecto.orElse(null);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @ResponseBody
    public List<Proyecto> obtenerProyectosPorUsuario(@PathVariable Long usuarioId) {
        return proyectoService.findByUsuarioId(usuarioId);
    }
} 