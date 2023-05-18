package com.ander.vitocarclient.Model;


import com.ander.vitocarclient.Vista.TextControll;

public class ActiveUser {
    // Atributes
    private static Integer dni;
    private static int telefono;
    private static String mail;
    private static String nombre;
    private static String apellido;
    private static String foto;
    private final String coche;
    private static ActiveUser activeUser;

    //Constructor
    private ActiveUser(Integer dni, int telefono, String mail, String nombre, String apellido, String foto, String coche){
        ActiveUser.dni = dni;
        ActiveUser.telefono = telefono;
        ActiveUser.mail = mail;
        ActiveUser.nombre = nombre;
        ActiveUser.apellido = apellido;
        ActiveUser.foto = foto;
        if(coche.equals("")){
            coche = TextControll.cocheVacio();
        }
        this.coche = coche;
    }

    public static void initialize(User user) {
        activeUser = new ActiveUser(user.getDni(),user.getTelefono(),user.getMail(),user.getNombre(),user.getApellido(),user.getFoto(),user.getCoche());
    }
    public static void logOut(){
        activeUser = null;
        dni = null;
        telefono = 0;
        mail = null;
        nombre = null;
        apellido = null;
        foto = null;
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
        return activeUser;
    }
}
