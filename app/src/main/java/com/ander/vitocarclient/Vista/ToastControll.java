package com.ander.vitocarclient.Vista;

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
    public static String fechaVacia(){return "Introduzca una fecha, por favor";}
    public static String origenDestinoIguales(){return "El origen y el destino no pueden ser el mismo";}
    public static String precioMenorUno(){return "El coste no puede ser menor que 1€";}

    public static String precioVacio() {return "Por favor introduzca un precio";}

    public static String fechaPasada() {return "La fecha de salida ya ha pasado";}

    public static String viajePublicado() { return "Viaje publciado con exito";}
    public static String errorPublicar() {return "Error al publicar";}
}
