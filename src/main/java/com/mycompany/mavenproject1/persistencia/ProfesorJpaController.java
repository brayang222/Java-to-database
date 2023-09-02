/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.mavenproject1.logica.Carrera;
import com.mycompany.mavenproject1.logica.Profesor;
import com.mycompany.mavenproject1.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProfesorJpaController implements Serializable {

    public ProfesorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ProfesorJpaController() {
        emf = Persistence.createEntityManagerFactory("pruebaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profesor profesor) {
        if (profesor.getListaCarreras() == null) {
            profesor.setListaCarreras(new LinkedList<Carrera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinkedList<Carrera> attachedListaCarreras = new LinkedList<Carrera>();
            for (Carrera listaCarrerasCarreraToAttach : profesor.getListaCarreras()) {
                listaCarrerasCarreraToAttach = em.getReference(listaCarrerasCarreraToAttach.getClass(), listaCarrerasCarreraToAttach.getId());
                attachedListaCarreras.add(listaCarrerasCarreraToAttach);
            }
            profesor.setListaCarreras(attachedListaCarreras);
            em.persist(profesor);
            for (Carrera listaCarrerasCarrera : profesor.getListaCarreras()) {
                Profesor oldProfesorOfListaCarrerasCarrera = listaCarrerasCarrera.getProfesor();
                listaCarrerasCarrera.setProfesor(profesor);
                listaCarrerasCarrera = em.merge(listaCarrerasCarrera);
                if (oldProfesorOfListaCarrerasCarrera != null) {
                    oldProfesorOfListaCarrerasCarrera.getListaCarreras().remove(listaCarrerasCarrera);
                    oldProfesorOfListaCarrerasCarrera = em.merge(oldProfesorOfListaCarrerasCarrera);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profesor profesor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesor persistentProfesor = em.find(Profesor.class, profesor.getId());
            LinkedList<Carrera> listaCarrerasOld = persistentProfesor.getListaCarreras();
            LinkedList<Carrera> listaCarrerasNew = profesor.getListaCarreras();
            LinkedList<Carrera> attachedListaCarrerasNew = new LinkedList<Carrera>();
            for (Carrera listaCarrerasNewCarreraToAttach : listaCarrerasNew) {
                listaCarrerasNewCarreraToAttach = em.getReference(listaCarrerasNewCarreraToAttach.getClass(), listaCarrerasNewCarreraToAttach.getId());
                attachedListaCarrerasNew.add(listaCarrerasNewCarreraToAttach);
            }
            listaCarrerasNew = attachedListaCarrerasNew;
            profesor.setListaCarreras(listaCarrerasNew);
            profesor = em.merge(profesor);
            for (Carrera listaCarrerasOldCarrera : listaCarrerasOld) {
                if (!listaCarrerasNew.contains(listaCarrerasOldCarrera)) {
                    listaCarrerasOldCarrera.setProfesor(null);
                    listaCarrerasOldCarrera = em.merge(listaCarrerasOldCarrera);
                }
            }
            for (Carrera listaCarrerasNewCarrera : listaCarrerasNew) {
                if (!listaCarrerasOld.contains(listaCarrerasNewCarrera)) {
                    Profesor oldProfesorOfListaCarrerasNewCarrera = listaCarrerasNewCarrera.getProfesor();
                    listaCarrerasNewCarrera.setProfesor(profesor);
                    listaCarrerasNewCarrera = em.merge(listaCarrerasNewCarrera);
                    if (oldProfesorOfListaCarrerasNewCarrera != null && !oldProfesorOfListaCarrerasNewCarrera.equals(profesor)) {
                        oldProfesorOfListaCarrerasNewCarrera.getListaCarreras().remove(listaCarrerasNewCarrera);
                        oldProfesorOfListaCarrerasNewCarrera = em.merge(oldProfesorOfListaCarrerasNewCarrera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = profesor.getId();
                if (findProfesor(id) == null) {
                    throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.");
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
            Profesor profesor;
            try {
                profesor = em.getReference(Profesor.class, id);
                profesor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.", enfe);
            }
            LinkedList<Carrera> listaCarreras = profesor.getListaCarreras();
            for (Carrera listaCarrerasCarrera : listaCarreras) {
                listaCarrerasCarrera.setProfesor(null);
                listaCarrerasCarrera = em.merge(listaCarrerasCarrera);
            }
            em.remove(profesor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Profesor> findProfesorEntities() {
        return findProfesorEntities(true, -1, -1);
    }

    public List<Profesor> findProfesorEntities(int maxResults, int firstResult) {
        return findProfesorEntities(false, maxResults, firstResult);
    }

    private List<Profesor> findProfesorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profesor.class));
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

    public Profesor findProfesor(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profesor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfesorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profesor> rt = cq.from(Profesor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
