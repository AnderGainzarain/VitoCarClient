package com.ander.vitocarclient.Model;

import java.util.List;

public class User {
    // Atributes
    private Integer DNI;
    private int telefono;
    private String Contraseña;
    private String mail;
    private String nombre;
    private String apellido;
    private String foto;
    private String Coche;
    private List<Viaje> viajes;
    private List<Viaje> viajes2;

    // Getters and setters
    public Integer getDNI() {
        return DNI;
    }

    public void setDNI(Integer DNI) {
        this.DNI = DNI;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
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
        return Coche;
    }

    public void setCoche(String coche) {
        Coche = coche;
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
