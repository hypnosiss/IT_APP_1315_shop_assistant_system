package pl.pwr.shopassistant.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.Product;

@Repository
public class ProductDao extends AbstractDao<Product, Long> {
    public ProductDao() {
        super(Product.class);
    }

    public Product findProductByEan(String ean) {
        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.eq("ean", ean));

        return (Product) criteria.uniqueResult();
    }
}