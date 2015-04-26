package pl.pwr.shopassistant.dao;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.Product; 

@Repository
public class ProductDao extends AbstractDao<Product, Long> {
    public ProductDao() {
        super(Product.class);
    }
}