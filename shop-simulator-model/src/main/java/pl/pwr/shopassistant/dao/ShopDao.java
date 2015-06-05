package pl.pwr.shopassistant.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.dao.AbstractDao;
import pl.pwr.shopassistant.entities.Shop;

@Repository
public class ShopDao extends AbstractDao<Shop, Integer> {
    public ShopDao() {
        super(Shop.class);
    }

    public Shop findByName(String shopName) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("name", shopName));

        return (Shop) criteria.uniqueResult();
    }
}