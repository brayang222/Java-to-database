/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.persistencia;

import com.mycompany.mavenproject1.logica.Carrera;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.mavenproject1.logica.Profesor;
import com.mycompany.mavenproject1.logica.Materia;
import com.mycompany.mavenproject1.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CarreraJpaController implements Serializable {

    public CarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CarreraJpaController() {
        emf = Persistence.createEntityManagerFactory("pruebaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) {
        if (carrera.getListaMaterias() == null) {
            carrera.setListaMaterias(new LinkedList<Materia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesor profesor = carrera.getProfesor();
            if (profesor != null) {
                profesor = em.getReference(profesor.getClass(), profesor.getId());
                carrera.setProfesor(profesor);
            }
            LinkedList<Materia> attachedListaMaterias = new LinkedList<Materia>();
            for (Materia listaMateriasMateriaToAttach : carrera.getListaMaterias()) {
                listaMateriasMateriaToAttach = em.getReference(listaMateriasMateriaToAttach.getClass(), listaMateriasMateriaToAttach.getId());
                attachedListaMaterias.add(listaMateriasMateriaToAttach);
            }
            carrera.setListaMaterias(attachedListaMaterias);
            em.persist(carrera);
            if (profesor != null) {
                profesor.getListaCarreras().add(carrera);
                profesor = em.merge(profesor);
            }
            for (Materia listaMateriasMateria : carrera.getListaMaterias()) {
                Carrera oldCarreraOfListaMateriasMateria = listaMateriasMateria.getCarrera();
                listaMateriasMateria.setCarrera(carrera);
                listaMateriasMateria = em.merge(listaMateriasMateria);
                if (oldCarreraOfListaMateriasMateria != null) {
                    oldCarreraOfListaMateriasMateria.getListaMaterias().remove(listaMateriasMateria);
                    oldCarreraOfListaMateriasMateria = em.merge(oldCarreraOfListaMateriasMateria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrera carrera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getId());
            Profesor profesorOld = persistentCarrera.getProfesor();
            Profesor profesorNew = carrera.getProfesor();
            LinkedList<Materia> listaMateriasOld = persistentCarrera.getListaMaterias();
            LinkedList<Materia> listaMateriasNew = carrera.getListaMaterias();
            if (profesorNew != null) {
                profesorNew = em.getReference(profesorNew.getClass(), profesorNew.getId());
                carrera.setProfesor(profesorNew);
            }
            LinkedList<Materia> attachedListaMateriasNew = new LinkedList<Materia>();
            for (Materia listaMateriasNewMateriaToAttach : listaMateriasNew) {
                listaMateriasNewMateriaToAttach = em.getReference(listaMateriasNewMateriaToAttach.getClass(), listaMateriasNewMateriaToAttach.getId());
                attachedListaMateriasNew.add(listaMateriasNewMateriaToAttach);
            }
            listaMateriasNew = attachedListaMateriasNew;
            carrera.setListaMaterias(listaMateriasNew);
            carrera = em.merge(carrera);
            if (profesorOld != null && !profesorOld.equals(profesorNew)) {
                profesorOld.getListaCarreras().remove(carrera);
                profesorOld = em.merge(profesorOld);
            }
            if (profesorNew != null && !profesorNew.equals(profesorOld)) {
                profesorNew.getListaCarreras().add(carrera);
                profesorNew = em.merge(profesorNew);
            }
            for (Materia listaMateriasOldMateria : listaMateriasOld) {
                if (!listaMateriasNew.contains(listaMateriasOldMateria)) {
                    listaMateriasOldMateria.setCarrera(null);
                    listaMateriasOldMateria = em.merge(listaMateriasOldMateria);
                }
            }
            for (Materia listaMateriasNewMateria : listaMateriasNew) {
                if (!listaMateriasOld.contains(listaMateriasNewMateria)) {
                    Carrera oldCarreraOfListaMateriasNewMateria = listaMateriasNewMateria.getCarrera();
                    listaMateriasNewMateria.setCarrera(carrera);
                    listaMateriasNewMateria = em.merge(listaMateriasNewMateria);
                    if (oldCarreraOfListaMateriasNewMateria != null && !oldCarreraOfListaMateriasNewMateria.equals(carrera)) {
                        oldCarreraOfListaMateriasNewMateria.getListaMaterias().remove(listaMateriasNewMateria);
                        oldCarreraOfListaMateriasNewMateria = em.merge(oldCarreraOfListaMateriasNewMateria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = carrera.getId();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            Profesor profesor = carrera.getProfesor();
            if (profesor != null) {
                profesor.getListaCarreras().remove(carrera);
                profesor = em.merge(profesor);
            }
            LinkedList<Materia> listaMaterias = carrera.getListaMaterias();
            for (Materia listaMateriasMateria : listaMaterias) {
                listaMateriasMateria.setCarrera(null);
                listaMateriasMateria = em.merge(listaMateriasMateria);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Carrera findCarrera(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
