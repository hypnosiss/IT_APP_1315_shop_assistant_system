package pl.pwr.shopassistant.dao;

import java.util.List;

public interface Dao<TEntity, TPrimaryKey> {

    public void save(TEntity entity);
    public void update(TEntity entity);
    public void detach(TEntity entity);
    public void delete(TEntity entity);
    public boolean loaded(TEntity entity);
    public void flush();

    public TEntity findByPk(TPrimaryKey primaryKey);
    public List<TEntity> getList();
}
