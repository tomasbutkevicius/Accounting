package persistenceController;

import model.AccountingSystem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AccountingSystemHib {

    EntityManagerFactory entityManagerFactory = null;

    public AccountingSystemHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void create(AccountingSystem accountingSystem) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(accountingSystem));
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<AccountingSystem> getAccountingSystemList() {
        return getAccountingSystemList(true, -1, -1);
    }

    public List<AccountingSystem> getAccountingSystemList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {

            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(AccountingSystem.class));
            Query query = entityManager.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return null;
    }

    public AccountingSystem getByName(String name) {
        for (AccountingSystem accountingSystem : getAccountingSystemList()) {
            if (accountingSystem.getName().equals(name)) return accountingSystem;
        }
        return null;
    }

    public AccountingSystem getById(int id) {
        for (AccountingSystem accountingSystem : getAccountingSystemList()) {
            if (accountingSystem.getId() == id) return accountingSystem;
        }
        return null;
    }

    public void update(AccountingSystem accountingSystem) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            accountingSystem = entityManager.merge(accountingSystem);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void delete(int id) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            AccountingSystem accountingSystem = null;
            try {
                accountingSystem = entityManager.getReference(AccountingSystem.class, id);
                accountingSystem.getId();
            } catch (Exception e) {
                e.printStackTrace();
            }
            entityManager.remove(accountingSystem);
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
