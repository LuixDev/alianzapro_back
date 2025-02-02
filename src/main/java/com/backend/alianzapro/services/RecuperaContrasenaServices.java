package com.backend.alianzapro.services;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.alianzapro.EmailService;
import com.backend.alianzapro.models.RecuperaContrasena;
import com.backend.alianzapro.repositories.RecuperaContrasenaRepositories;

import jakarta.transaction.Transactional;

@Service
public class RecuperaContrasenaServices {

    private final RecuperaContrasenaRepositories recuperaContraseñaRepositories;
    private final Map<String, String> codigosTemporales = new ConcurrentHashMap<>(); // Almacenamiento temporal
    @Autowired
    private EmailService emailService; // Ensure this is auto-wired

    

    public RecuperaContrasenaServices(RecuperaContrasenaRepositories recuperaContraseñaRepositories) {
        this.recuperaContraseñaRepositories = recuperaContraseñaRepositories;
    }

    public boolean existePorEmail(String email) {
        boolean existe = recuperaContraseñaRepositories.existsByEmail(email);
        System.out.println("Email existe: " + existe); // Log para depuración
        return existe;
    }

    public String generarCodigoVerificacion() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // Código de 6 dígitos
        return String.valueOf(codigo);
    }

    public boolean enviarCodigoPorEmail(String email, String codigo) {
        try {
            String subject = "AlianzaPro Código de Verificación";
            String text = "Tu código de verificación es: " + codigo + "\n\n";
            String to = email;
    
            // Test sending the email directly (without async) for debugging
            CompletableFuture.runAsync(() -> {
                try {
                    emailService.sendEmail(to, subject, text);
                    guardarCodigoTemporal(email, codigo);
                } catch (Exception e) {
                    // Log de error si el envío del correo falla
                    e.printStackTrace();
                }
            });
    
            // Almacenar el código temporalmente
           
           
            return true; // Envío exitoso
        } catch (DataAccessException e) {
            // Log de error específico para problemas de base de datos
            e.printStackTrace();
            throw new RuntimeException("Error en la base de datos : " + e.getMessage());
        } catch (Exception e) {
            // Log de error general para cualquier otro problema
            e.printStackTrace();
            throw new RuntimeException("Error : " + e.getMessage());
        }
    }
    

    public void guardarCodigoTemporal(String email, String codigo) {
        // Guardar el código temporalmente
        codigosTemporales.put(email, codigo);
        System.out.println("Código almacenado -> Email: " + email + ", Código: " + codigo);

        // Configurar un temporizador para eliminar el código después de 5 minutos
        new Thread(() -> {
            try {
                Thread.sleep(900_000); // 15 minutos en milisegundos
                codigosTemporales.remove(email);
                System.out.println("Código expirado para: " + email);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public boolean validarCodigo(String email, String codigo) {
        // Verificar que el email y el código proporcionados no sean nulos
        if (email == null || email.isEmpty() || codigo == null || codigo.isEmpty()) {
            System.out.println("El email o el código proporcionado es nulo o vacío.");
            return false; // Devuelve false si los parámetros son inválidos
        }
    
        // Recuperar el código almacenado para el email
        String codigoGuardado = codigosTemporales.get(email);
    
        // Verificar que el código almacenado no sea nulo
        if (codigoGuardado == null) {
            System.out.println("No se encontró un código asociado para el email proporcionado.");
            return false; // Devuelve false si no hay un código almacenado
        }
    
        // Eliminar cualquier espacio o salto de línea adicional
        String codigoGuardadoTrimmed = codigoGuardado.trim();
        String codigoTrimmed = codigo.trim();
    
        // Comparar los códigos
        if (codigoGuardadoTrimmed.equals(codigoTrimmed)) {
            System.out.println("El código es válido.");
            return true; // Si los códigos coinciden, es válido
        } else {
            System.out.println("El código es inválido o ha expirado.");
            return false; // Si no coinciden, es inválido
        }
    }
    
    
    @Transactional
  public boolean cambiarContraseña(String email, String nuevaContraseña) {
    // Buscar al usuario por su email
    RecuperaContrasena usuario = recuperaContraseñaRepositories.findByEmail(email);
    
    if (usuario == null) {
        return false; // Si no existe, no se puede cambiar la contraseña
    }
    
    // Cifrar la nueva contraseña antes de almacenarla
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String contrasenaCifrada = encoder.encode(nuevaContraseña);
    
    // Actualizar la contraseña
    usuario.setPassword(contrasenaCifrada);
    recuperaContraseñaRepositories.save(usuario);
    
    return true;
}
    
    
}
