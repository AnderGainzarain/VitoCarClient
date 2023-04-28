package com.ander.vitocarclient.Model;


public class ActiveUser {
    // Atributes
    private final Integer dni;
    private final int telefono;
    private final String mail;
    private final String nombre;
    private final String apellido;
    private final String foto;
    private final String coche;
    private static ActiveUser activeUser;

    //Constructor
    private ActiveUser(Integer dni, int telefono, String mail, String nombre, String apellido, String foto, String coche){
        this.dni = dni;
        this.telefono = telefono;
        this.mail = mail;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.coche = coche;
    }

    public static void initialize(User user) {
        activeUser = new ActiveUser(user.getDni(),user.getTelefono(),user.getMail(),user.getNombre(),user.getApellido(),user.getFoto(),user.getCoche());
    }
    public static void logOut(){
        activeUser = null;
    }

    public Integer getDNI() {
        return dni;
    }

    public int getTelefono() {
        return telefono;
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

    public static ActiveUser getActiveUser() {
        if(activeUser==null){
            activeUser = new ActiveUser(1111,1,"a@a","a","apellido1","https://www.w3schools.com/howto/img_avatar2.png","coche1");

        }
        return activeUser;
    }
}
