package Vista;

public class ToastControll {
    // Conection error
    public static String getConectionErrorMsg(){
        return "Ha ocurrido un error de conexion";
    }
    // there are no viajes
    public static String noViajesPublicados(){
        return "No hay viajes";
    }
    public static String noViajesReservados(){return "No has hecho ninguna reserva";}
    public static String noHayBusqueda(){return "no hay viajes que coincidan con los valores de busqueda";}

}
