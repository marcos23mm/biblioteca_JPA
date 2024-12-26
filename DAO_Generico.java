package miPaquete;

import jakarta.persistence.*;
import java.util.List;

public class DAO_Generico<T> {
    private final EntityManager entityManager;

    public DAO_Generico(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public T findById(Class<T> entityClass, Object id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll(Class<T> entityClass) {
        return entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }

    public void update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
    }

    public void delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
    }
}
