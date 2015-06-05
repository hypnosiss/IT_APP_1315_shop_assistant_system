package pl.pwr.shopassistant.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.dao.AbstractDao;
import pl.pwr.shopassistant.entities.Order;
import pl.pwr.shopassistant.entities.User;

import java.util.List;

@Repository
public class OrderDao extends AbstractDao<Order, Integer> {
    public OrderDao() {
        super(Order.class);
    }

    public List<Order> findByUser(User user) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("user.id", user.getId()));

        return (List<Order>) criteria.list();
    }
}