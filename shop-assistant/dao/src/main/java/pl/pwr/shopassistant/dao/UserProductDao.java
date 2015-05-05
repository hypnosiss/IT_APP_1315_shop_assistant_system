package pl.pwr.shopassistant.dao;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.UserProduct;


@Repository
public class UserProductDao extends AbstractDao<UserProduct, Integer> {
    public UserProductDao() {
        super(UserProduct.class);
    }

    public UserProduct getUserProductForEAN(String ean) {
        return entityManager.createQuery("select up from " + this.entityClass.getName() +
                " as up, Product as p where p.ean=" + ean, UserProduct.class).getSingleResult();
    }
}