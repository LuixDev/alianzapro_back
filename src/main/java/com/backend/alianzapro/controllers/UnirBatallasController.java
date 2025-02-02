package com.backend.alianzapro.controllers;

import com.backend.alianzapro.models.UnirBatallas;

import com.backend.alianzapro.services.UnirBatallasServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batallas") // Ruta base para las solicitudes
public class UnirBatallasController {

    @Autowired
    private UnirBatallasServices unirBatallasServices;

    // Obtener todas las batallas
    @GetMapping("/obtener")
    public List<UnirBatallas> getAllBatallas() {
        return unirBatallasServices.getAllBatallas();
    }

    // Obtener una batalla por ID
    @GetMapping("/{id}")
    public ResponseEntity<UnirBatallas> getBatallaById(@PathVariable Long id) {
        return unirBatallasServices.getBatallaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva batalla
    @PostMapping("/unir")
    public UnirBatallas createBatalla(@RequestBody UnirBatallas unirBatallas) {
        return unirBatallasServices.saveBatalla(unirBatallas);
    }

    // Actualizar una batalla
    @PutMapping("/{id}")
    public ResponseEntity<UnirBatallas> updateBatalla(@PathVariable Long id, @RequestBody UnirBatallas updatedBatalla) {
        try {
            return ResponseEntity.ok(unirBatallasServices.updateBatalla(id, updatedBatalla));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una batalla
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatalla(@PathVariable Long id) {
        try {
            unirBatallasServices.deleteBatalla(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/unirbatallas")
    public ResponseEntity<String> unirBatallas(@RequestBody UnirBatallas unirBatallas) {
        // Suponiendo que BatallaDTO contiene los valores de actualId y hostId
        Long actualId =  unirBatallas.getActualId();
        Long hostId =  unirBatallas.getHostId();

        // Llamar al servicio para enviar correos electrónicos
        unirBatallasServices.verificarYEnviarCorreo(actualId, hostId);

        // Continuar con la lógica de la aplicación...
        return ResponseEntity.ok("Correo enviado.");
    }
    
}


