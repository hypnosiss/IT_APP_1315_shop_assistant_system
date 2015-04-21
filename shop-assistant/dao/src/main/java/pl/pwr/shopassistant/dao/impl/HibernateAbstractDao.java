package pl.pwr.shopassistant.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import pl.pwr.shopassistant.dao.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class HibernateAbstractDao<TEntityImpl, TPrimaryKey> implements Dao<TEntityImpl, TPrimaryKey> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<? extends TEntityImpl> entityClass;

    public HibernateAbstractDao(Class<? extends TEntityImpl> entityClass) {
        this.entityClass = entityClass;
    }

	public void save(TEntityImpl entity) {
        entityManager.persist(entity);
    }

    public void update(TEntityImpl entity) {
        entityManager.merge(entity);
    }

    public void detach(TEntityImpl entity){
        entityManager.detach(entity);
    }

    public void delete(TEntityImpl entity) {
        entityManager.remove(entity);
    }

    public boolean loaded(TEntityImpl entity){
        return entityManager.contains(entity);
    }

    public void flush() {
        entityManager.flush();
    }


    public TEntityImpl findByPk(TPrimaryKey pk) {
        return this.entityManager.find(this.entityClass, pk);
    }

    public List<TEntityImpl> getList() {
        return (List<TEntityImpl>) this.entityManager.createQuery("from " + this.entityClass.getName(), entityClass).getResultList();
    }
    
    public Session getHibernateSession() {
    	return this.entityManager.unwrap(Session.class);
    }
    
    public Criteria createEntityCriteria() {
    	return getHibernateSession().createCriteria(entityClass);
    }
    
    public Criteria createEntityCriteria(String alias) {
    	return getHibernateSession().createCriteria(entityClass, alias);
    }
}
