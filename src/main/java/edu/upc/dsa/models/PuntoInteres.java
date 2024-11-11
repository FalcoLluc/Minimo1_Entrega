package edu.upc.dsa.models;

public class PuntoInteres {
    private int horizontal;
    private int vertical;
    ElementType tipo;

    public PuntoInteres(int horizontal, int vertical, ElementType tipo) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.tipo = tipo;
    }
    public PuntoInteres(){}

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public ElementType getTipo() {
        return tipo;
    }

    public void setTipo(ElementType tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Punto [hor="+this.horizontal+", ver=" + this.vertical + ", tipo=" +this.tipo +"]";
    }
}
