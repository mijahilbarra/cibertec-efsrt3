package com.cibertec.t2.controller;

import com.cibertec.t2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            // Test database connection
            Connection connection = dataSource.getConnection();
            boolean isValid = connection.isValid(5);
            connection.close();
            
            status.put("database", isValid ? "UP" : "DOWN");
            status.put("status", "UP");
            
            // Test user service
            long userCount = usuarioService.findAll().size();
            status.put("userCount", userCount);
            
        } catch (Exception e) {
            status.put("database", "DOWN");
            status.put("status", "DOWN");
            status.put("error", e.getMessage());
        }
        
        return status;
    }
} 