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
    public static void initialize(User user){
            DNI = user.getDNI();
            telefono = user.getTelefono();
            mail = user.getMail();
            nombre = user.getNombre();
            apellido = user.getApellido();
            foto = user.getFoto();
            Coche = user.getCoche();
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
