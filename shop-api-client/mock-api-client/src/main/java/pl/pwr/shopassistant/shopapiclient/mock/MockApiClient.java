package pl.pwr.shopassistant.shopapiclient.mock;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import pl.pwr.shopassistant.shopapiclient.TimePeriod;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.shopapiclient.ShopProduct;
import pl.pwr.shopassistant.shopapiclient.ShopApiClient;

import java.math.BigDecimal;
import java.util.*;

public class MockApiClient implements ShopApiClient {

    Map<String, ShopProduct> products = new HashMap<String, ShopProduct>();
    public MockApiClient() {
        ShopProduct shopProduct1 = new ShopProduct();
        shopProduct1.setName("Biszkopty w czekoladzie o smaku pomarańczowym");
        shopProduct1.setBrand("Wedel");
        shopProduct1.setEAN("1111111111111");
        shopProduct1.setPrice(BigDecimal.valueOf(10.23));
        products.put("1111111111111", shopProduct1);

        ShopProduct shopProduct2 = new ShopProduct();
        shopProduct2.setName("Pierniki w czekoladzie mlecznej");
        shopProduct2.setBrand("Jutrzenka");
        shopProduct2.setEAN("2222222222222");
        shopProduct2.setPrice(BigDecimal.valueOf(9.99));
        products.put("2222222222222", shopProduct2);
    }

    public OperationResult findProductByEAN(String ean) {
        OperationResult operationResult = new OperationResult();

        ShopProduct shopProduct = products.get(ean);

        //przykład
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.FIND_PRODUCT_BY_EAN__PRODUCT, shopProduct);

        return operationResult;
    }

    public OperationResult findProductsByEANs(Set<String> productsEANs) {
        OperationResult operationResult = new OperationResult();

        List<ShopProduct> shopProducts = new ArrayList<ShopProduct>();

        Integer i = 1;
        for (String productEAN : productsEANs) {
            ShopProduct shopProduct = products.get(productEAN);

            shopProducts.add(shopProduct);

            i++;
        }

        //
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.FIND_PRODUCTS_BY_EANS__PRODUCTS, shopProducts);

        return operationResult;
    }

    public OperationResult getDeliveryTimetable() {
        OperationResult operationResult = new OperationResult();

        Random random = new Random();

        Map<LocalDate, List<TimePeriod>> timetable = new HashMap<LocalDate, List<TimePeriod>>();
        LocalDate today = new LocalDate();
        for (int i = 1; i <= 5; i++) {
            LocalDate date = today.plusDays(i);

            List<TimePeriod> timePeriods = new ArrayList<TimePeriod>();
            for (int hour = 8; hour < 20; hour++) {
                //totalny random
                if (random.nextInt() % 5 >= 2) {
                    TimePeriod timePeriod = new TimePeriod();
                    timePeriod.setFrom(new LocalTime(hour, 0));
                    timePeriod.setTo(new LocalTime(hour + 1, 0));
                    timePeriods.add(timePeriod);
                }
            }
            timetable.put(date, timePeriods);
        }

        //
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.GET_DELIVERY_TIMETABLE__TIMETABLE, timetable);

        return operationResult;
    }

    public OperationResult placeOrder(Set<String> productsEANs) {
        OperationResult operationResult = new OperationResult();

        //
        operationResult.setResultCode(0);

        return operationResult;
    }
}
