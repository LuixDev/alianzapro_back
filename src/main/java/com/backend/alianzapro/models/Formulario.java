package com.backend.alianzapro.models;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "formulario")
public class Formulario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único para la entidad

    // Datos de la cuenta
    @NotEmpty(message = "El nombre de cuenta es obligatorio")
    private String nombreCuenta;

    @NotEmpty(message = "La contraseña es obligatoria")
    private String contrasena;

    // Datos del encargado
    @Email(message = "Debe ingresar un email válido")
    @NotEmpty(message = "El email es obligatorio")
    private String email;

    @NotEmpty(message = "El nombre del encargado es obligatorio")
    private String nombres;

    @NotEmpty(message = "El apellido del encargado es obligatorio")
    private String apellidos;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    // Datos de la agencia
    @NotEmpty(message = "El nombre de la agencia es obligatorio")
    private String nombreAgencia;

    private String imagenAgencia; // Ruta de la imagen de la agencia

    private String emoji;

    @NotEmpty(message = "El número de celular es obligatorio")
    private String celular;

    private String color;

    @NotEmpty(message = "Debe especificar la razón para unirse")
    private String razonUnirse;

    // Atributos adicionales
    private Boolean aceptado;
    private Boolean rechazado; // Nuevo atributo para "rechazado"

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreAgencia() {
        return nombreAgencia;
    }

    public void setNombreAgencia(String nombreAgencia) {
        this.nombreAgencia = nombreAgencia;
    }

    public String getImagenAgencia() {
        return imagenAgencia;
    }

    public void setImagenAgencia(String imagenAgencia) {
        this.imagenAgencia = imagenAgencia;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRazonUnirse() {
        return razonUnirse;
    }

    public void setRazonUnirse(String razonUnirse) {
        this.razonUnirse = razonUnirse;
    }

    public Boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(Boolean aceptado) {
        this.aceptado = aceptado;
    }

    public Boolean getRechazado() {
        return rechazado;
    }

    public void setRechazado(Boolean rechazado) {
        this.rechazado = rechazado;
    }
}
