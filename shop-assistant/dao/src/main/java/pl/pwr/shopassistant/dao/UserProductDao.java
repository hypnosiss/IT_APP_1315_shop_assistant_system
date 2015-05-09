package pl.pwr.shopassistant.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.entities.Product;
import pl.pwr.shopassistant.entities.User;

import java.util.List;

@Repository
public class UserProductDao extends AbstractDao<UserProduct, Integer> {
    public UserProductDao() {
        super(UserProduct.class);
    }

    public UserProduct findByUserAndProduct(User user, Product product) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("product.id", product.getId()));
        criteria.add(Restrictions.eq("user.id", user.getId()));

        return (UserProduct) criteria.uniqueResult();
    }

    public List<UserProduct> getUserProductsByUser(User user) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("user.id", user.getId()));

        return criteria.list();
    }
}