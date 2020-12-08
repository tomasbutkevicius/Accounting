package persistenceController;

import model.AccountingSystem;
import model.User;
import window.Popup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserHibController {

    EntityManagerFactory entityManagerFactory = null;

    public UserHibController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void create(User user) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(user));
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            Popup.display("error", "error", "ok");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<User> getUserList(){
        return getUserList(true, -1, -1);
    }

    public List<User> getUserList(boolean all, int maxRes, int firstRes){

        EntityManager entityManager = getEntityManager();
        try{
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(User.class));
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

    public User getById(int id) {
        for(User user: getUserList()){
            if(user.getId() == id) return user;
        }
        return null;
    }

    public User getByNameInSystem(AccountingSystem accountingSystem, String name) {
        for (User user : accountingSystem.getUsers()) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }


    public void update(User user){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            user = entityManager.merge(user);
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
            User user = null;
            try{
                user = entityManager.getReference(User.class, id);
                user.getCategories().clear();
            }catch(Exception e){
                e.printStackTrace();
            }
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<User> getAllUsersInSystem(AccountingSystem accountingSystem) {
        List<User> usersInSystem = new ArrayList<>();

        for (User user : getUserList()) {
            if (user.getAccountingSystem().getId() == accountingSystem.getId()){
                usersInSystem.add(user);
            }
        }
        return usersInSystem;
    }
}
