package com.ander.vitocarclient.Model;


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

    // Getters and setters
    public Integer getDni() {
        return dni;
    }


    public int getTelefono() {
        return telefono;
    }


    public String getContraseña() {
        return contraseña;
    }


    public String getMail() {
        return mail;
    }


    public String getNombre() {
        return nombre;
    }


    public String getApellido() {
        return apellido;
    }


    public String getFoto() {
        return foto;
    }


    public String getCoche() {
        return coche;
    }

    public User(Integer dni, int telefono, String contraseña, String mail, String nombre, String apellido, String foto, String coche) {
        this.dni = dni;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.mail = mail;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.coche = coche;
    }
}
