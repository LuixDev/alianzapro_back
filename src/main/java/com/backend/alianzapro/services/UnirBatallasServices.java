package com.backend.alianzapro.services;

import com.backend.alianzapro.EmailService;
import com.backend.alianzapro.models.Login;
import com.backend.alianzapro.models.UnirBatallas;
import com.backend.alianzapro.repositories.LoginRepositories;
import com.backend.alianzapro.repositories.UnirBatallasRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnirBatallasServices {


     @Autowired
    private LoginRepositories loginRepositories;

    @Autowired
    private EmailService emailService;  // Asumimos que tienes un servicio para enviar correos

  
    


    @Autowired
    private UnirBatallasRepositories unirBatallasRepositories;

    // Obtener todas las batallas
    public List<UnirBatallas> getAllBatallas() {
        return unirBatallasRepositories.findAll();
    }

    // Obtener una batalla por ID
    public Optional<UnirBatallas> getBatallaById(Long id) {
        return unirBatallasRepositories.findById(id);
    }

    // Guardar una nueva batalla
    public UnirBatallas saveBatalla(UnirBatallas unirBatallas) {
        return unirBatallasRepositories.save(unirBatallas);
    }

    // Actualizar una batalla existente
    public UnirBatallas updateBatalla(Long id, UnirBatallas updatedBatalla) {
        return unirBatallasRepositories.findById(id).map(batalla -> {
            batalla.setAgencia(updatedBatalla.getAgencia());
            batalla.setRonda(updatedBatalla.getRonda());
            batalla.setPuntos(updatedBatalla.getPuntos());
            batalla.setReto(updatedBatalla.getReto());
            batalla.setObservaciones(updatedBatalla.getObservaciones());
            batalla.setActualId(updatedBatalla.getActualId());
            batalla.setIdBatalla(updatedBatalla.getIdBatalla());
            batalla.setEnviarCorreoBoth(updatedBatalla.isEnviarCorreoBoth());
            return unirBatallasRepositories.save(batalla);
        }).orElseThrow(() -> new RuntimeException("Batalla no encontrada con ID: " + id));
    }

    // Eliminar una batalla
    public void deleteBatalla(Long id) {
        unirBatallasRepositories.deleteById(id);
    }

  
    public void verificarYEnviarCorreo(Long actualId, Long hostId) {
        // Buscar el email asociado al actualId y hostId
        Optional<Login> actualUser = loginRepositories.findById(actualId);
        Optional<Login> hostUser = loginRepositories.findById(hostId);

        // Verificar si ambos usuarios existen
        if (actualUser.isPresent() && hostUser.isPresent()) {
            String emailActual = actualUser.get().getEmail();
            String emailHost = hostUser.get().getEmail();

            // Lógica para enviar los correos
            enviarCorreo(emailActual, "Notificación de batalla", "Se ha unido a una batalla.");
            enviarCorreo(emailHost, "Notificación de batalla", "El usuario ha completado la batalla.");
        }
    }

    private void enviarCorreo(String toEmail, String subject, String message) {
        // Lógica para enviar el correo electrónico
        emailService.sendEmail(toEmail, subject, message);
    }
    
}