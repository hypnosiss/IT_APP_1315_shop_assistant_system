package pl.pwr.shopassistant.entities.comparators;

import pl.pwr.shopassistant.entities.UserProduct;

import java.util.Comparator;

public class UserProductsByStatusComparator implements Comparator<UserProduct> {
    public int compare(UserProduct userProduct1, UserProduct userProduct2) {
        return userProduct2.getStatus().getValue().compareTo(userProduct1.getStatus().getValue());
    }
}
