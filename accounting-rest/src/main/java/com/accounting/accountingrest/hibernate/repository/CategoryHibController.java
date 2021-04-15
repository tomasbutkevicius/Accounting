package com.accounting.accountingrest.hibernate.repository;

import com.accounting.accountingrest.hibernate.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryHibController {

    EntityManagerFactory entityManagerFactory = null;

    public CategoryHibController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void create(Category category) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(category));
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server is experiencing a problem");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Category> getCategoryList(){
        return getCategoryList(true, -1, -1);
    }

    public List<Category> getCategoryList(boolean all, int maxRes, int firstRes){

        EntityManager entityManager = getEntityManager();
        try{

            CriteriaQuery<Object> criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Category.class));
            Query query = entityManager.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server is experiencing a problem");
        } finally {
                entityManager.close();
        }
    }

    public Category getById(int id) {
        for(Category category: getCategoryList()){
            if(category.getId() == id) return category;
        }
        return null;
    }

    public void update(Category category){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            category = entityManager.merge(category);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server is experiencing a problem");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void delete(int catId){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Category category = null;
            try{
                CategoryHibController categoryHibController = new CategoryHibController(entityManagerFactory);
                for(User user: categoryHibController.getById(catId).getResponsibleUsers()) {
                    categoryHibController.removeUserFromCategory(catId, user.getId());
                }
                category = entityManager.find(Category.class, catId);
            }catch(Exception e){
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server encountered a problem");
            }
            entityManager.remove(category);
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

    public List<Category> getAllCategoriesInSystem(AccountingSystem accountingSystem) {
        List<Category> categoriesInSystem = new ArrayList<>();

        for (Category category : getCategoryList()) {
            if (category.getAccountingSystem().getId() == accountingSystem.getId()){
                categoriesInSystem.add(category);
            }
        }
        return categoriesInSystem;
    }


    public void removeUserFromCategory(int categoryId, int userId) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                Category category = em.find(Category.class, categoryId);
                User user = em.find(User.class, userId);
                user.getCategories().remove(category);
                category.getResponsibleUsers().remove(user);
                em.getTransaction().commit();
            } catch (EntityNotFoundException enfe) {
                enfe.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not found");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void addUserToCategory(int categoryId, int userId) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                Category category = em.find(Category.class, categoryId);
                User user = em.find(User.class, userId);
                user.getCategories().add(category);
                category.getResponsibleUsers().add(user);
                em.getTransaction().commit();
            } catch (EntityNotFoundException enfe) {
                enfe.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not found");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeIncomeFromCategory(Category category, Income income) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                income.setCategory(null);
                category.getIncomes().remove(income);

                em.merge(category);
                em.merge(income);
                em.flush();

            } catch (EntityNotFoundException enfe) {
                enfe.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not found");
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeExpenseFromCategory(Category category, Expense expense){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                expense.setCategory(null);
                category.getIncomes().remove(expense);

                em.merge(category);
                em.merge(expense);
                em.flush();

            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not found");
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
