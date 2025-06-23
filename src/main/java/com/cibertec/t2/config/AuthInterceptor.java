package com.cibertec.t2.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        // Public paths that don't require authentication
        if (uri.equals("/") || uri.equals("/login") || uri.equals("/register") || uri.equals("/health") ||
            uri.startsWith("/proyectos") && !uri.contains("/gestion") ||
            uri.startsWith("/tutorias") && !uri.contains("/gestion") ||
            uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/images/")) {
            return true;
        }
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            return true;
        }
        
        // Redirect to login if not authenticated
        response.sendRedirect("/login");
        return false;
    }
} 