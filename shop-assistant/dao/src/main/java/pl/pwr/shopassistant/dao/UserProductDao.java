package pl.pwr.shopassistant.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.entities.Product;
import pl.pwr.shopassistant.entities.User;
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

    public UserProduct findByUserAndProduct(User user, Product product) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("product.id", product.getId()));
        criteria.add(Restrictions.eq("user.id", user.getId()));

        return (UserProduct) criteria.uniqueResult();
    }
}