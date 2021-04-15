package com.accounting.accountingrest.hibernate.repository;


import com.accounting.accountingrest.hibernate.model.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class ExpenseHibController {

    EntityManagerFactory entityManagerFactory = null;

    public ExpenseHibController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void create(Expense expense) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(expense));
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Expense> getExpenseList() {
        return getExpenseList(true, -1, -1);
    }

    public List<Expense> getExpenseList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {

            CriteriaQuery<Object> criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Expense.class));
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

    public Expense getById(int id) {
        for (Expense expense : getExpenseList()) {
            if (expense.getId() == id) return expense;
        }
        return null;
    }

    public void update(Expense expense) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            expense = entityManager.merge(expense);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error when updating");
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
            Expense expense = null;

            try {
                expense = em.getReference(Expense.class, id);
                expense.getId();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id not found");
            }
            em.remove(expense);
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
