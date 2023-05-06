package com.ander.vitocarclient.Vista;

public class TextControll {
    public static String getConectionErrorMsg(){
        return "Ha ocurrido un error de conexion";
    }
    public static String noViajesPublicados(){return "No hay viajes publicados";}
    public static String noViajesReservados(){return "No has hecho ninguna reserva";}
    public static String noHayBusqueda(){return "no hay viajes que coincidan con los valores de busqueda";}
    public static String fechaVacia(){return "Introduzca una fecha, por favor";}
    public static String origenDestinoIguales(){return "El origen y el destino no pueden ser el mismo";}
    public static String precioMenorUno(){return "El coste no puede ser menor que 1€";}
    public static String precioVacio() {return "Por favor introduzca un precio";}
    public static String fechaPasada() {return "La fecha de salida ya ha pasado";}
    public static String viajePublicado() { return "Viaje publciado con exito";}
    public static String errorPublicar() {return "Error al publicar";}
    public static String mailIncorrecto() {return "El mail es incorrecto";}
    public static String pwdIncorrecto() {return "La contraseña es incorrecta";}
    public static String publicarNoLogueado() { return "Es necesario iniciar sesion para publicar un viaje";}
    public static String reservasNoLogueado() {return "Es necesario iniciar sesion para ver tus reservas";}
    public static String viajesNoLogueado() {return "Es necesario iniciar sesion para ver tus viajes publicados";}
    public static String viajeYaPublicado() { return "Ya has publicado un viaje con ese origen y destino para la misma hora";}
    public static String btnAnular() {return "anular";}
    public static String reservaRealizada() {return "Reserva realizada con exito";}
    public static String viajeAnulado() {return "Viaje anulado con exito";}
    public static String reservaAnulada(){return "Reserva anulada con exito";}

    public static String sesionIniciada() {return "Sesion iniciada";}

    public static String cocheVacio() {return "No hay coche registrado";}

    public static String btnReservar() { return "Reservar";}

    public static String noHayPasajeros() { return "Libre";}

    public static String tBuscarViaje() {return "Buscar Viaje";}

    public static String tPerfil() {return "Perfil";}

    public static String tLogIn() {return "Log In";}

    public static String tViajesPublicados() {return "Mis Viajes";}

    public static String tReservas() {return "Mis Reservas";}

    public static String tPublicarViaje() {return "Publicar Viaje";}
}
