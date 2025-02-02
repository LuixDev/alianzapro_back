package com.backend.alianzapro.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "unirbatallas")
public class UnirBatallas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id; // ID único para la batalla

    private String agencia; // Nombre o código de la agencia
    private int ronda; // Número de la ronda
    private String puntos;  // Puntos obtenidos
    private String reto; // Descripción del reto
    private String observaciones; // Observaciones adicionales
    private Long actualId; // ID actual asociado
    private Long idBatalla; // ID de la batalla específica
    private boolean enviarCorreoBoth; // Indicador de envío de correo
    private Long hostId; // Puede ser nulo

    // Constructor vacío requerido por JPA
    public UnirBatallas() {
    }

    // Constructor con parámetros
    public UnirBatallas(String agencia, int ronda, Long hostId,String puntos, String reto, String observaciones, Long actualId, Long idBatalla, boolean enviarCorreoBoth) {
        this.agencia = agencia;
        this.ronda = ronda;
        this.puntos = puntos;
        this.reto = reto;
        this.observaciones = observaciones;
        this.actualId = actualId;
        this.idBatalla = idBatalla;
        this.enviarCorreoBoth = enviarCorreoBoth;
        this.hostId = hostId;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getReto() {
        return reto;
    }

    public void setReto(String reto) {
        this.reto = reto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getActualId() {
        return actualId;
    }

    public void setActualId(Long actualId) {
        this.actualId = actualId;
    }

    public Long getIdBatalla() {
        return idBatalla;
    }

    public void setIdBatalla(Long idBatalla) {
        this.idBatalla = idBatalla;
    }

    public boolean isEnviarCorreoBoth() {
        return enviarCorreoBoth;
    }

    public void setEnviarCorreoBoth(boolean enviarCorreoBoth) {
        this.enviarCorreoBoth = enviarCorreoBoth;
    }
    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

}


