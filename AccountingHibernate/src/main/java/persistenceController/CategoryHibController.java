package persistenceController;

import model.AccountingSystem;
import model.Category;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    public void delete(int id){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Category category = null;
            try{
                category = entityManager.getReference(Category.class, id);
                for(User user: category.getResponsibleUsers()){
                    user.getCategories().remove(category);
                }
                category.getResponsibleUsers().clear();
                category.getId();

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
}
