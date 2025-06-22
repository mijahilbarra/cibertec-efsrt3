package com.cibertec.t2.controller;

import com.cibertec.t2.model.Usuario;
import com.cibertec.t2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }
    
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            if (usuarioService.existsByEmail(usuario.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "El email ya está registrado");
                return "redirect:/usuarios";
            }
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("success", "Usuario guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar usuario: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }
    
    @PostMapping("/actualizar")
    public String actualizarUsuario(@RequestParam Long id, @ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            Optional<Usuario> existingUsuario = usuarioService.findById(id);
            if (existingUsuario.isPresent() && 
                !existingUsuario.get().getEmail().equals(usuario.getEmail()) && 
                usuarioService.existsByEmail(usuario.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "El email ya está registrado por otro usuario");
                return "redirect:/usuarios";
            }
            
            Usuario updated = usuarioService.update(id, usuario);
            if (updated != null) {
                redirectAttributes.addFlashAttribute("success", "Usuario actualizado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar usuario: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }
    
    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado exitosamente");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el usuario porque tiene proyectos o tutorías asociadas");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar usuario: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.findById(id);
    }
} 