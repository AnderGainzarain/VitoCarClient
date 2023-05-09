package com.ander.vitocarclient.Model;

public class Viaje {
    // Atributes
    private Integer idViaje;
    private int precio;
    private String origen;
    private String destino;
    private String fechaSalida;

    // Getters and setters
    public int getIdViaje() {
        return idViaje;
    }

    public int getPrecio() {
        return precio;
    }


    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public String getPrecioString(){
        return String.valueOf(precio);
    }

    public Viaje(int precio, String origen, String destino, String fechaSalida) {
        this.precio = precio;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
    }
}
