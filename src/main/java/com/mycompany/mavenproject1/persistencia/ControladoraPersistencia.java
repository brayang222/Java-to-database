
package com.mycompany.mavenproject1.persistencia;

import com.mycompany.mavenproject1.logica.Alumno;
import com.mycompany.mavenproject1.logica.Carrera;
import com.mycompany.mavenproject1.logica.Materia;
import com.mycompany.mavenproject1.logica.Profesor;
import com.mycompany.mavenproject1.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladoraPersistencia {
    
    AlumnoJpaController aluJpa = new AlumnoJpaController();
    CarreraJpaController carreraJpa = new CarreraJpaController();
    MateriaJpaController materiaJpa = new MateriaJpaController();
    ProfesorJpaController profesorJpa = new ProfesorJpaController();

    
    public void crearAlumno (Alumno alumno) {
        aluJpa.create(alumno);
    }

    public void eliminarAlumno(int id) {
        try {
            aluJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarAlumno(Alumno alumno) {
        try {
            aluJpa.edit(alumno);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Alumno traerAlumno(int id) {
        return aluJpa.findAlumno(id);
    }
    
    public ArrayList<Alumno> traerListaAlumnos(){
        List<Alumno> lista = aluJpa.findAlumnoEntities();
        ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno> (lista);
        return listaAlumnos;
    }

    // CARRERA
    
    public void crearCarrera(Carrera carrera) {
        carreraJpa.create(carrera);
    }

    public ArrayList<Carrera> traerListaCarreras() {
       List<Carrera> lista = aluJpa.findCarreraEntities();
        ArrayList<Carrera> listaCarreras = new ArrayList<Carrera> (lista);
        return listaCarreras;
    }

    public Carrera traerCarrera(int id) {
        return carreraJpa.findCarrera(id);
    }

    public void editarCarrera(Carrera carrera) {
        try {
            carreraJpa.edit(carrera);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarCarrera(int id) {
        try {
            carreraJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // MATERIA

    public void crearMateria(Materia materia) {
               materiaJpa.create(materia);
    }

    public void eliminarMateria(int id) {
        try {
            materiaJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarMateria(Materia materia) {
        try {
            materiaJpa.edit(materia);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Materia traerMateria(int id) {
        return materiaJpa.findMateria(id);
    }

    public LinkedList<Materia> traerListaMaterias() {
        List<Materia> lista = materiaJpa.findMateriaEntities();
        LinkedList<Materia> listaMaterias = new LinkedList<Materia> (lista);
        return listaMaterias;
    }
    
    // PROFESOR

    public void crearProfesor(Profesor profesor) {
        profesorJpa.create(profesor);
    }

    public void eliminarProfesor(int id) {
        try {
            profesorJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarProfesor(Profesor profesor) {
        try {
            profesorJpa.edit(profesor);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Profesor traerProfesor(int id) {
        return profesorJpa.findProfesor(id);
    }

    public LinkedList<Profesor> traerListaProfesores() {
        List<Profesor> lista = profesorJpa.findProfesorEntities();
        LinkedList<Profesor> listaProfesores = new LinkedList<Profesor> (lista);
        return listaProfesores;
    }
    
}
