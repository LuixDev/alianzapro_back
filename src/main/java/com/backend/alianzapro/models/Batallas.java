package com.backend.alianzapro.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "batallas")
public class Batallas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime horaCol; // Hora separada

    @Column(nullable = false)
    private String agencias;

    @Column(nullable = false)
    private String usuarios;

    @Column(nullable = false)
    private String objetivo;

    @Column(nullable = false)
    private Integer ronda;

    @Column
    private String observaciones;

    @Column
    private String reto;

    @Column(nullable = false)
    private String tipoDeBatalla;


    private Long actual_id;

    @Column(nullable = false)
    private Boolean unirse; // Indica si se permite unirse

    public Batallas() {}

    // Constructor con todos los campos
    public Batallas(Long id, LocalDate fecha, LocalTime horaCol, String agencias, String usuarios, 
                    String objetivo, Integer ronda, String observaciones, String reto, 
                    String tipoDeBatalla, Long actual_id,  Boolean unirse) {
        this.id = id;
        this.fecha = fecha;
        this.horaCol = horaCol;
        this.agencias = agencias;
        this.usuarios = usuarios;
        this.objetivo = objetivo;
        this.ronda = ronda;
        this.observaciones = observaciones;
        this.reto = reto;
        this.tipoDeBatalla = tipoDeBatalla;
        this.actual_id = actual_id;
        this.unirse = unirse;
    }

    // Constructor parcial
    public Batallas(LocalDate fecha, LocalTime horaCol, String agencias, String usuarios, String objetivo, 
                    String tipoDeBatalla, Long actual_id) {
        this.fecha = fecha;
        this.horaCol = horaCol;
        this.agencias = agencias;
        this.usuarios = usuarios;
        this.objetivo = objetivo;
        this.tipoDeBatalla = tipoDeBatalla;
        this.actual_id = actual_id;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraCol() {
        return horaCol;
    }

    public void setHoraCol(LocalTime horaCol) {
        this.horaCol = horaCol;
    }

    public String getAgencias() {
        return agencias;
    }

    public void setAgencias(String agencias) {
        this.agencias = agencias;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Integer getRonda() {
        return ronda;
    }

    public void setRonda(Integer ronda) {
        this.ronda = ronda;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getReto() {
        return reto;
    }

    public void setReto(String reto) {
        this.reto = reto;
    }

    public String getTipoDeBatalla() {
        return tipoDeBatalla;
    }

    public void setTipoDeBatalla(String tipoDeBatalla) {
        this.tipoDeBatalla = tipoDeBatalla;
    }

    public Long getActual_id() {
        return actual_id;
    }

    public void setActual_id(Long actual_id) {
        this.actual_id = actual_id;
    }

    public Boolean getUnirse() {
        return unirse;
    }

    public void setUnirse(Boolean unirse) {
        this.unirse = unirse;
    }
}
