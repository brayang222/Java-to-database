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
import com.mycompany.mavenproject1.logica.Materia;
import com.mycompany.mavenproject1.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MateriaJpaController implements Serializable {

    public MateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public MateriaJpaController() {
        emf = Persistence.createEntityManagerFactory("pruebaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materia materia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera carrera = materia.getCarrera();
            if (carrera != null) {
                carrera = em.getReference(carrera.getClass(), carrera.getId());
                materia.setCarrera(carrera);
            }
            em.persist(materia);
            if (carrera != null) {
                carrera.getListaMaterias().add(materia);
                carrera = em.merge(carrera);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materia materia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia persistentMateria = em.find(Materia.class, materia.getId());
            Carrera carreraOld = persistentMateria.getCarrera();
            Carrera carreraNew = materia.getCarrera();
            if (carreraNew != null) {
                carreraNew = em.getReference(carreraNew.getClass(), carreraNew.getId());
                materia.setCarrera(carreraNew);
            }
            materia = em.merge(materia);
            if (carreraOld != null && !carreraOld.equals(carreraNew)) {
                carreraOld.getListaMaterias().remove(materia);
                carreraOld = em.merge(carreraOld);
            }
            if (carreraNew != null && !carreraNew.equals(carreraOld)) {
                carreraNew.getListaMaterias().add(materia);
                carreraNew = em.merge(carreraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = materia.getId();
                if (findMateria(id) == null) {
                    throw new NonexistentEntityException("The materia with id " + id + " no longer exists.");
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
            Materia materia;
            try {
                materia = em.getReference(Materia.class, id);
                materia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materia with id " + id + " no longer exists.", enfe);
            }
            Carrera carrera = materia.getCarrera();
            if (carrera != null) {
                carrera.getListaMaterias().remove(materia);
                carrera = em.merge(carrera);
            }
            em.remove(materia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materia> findMateriaEntities() {
        return findMateriaEntities(true, -1, -1);
    }

    public List<Materia> findMateriaEntities(int maxResults, int firstResult) {
        return findMateriaEntities(false, maxResults, firstResult);
    }

    private List<Materia> findMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materia.class));
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

    public Materia findMateria(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materia> rt = cq.from(Materia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
