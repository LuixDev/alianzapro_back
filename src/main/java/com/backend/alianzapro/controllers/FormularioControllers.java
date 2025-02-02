

package com.backend.alianzapro.controllers;

import com.backend.alianzapro.models.Formulario;
import com.backend.alianzapro.services.FormularioServices;

import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class FormularioControllers {

    @Autowired
    private FormularioServices formularioService;

    // Mostrar el formulario
    @GetMapping("/formularios")
    public ResponseEntity<?> obtenerTodosLosFormularios() {
        List<Formulario> formularios = formularioService.obtenerTodosLosFormularios();
        return ResponseEntity.ok(formularios);
    }
    

  
    @PostMapping("/formulario")
    public ResponseEntity<?> enviarFormulario(
            @RequestParam("nombre_cuenta") String nombreCuenta,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("email") String email,
            @RequestParam("nombres") String nombres,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("fecha_nacimiento") LocalDate fechaNacimiento,
            @RequestParam("nombre_agencia") String nombreAgencia,
            @RequestParam("emoji") String emoji,
            @RequestParam("celular") String celular,
            @RequestParam("color") String color,
            @RequestParam("razon_unirse") String razonUnirse,
            @RequestParam("imagen_agencia") MultipartFile imagenAgencia
    ) {
        // Validación de campos requeridos
        if (nombreCuenta == null || contrasena == null || email == null || nombreAgencia == null || fechaNacimiento == null) {
            return ResponseEntity.badRequest().body("Faltan datos requeridos.");
        }

        // Crear objeto de formulario
        Formulario formulario = new Formulario();
        formulario.setNombreCuenta(nombreCuenta);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenaCifrada = encoder.encode(contrasena);
        formulario.setContrasena(contrasenaCifrada);
        formulario.setEmail(email);
        formulario.setNombres(nombres);
        formulario.setApellidos(apellidos);
        formulario.setFechaNacimiento(fechaNacimiento);
        formulario.setNombreAgencia(nombreAgencia);
        formulario.setEmoji(emoji);
        formulario.setCelular(celular);
        formulario.setColor(color);
        formulario.setRazonUnirse(razonUnirse);
        formulario.setAceptado(false);
        formulario.setRechazado(false);

        // Subir la imagen si está presente
        if (!imagenAgencia.isEmpty()) {
            try {
                // Obtener nombre del archivo y convertirlo en bytes
                String fileName = System.currentTimeMillis() + "_" + imagenAgencia.getOriginalFilename();
                byte[] fileBytes = imagenAgencia.getBytes();

                // Preparar los datos para la solicitud multipart
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("file", new ByteArrayResource(fileBytes) {
                    @Override
                    public String getFilename() {
                        return fileName;
                    }
                });

                // Obtener token de autorización de una variable de entorno
              
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InB2amdudWtrdHNreW5yaXFncWRiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjg2MTkxMzEsImV4cCI6MjA0NDE5NTEzMX0.ccMmyf5cg4xT2BpGt4W5R_LDi6655GsPMSbokYQ4Mjw");

                // URL de Supabase para subir la imagen
                String url = "https://pvjgnukktskynriqgqdb.supabase.co/storage/v1/object/imagen/" + fileName;
                HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

                // Enviar solicitud de subida
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

                // Comprobar si la subida fue exitosa
                if (response.getStatusCode().is2xxSuccessful()) {
                    // URL de la imagen en el bucket
                    String imageUrl = "https://pvjgnukktskynriqgqdb.supabase.co/storage/v1/object/imagen/" + fileName;
                    formulario.setImagenAgencia(imageUrl);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen a Supabase.");
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen.");
            }
        }

        // Guardar el formulario en la base de datos
        formularioService.guardarFormulario(formulario);

        // Respuesta exitosa
        return ResponseEntity.status(HttpStatus.CREATED).body("Formulario enviado correctamente");
    }
}