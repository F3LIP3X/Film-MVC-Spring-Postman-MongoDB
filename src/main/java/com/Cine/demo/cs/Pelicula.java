package com.Cine.demo.cs;

import java.io.Serializable;
import java.util.Date;

public class Pelicula implements Serializable{
	/**
	 * Felipe | Rafa | @version1.0.0
	 */
	private static final long serialVersionUID = 1L;
	
	
    private String Nombre;
    private int AñoLanzamiento;
    private int Duracion;
    private int Nota;
    
    public Pelicula(String Nombre, int AñoLanzamiento, int Duracion, int Nota) {
        this.Nombre = Nombre;
        this.AñoLanzamiento = AñoLanzamiento;
        this.Duracion = Duracion;
        this.Nota = Nota;
    }


    public Pelicula() {
        this.Nombre="";
        this.AñoLanzamiento= 0;
        this.Duracion= 0;
        this.Nota= 0;
    }


	public String getNombre() {
		return Nombre;
	}


	public void setNombre(String nombre) {
		Nombre = nombre;
	}


	public int getAñoLanzamiento() {
		return AñoLanzamiento;
	}


	public void setAñoLanzamiento(int añoLanzamiento) {
		AñoLanzamiento = añoLanzamiento;
	}


	public int getDuracion() {
		return Duracion;
	}


	public void setDuracion(int duracion) {
		Duracion = duracion;
	}


	public int getNota() {
		return Nota;
	}


	public void setNota(int nota) {
		Nota = nota;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}