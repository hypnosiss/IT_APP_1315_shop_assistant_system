package pl.pwr.shopassistant.dao.impl;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.dao.ProductDao;
import pl.pwr.shopassistant.entities.ProductEntity;
import pl.pwr.shopassistant.entities.impl.HibernateProductEntity;

import java.util.List;

@Repository
public class HibernateProductDao extends HibernateAbstractDao<ProductEntity, Integer> implements ProductDao {
    
	public HibernateProductDao() {
        super(HibernateProductEntity.class);
    }
}