
package com.mycompany.mavenproject1.logica;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Carrera implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String nombre;
    
    @OneToMany (mappedBy="carrera")
    private LinkedList<Materia> listaMaterias;
    
    @ManyToOne
    private Profesor profesor;

    public Carrera() {
    }

    public Carrera(int id, String nombre, LinkedList<Materia> listaMaterias, Profesor profesor) {
        this.id = id;
        this.nombre = nombre;
        this.listaMaterias = listaMaterias;
        this.profesor = profesor;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public LinkedList<Materia> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(LinkedList<Materia> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
