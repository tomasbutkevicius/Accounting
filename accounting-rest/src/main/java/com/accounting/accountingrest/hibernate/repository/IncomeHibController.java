package com.accounting.accountingrest.hibernate.repository;


import com.accounting.accountingrest.hibernate.model.Income;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class IncomeHibController {

    EntityManagerFactory entityManagerFactory = null;

    public IncomeHibController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void create(Income income) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(income));
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server encountered a problem");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Income> getIncomeList() {
        return getIncomeList(true, -1, -1);
    }

    public List<Income> getIncomeList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {

            CriteriaQuery<Object> criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Income.class));
            Query query = entityManager.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server encountered a problem");
        } finally {
                entityManager.close();
        }
    }

    public Income getById(int id) {
        for (Income income : getIncomeList()) {
            if (income.getId() == id) return income;
        }
        return null;
    }

    public void update(Income income) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            income = entityManager.merge(income);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server encountered a problem");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void delete(int id) {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Income income = null;

            try {
                income = em.getReference(Income.class, id);
                income.getId();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server encountered a problem");
            }
            em.remove(income);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
