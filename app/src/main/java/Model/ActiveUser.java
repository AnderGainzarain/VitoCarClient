package Model;

public class ActiveUser {
    // Atributes
    private static Integer DNI;
    private static int telefono;
    private static String mail;
    private static String nombre;
    private static String apellido;
    private static String foto;
    private static String Coche;
    private static ActiveUser activeUser;

    //Constructor
    private ActiveUser(){
    }
    public static void initialize(Integer dni, int ptelefono, String pmail, String pnombre, String papellido, String pfoto, String coche){
            DNI = dni;
            telefono = ptelefono;
            mail = pmail;
            nombre = pnombre;
            apellido = papellido;
            foto = pfoto;
            Coche = coche;
    }
    public Integer getDNI() {
        return DNI;
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
        return Coche;
    }

    public static ActiveUser getActiveUser() {
        return activeUser;
    }
}
