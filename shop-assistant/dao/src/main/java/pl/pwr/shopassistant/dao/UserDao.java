package pl.pwr.shopassistant.dao;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.User; 

@Repository
public class UserDao extends AbstractDao<User, Integer> {
    public UserDao() {
        super(User.class);
    }
}