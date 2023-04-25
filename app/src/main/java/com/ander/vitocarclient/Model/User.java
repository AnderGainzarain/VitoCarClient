package com.ander.vitocarclient.Model;

import java.util.List;

public class User {
    // Atributes
    private Integer dni;
    private int telefono;
    private String contraseña;
    private String mail;
    private String nombre;
    private String apellido;
    private String foto;
    private String coche;
    private List<Viaje> viajes;
    private List<Viaje> viajes2;

    // Getters and setters
    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCoche() {
        return coche;
    }

    public void setCoche(String coche) {
        this.coche = coche;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes;
    }

    public List<Viaje> getViajes2() {
        return viajes2;
    }

    public void setViajes2(List<Viaje> viajes2) {
        this.viajes2 = viajes2;
    }
}
