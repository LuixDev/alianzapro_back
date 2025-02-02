package com.backend.alianzapro.controllers;

import com.backend.alianzapro.models.Login;
import com.backend.alianzapro.repositories.LoginRepositories;
import com.backend.alianzapro.services.LoginServices;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/api/logins")
public class LoginControllers {

    @Autowired
    private LoginServices loginService;
     private LoginRepositories loginRepositories; 
    // Crear un nuevo Login
    @PostMapping
    public ResponseEntity<Login> createLogin(@RequestBody Login login) {
        Login savedLogin = loginService.save(login);
        return ResponseEntity.ok(savedLogin);
    }

    

    // Obtener todos los logins
    @GetMapping("/cuentas")
    public ResponseEntity<List<Login>> getAllLogins() {
        List<Login> logins = loginService.findAll();
        return ResponseEntity.ok(logins);
    }

    

    // Encontrar un Login por nombre de usuario
    @GetMapping("/{username}")
    public ResponseEntity<Login> getLoginByUsername(@PathVariable String username) {
        Login login = loginService.findByUsername(username);
        if (login != null) {
            return ResponseEntity.ok(login);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un Login por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable Long id) {
        loginService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

 @PostMapping("/login")
public ResponseEntity<String> login(@RequestBody Login loginRequest) {
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();
    
    // Lógica para validar el usuario
    boolean isValidUser = loginService.validateUser(username, password);

    if (isValidUser) {
        Long userId = loginService.getUserIdByUsername(username);
        // Generación de una clave secreta segura para HS512
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // Generación del token JWT sin fecha de expiración
        String token = Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(new Date()) // Fecha de emisión
            .signWith(secretKey) // Firmar con la clave secreta generada
            .compact();
        
        return ResponseEntity.ok(token); // Enviar el token al frontend
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Nombre de usuario o contraseña incorrectos");
    }
}

    
}
