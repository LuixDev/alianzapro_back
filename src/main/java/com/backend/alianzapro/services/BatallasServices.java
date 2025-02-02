package com.backend.alianzapro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.backend.alianzapro.models.Batallas;
import com.backend.alianzapro.repositories.BatallasRepositories;

@Service  // Add this annotation
public class BatallasServices {

    @Autowired
    private BatallasRepositories batallasRepositories;

    public List<Batallas> obtenerBatallasPorTipo(String tipoDeBatalla) {
        // Obtener batallas desde el repositorio filtradas por el tipo de batalla
        List<Batallas> batallas = batallasRepositories.findByTipoDeBatallaIgnoreCase(tipoDeBatalla);

        // Mapear los resultados, asegurándose de que los campos fecha y horaCol se mantengan correctamente
        return batallas.stream()
            .map(batalla -> new Batallas(
                batalla.getId(),
                batalla.getFecha(),        // Fecha separada
                batalla.getHoraCol(),      // Hora separada
                batalla.getAgencias(),
                batalla.getUsuarios(),
                batalla.getObjetivo(),
                batalla.getRonda(),
                batalla.getObservaciones(),
                batalla.getReto(),
                batalla.getTipoDeBatalla(),
                batalla.getActual_id(),
                batalla.getUnirse()
            ))
            .collect(Collectors.toList()); // Uso de collect para almacenar correctamente los resultados
    }

    public Batallas insertarBatalla(Batallas batalla) {
        // Guardar la batalla con fecha y hora separadas
        return batallasRepositories.save(batalla);
    }
}
