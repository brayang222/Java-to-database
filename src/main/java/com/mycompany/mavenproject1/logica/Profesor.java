
package com.mycompany.mavenproject1.logica;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Profesor implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String Name;
    
    @OneToMany (mappedBy="profesor")
    private LinkedList<Carrera> listaCarreras;

    public Profesor() {
    }

    public Profesor(int id, String Name, LinkedList<Carrera> listaCarreras) {
        this.id = id;
        this.Name = Name;
        this.listaCarreras = listaCarreras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public LinkedList<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(LinkedList<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }
    
    
    
}
