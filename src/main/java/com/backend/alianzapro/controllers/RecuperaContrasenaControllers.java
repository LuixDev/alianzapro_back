package com.backend.alianzapro.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.alianzapro.services.RecuperaContrasenaServices;

@RestController
@RequestMapping("/api/usuarios")
public class RecuperaContrasenaControllers {
    
    private final RecuperaContrasenaServices recuperaContraseñaServices;

    // Constructor corregido
    public RecuperaContrasenaControllers(RecuperaContrasenaServices recuperaContraseñaServices) {
        this.recuperaContraseñaServices = recuperaContraseñaServices;
    }

    @GetMapping("/verificar-email")
    public ResponseEntity<Boolean> verificarEmail(@RequestParam String email) {
        boolean existe = recuperaContraseñaServices.existePorEmail(email);
        return ResponseEntity.ok(existe);
    }


 @PostMapping("/enviar-codigo")
public ResponseEntity<Map<String, String>> enviarCodigo(@RequestParam String email) {
    boolean existe = recuperaContraseñaServices.existePorEmail(email);
    if (!existe) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "El correo no está registrado.");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Generar el código de verificación
    String codigo = recuperaContraseñaServices.generarCodigoVerificacion();

    // Enviar el código por correo
    boolean enviado = recuperaContraseñaServices.enviarCodigoPorEmail(email, codigo);

    if (!enviado) {
        Map<String, String> errorResponse = new HashMap<>();
        
        errorResponse.put("error", "No se pudo enviar el código.");
        return ResponseEntity.status(500).body(errorResponse);
    }

    Map<String, String> successResponse = new HashMap<>();
    
    successResponse.put("mensaje", "Código enviado exitosamente.");
    return ResponseEntity.ok(successResponse);
}

@PostMapping("/verificar-codigo")
public ResponseEntity<Map<String, Object>> verificarCodigo(@RequestParam String email, @RequestParam String codigo) {
    boolean codigoValido = recuperaContraseñaServices.validarCodigo(email, codigo);

    if (!codigoValido) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "El código es inválido o ha expirado.");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    Map<String, Object> successResponse = new HashMap<>();
    successResponse.put("success", true);
    successResponse.put("mensaje", "Código verificado correctamente.");
    return ResponseEntity.ok(successResponse);
}


    @PostMapping("/cambiar-contraseña")
    public ResponseEntity<String> cambiarContraseña(@RequestParam String email, @RequestParam String codigo, @RequestParam String nuevaContraseña) {
        // Verificar si el código es válido
        boolean codigoValido = recuperaContraseñaServices.validarCodigo(email, codigo);
        
        if (!codigoValido) {
            return ResponseEntity.badRequest().body("El código es inválido o ha expirado.");
        }

        // Cambiar la contraseña
        boolean contrasenaCambiada = recuperaContraseñaServices.cambiarContraseña(email, nuevaContraseña);
        
        if (!contrasenaCambiada) {
            return ResponseEntity.status(500).body("No se pudo cambiar la contraseña.");
        }

        return ResponseEntity.ok("Contraseña cambiada exitosamente.");
    }
    
}
