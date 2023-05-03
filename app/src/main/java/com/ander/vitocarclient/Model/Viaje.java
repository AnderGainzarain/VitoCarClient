package com.ander.vitocarclient.Model;

public class Viaje {
    // Atributes
    private Integer idViaje;
    private int precio;
    private String origen;
    private String destino;
    private String fechaSalida;
    private User conductor;

    private User usuarios2;

    // Getters and setters
    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
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

    public User getConductor() {
        return conductor;
    }

    public void setConductor(User conductor) {
        this.conductor = conductor;
    }

    public User getUsuarios2() {
        return usuarios2;
    }

    public void setUsuarios2(User usuarios2) {
        this.usuarios2 = usuarios2;
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
    public boolean equals(Viaje viaje){
        return viaje.getOrigen().equals(origen) ||
                viaje.getDestino().equals(destino) ||
                viaje.getFechaSalida().equals(fechaSalida);
    }
}
