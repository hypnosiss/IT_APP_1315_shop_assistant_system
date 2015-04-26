package pl.pwr.shopassistant.dao;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.Shop; 

@Repository
public class ShopDao extends AbstractDao<Shop, Integer> {
    public ShopDao() {
        super(Shop.class);
    }
}