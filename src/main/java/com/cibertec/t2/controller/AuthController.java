package com.cibertec.t2.controller;

import com.cibertec.t2.model.Usuario;
import com.cibertec.t2.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // If already logged in, redirect to dashboard
        if (session.getAttribute("usuario") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, 
                       HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            // Validate input
            if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Email y contraseña son requeridos");
                return "redirect:/login";
            }
            
            // Clean input
            email = email.trim().toLowerCase();
            
            // Find user by email
            Optional<Usuario> usuario = usuarioService.findByEmail(email);
            
            if (usuario.isPresent()) {
                Usuario user = usuario.get();
                // Check password
                if (user.getPassword() != null && user.getPassword().equals(password)) {
                    // Store user in session
                    session.setAttribute("usuario", user);
                    session.setAttribute("usuarioId", user.getIdUsuario());
                    session.setAttribute("usuarioNombre", user.getNombre());
                    
                    redirectAttributes.addFlashAttribute("success", "¡Bienvenido " + user.getNombre() + "!");
                    return "redirect:/dashboard";
                } else {
                    redirectAttributes.addFlashAttribute("error", "Contraseña incorrecta");
                    return "redirect:/login";
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/login";
            }
        } catch (org.springframework.dao.DataAccessException e) {
            System.err.println("Database error during login: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error de conexión a la base de datos. Intenta de nuevo.");
            return "redirect:/login";
        } catch (Exception e) {
            System.err.println("Unexpected error during login: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error inesperado. Intenta de nuevo.");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        // If already logged in, redirect to dashboard
        if (session.getAttribute("usuario") != null) {
            return "redirect:/dashboard";
        }
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            // Validate input
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre es requerido");
                return "redirect:/register";
            }
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El email es requerido");
                return "redirect:/register";
            }
            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "La contraseña es requerida");
                return "redirect:/register";
            }
            
            // Clean and prepare user data
            usuario.setNombre(usuario.getNombre().trim());
            usuario.setEmail(usuario.getEmail().trim().toLowerCase());
            usuario.setPassword(usuario.getPassword().trim());
            
            // Check if email already exists
            Optional<Usuario> existingUser = usuarioService.findByEmail(usuario.getEmail());
            if (existingUser.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "El email ya está registrado");
                return "redirect:/register";
            }
            
            // Save new user
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("success", "Usuario registrado exitosamente. Ya puedes iniciar sesión.");
            return "redirect:/login";
            
        } catch (org.springframework.dao.DataAccessException e) {
            System.err.println("Database error during registration: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error de conexión a la base de datos. Intenta de nuevo.");
            return "redirect:/register";
        } catch (Exception e) {
            System.err.println("Unexpected error during registration: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error inesperado. Intenta de nuevo.");
            return "redirect:/register";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Sesión cerrada exitosamente");
        return "redirect:/login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuario", usuario);
        return "dashboard";
    }
} 