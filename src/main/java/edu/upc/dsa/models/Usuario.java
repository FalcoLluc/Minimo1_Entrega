package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String fechaNacimiento;

    private List<PuntoInteres> puntosInteres;

    public Usuario(String id, String nombre, String apellidos, String correo, String fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        puntosInteres=new LinkedList<>();
    }

    public Usuario(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<PuntoInteres> getPuntosInteres() {
        return puntosInteres;
    }

    public void setPuntosInteres(List<PuntoInteres> puntosInteres) {
        this.puntosInteres = puntosInteres;
    }

    public void addPunto(PuntoInteres p){this.puntosInteres.add(p);}

    @Override
    public String toString() {
        return "Usuario [id="+this.id+", nombre=" + this.nombre + ", puntos=" +this.puntosInteres.size() +"]";
    }
}
