package com.backend.alianzapro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.backend.alianzapro.models.Batallas;
import com.backend.alianzapro.models.Formulario;
import com.backend.alianzapro.services.BatallasServices;
import com.backend.alianzapro.services.FormularioServices;

@Controller
public class BatallasControllers {

 @Autowired
    private BatallasServices batallasServices;

    @GetMapping("/batallas/filtrar")
    public ResponseEntity<List<Batallas>> obtenerBatallasPorTipo(@RequestParam String tipoDeBatalla) {
        List<Batallas> batallas = batallasServices.obtenerBatallasPorTipo(tipoDeBatalla);
        if (batallas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay resultados
        }
        return ResponseEntity.ok(batallas); // Devuelve las batallas filtradas
    }
    
    
  @PostMapping("/insertar")
    public ResponseEntity<Batallas> insertarBatalla(@RequestBody Batallas batalla) {
        Batallas nuevaBatalla = batallasServices.insertarBatalla(batalla);
        return ResponseEntity.ok(nuevaBatalla);
    }
    
    
}
