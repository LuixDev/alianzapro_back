package com.backend.alianzapro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import com.backend.alianzapro.EmailService;
import com.backend.alianzapro.models.Formulario;
import com.backend.alianzapro.repositories.FormularioRepositories;

import jakarta.transaction.Transactional;

@Service
public class FormularioServices {

    @Autowired
    private FormularioRepositories formularioRepository;

    @Autowired
    private EmailService emailService;


    
    

    @Transactional
    public void guardarFormulario(Formulario formulario) {
        try {
            // Guarda el formulario utilizando el repositorio
            formularioRepository.save(formulario);
            
            // Configuración del asunto y texto del correo
            String subject = "AlianzaPro nuevos datos recibido";
            String text = "Hola " + formulario.getNombres() + ",\n\n" +
                          "Nuevos datos se ha enviado al formulario de aprobación de AlianzaPro. Los datos han sido registrados correctamente.\n\n" +
                          "Saludos,\nEl equipo de soporte AlianzaPro.";

            // Enviar el correo de forma asíncrona
            CompletableFuture.runAsync(() -> {
                try {
                    emailService.sendEmail("nexuslivepro9@gmail.com", subject, text);
                } catch (Exception e) {
                    // Log de error si el envío del correo falla
                    e.printStackTrace();
                }
            });

        } catch (DataAccessException e) {
            // Log de error específico para problemas de base de datos
            e.printStackTrace();
            throw new RuntimeException("Error en la base de datos al guardar el formulario: " + e.getMessage());
        } catch (Exception e) {
            // Log de error general para cualquier otro problema
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el formulario: " + e.getMessage());
        }
    }

    public List<Formulario> obtenerTodosLosFormularios() {
        return formularioRepository.findAll();
    }
}
