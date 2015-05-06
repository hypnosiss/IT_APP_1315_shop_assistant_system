package pl.pwr.shopassistant.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.User;

@Repository
public class UserDao extends AbstractDao<User, Integer> {
    public UserDao() {
        super(User.class);
    }

    public User findByUsername(String username) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("username", username));

        return (User) criteria.uniqueResult();
    }
}