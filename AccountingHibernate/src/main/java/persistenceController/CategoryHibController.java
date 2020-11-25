package persistenceController;

import controller.AccountingSystemController;
import controller.CategoryController;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

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

            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Category.class));
            Query query = entityManager.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return null;
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
                category = entityManager.getReference(Category.class, catId);

                for(User user: category.getResponsibleUsers()) {
                    user.getCategories().remove(category);
                    entityManager.merge(user);
                }
                category.getResponsibleUsers().clear();
                entityManager.merge(category);

                AccountingSystem accountingSystem = category.getAccountingSystem();
                accountingSystem.getCategories().remove(category);
                entityManager.merge(accountingSystem);

                Category parentCat = category.getParentCategory();
                if(parentCat !=null){
                    parentCat.getSubCategories().remove(category);
                    entityManager.merge(parentCat);
                }

                for(Category cats:category.getSubCategories()){
                    delete(cats.getId());
                }

                category.getSubCategories().clear();
                entityManager.merge(category);




//                for(User user: categoryHibController.getById(catId).getResponsibleUsers()) {
//                    categoryHibController.removeUserFromCategory(catId, user.getId());
//                }
//                category = entityManager.find(Category.class, catId);
//                for(User user: category.getResponsibleUsers()){
//                    user.getCategories().remove(category);
//                }
//
//                category.getResponsibleUsers().clear();
//
//                category.getIncomes().clear();
//                category.getExpenses().clear();
//                category.getId();

            }catch(Exception e){
                e.printStackTrace();
            }
            entityManager.remove(category);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
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


    public void removeUserFromCategory(int categoryId, int userId) throws Exception {
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
                throw new Exception("Error when removing responsible User from category", enfe);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void addUserToCategory(int categoryId, int userId) throws Exception {
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
                throw new Exception("Error when adding responsible User to category", enfe);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeIncomeFromCategory(Category category, Income income) throws Exception {
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
                throw new Exception("Error when removing income from category", enfe);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeExpenseFromCategory(Category category, Expense expense) throws Exception {
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

            } catch (EntityNotFoundException enfe) {
                throw new Exception("Error when removing expense from category", enfe);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
