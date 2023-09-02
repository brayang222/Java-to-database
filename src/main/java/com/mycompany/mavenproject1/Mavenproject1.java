package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.logica.Alumno;
import com.mycompany.mavenproject1.logica.Carrera;
import com.mycompany.mavenproject1.logica.Controladora;
import com.mycompany.mavenproject1.logica.Materia;
import com.mycompany.mavenproject1.logica.Profesor;
import java.util.Date;
import java.util.LinkedList;

public class Mavenproject1 {

    public static void main(String[] args) {

        Controladora control = new Controladora();
        
        //Crear lista de carrera
        LinkedList<Carrera> listaCarreras = new LinkedList<>();
        
        //Crear profesor
        Profesor profesor = new Profesor(1, "Paco", listaCarreras);
        
        //Guardar profesor
        control.crearProfesor(profesor);
        
        // Crear lista de materia
        LinkedList<Materia> listaMaterias = new LinkedList<>();
        
         // Crear carrera
        Carrera carrera = new Carrera(1, "Ingeniero", listaMaterias, profesor);
        
        // Guardar carrera en BD
        control.crearCarrera(carrera);
        
        // agregar a la lista
        listaCarreras.add(carrera);
        
        // Crear Materias
        Materia materia1 = new Materia(1, "Programacion", "trimestral", carrera);
        Materia materia2 = new Materia(2, "Diseño web", "cuatrimestral", carrera);
        Materia materia3 = new Materia(3, "Base de datos", "anual", carrera);

        //Guardar Materias
        control.crearMateria(materia1);
        control.crearMateria(materia2);
        control.crearMateria(materia3);

        //agregar a la lista
        listaMaterias.add(materia1);
        listaMaterias.add(materia2);
        listaMaterias.add(materia3);
        
        profesor.setListaCarreras(listaCarreras);
        control.editarProfesor(profesor);
        carrera.setListaMaterias(listaMaterias);
        control.editarCarrera(carrera);
        
        // Crear Alumno ( con carrera y profesor)
        Alumno alumno = new Alumno(202, "Valenttina", "Quiroga", new Date(), carrera);

        // Guardar el alumno en la BD
        control.crearAlumno(alumno);
        
        // Ver resultado
        Alumno alum = control.traerAlumno(202);
        System.out.println("Datos alumno: " + alum.getNombre() + " " + alum.getApellido());
        System.out.println("Cursa la carrera de: " + alum.getCarrera().getNombre());
        
        
        
        
        /*Alumno alumno = new Alumno(202, "Valenttina", "Quiroga", new Date());
        control.crearAlumno(alumno); // Crear alumno
        
        //control.eliminarAlumno(202); // Eliminar alumno
        
        /*alumno.setApellido("Vasquez");
        control.editarAlumno(alumno); // Editar alumno 
        
        Alumno alumno = control.traerAlumno(1001);
        System.out.println("----------- Busqueda individual ------------------");
        System.out.println("El alumnos es: " + alumno.toString());
        System.out.println("----------- Busqueda de todos --------------------");
        ArrayList<Alumno> listaAlumnos = control.traerListaAlumnos();
        for (Alumno alum : listaAlumnos) {
            System.out.println("el alumno es: " + alum.toString());
        } */
    }
}
