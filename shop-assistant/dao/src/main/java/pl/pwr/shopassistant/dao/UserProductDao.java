package pl.pwr.shopassistant.dao;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.UserProduct; 

@Repository
public class UserProductDao extends AbstractDao<UserProduct, Integer> {
    public UserProductDao() {
        super(UserProduct.class);
    }
}