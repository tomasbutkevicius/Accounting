package persistenceController;

import model.User;
import window.Popup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
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
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            Popup.display("Something went wrong", "Error", "ok");
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

    public User getByName(String name) {
        for(User user: getUserList()){
            if(user.getName().equals(name)) return user;
        }
        return null;
    }

    public void update(User user){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
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

    public void delete(String name){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            User user = null;
            try{
                user = entityManager.getReference(User.class, name);
                user.getName();

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
}
