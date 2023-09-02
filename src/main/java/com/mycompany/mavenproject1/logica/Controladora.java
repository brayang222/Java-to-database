
package com.mycompany.mavenproject1.logica;

import com.mycompany.mavenproject1.persistencia.ControladoraPersistencia;
import java.util.ArrayList;
import java.util.LinkedList;

public class Controladora {
    
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    
    public void crearAlumno(Alumno alumno) {
        
        controlPersis.crearAlumno (alumno);
        
    }
    
    public void eliminarAlumno(int id){
        
        controlPersis.eliminarAlumno(id);
        
    }
    
    public void editarAlumno(Alumno alumno){
        controlPersis.editarAlumno(alumno);
    }
    
    public Alumno traerAlumno(int id){
        return controlPersis.traerAlumno(id);
    }
    
    public ArrayList<Alumno> traerListaAlumnos(){
        
                return controlPersis.traerListaAlumnos();

    }
    
    // CARRERA
    
    public void crearCarrera(Carrera carrera) {
        
        controlPersis.crearCarrera (carrera);
        
    }
    
    public void eliminarCarrera(int id){
        
        controlPersis.eliminarCarrera(id);
        
    }
    
    public void editarCarrera(Carrera carrera){
        controlPersis.editarCarrera(carrera);
    }
    
    public Carrera traerCarrera(int id){
        return controlPersis.traerCarrera(id);
    }
    
    public ArrayList<Carrera> traerListaCarreras(){
        
                return controlPersis.traerListaCarreras();

    }
    
    // MATERIA

    public void crearMateria(Materia materia) {
        
        controlPersis.crearMateria (materia);
        
    }
    
    public void eliminarMateria(int id){
        
        controlPersis.eliminarMateria(id);
        
    }
    
    public void editarMateria(Materia materia){
        controlPersis.editarMateria(materia);
    }
    
    public Materia traerMateria(int id){
        return controlPersis.traerMateria(id);
    }
    
    public LinkedList<Materia> traerListaMaterias(){
        
        return controlPersis.traerListaMaterias();

    }
    
    // PROFESOR 
    
     public void crearProfesor(Profesor profesor) {
        
        controlPersis.crearProfesor (profesor);
        
    }
    
    public void eliminarProfesor(int id){
        
        controlPersis.eliminarProfesor(id);
        
    }
    
    public void editarProfesor(Profesor profesor){
        controlPersis.editarProfesor(profesor);
    }
    
    public Profesor traerProfesor(int id){
        return controlPersis.traerProfesor(id);
    }
    
    public LinkedList<Profesor> traerListaProfesores(){
        
        return controlPersis.traerListaProfesores();

    }
    
}
