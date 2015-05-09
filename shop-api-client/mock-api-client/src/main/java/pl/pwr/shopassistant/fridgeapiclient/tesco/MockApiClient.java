package pl.pwr.shopassistant.fridgeapiclient.tesco;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import pl.pwr.shopassistant.fridgeapiclient.TimePeriod;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.fridgeapiclient.ShopProduct;
import pl.pwr.shopassistant.fridgeapiclient.ShopApiClient;

import java.math.BigDecimal;
import java.util.*;

public class MockApiClient implements ShopApiClient {

    public OperationResult findProductByEAN(String ean) {
        OperationResult operationResult = new OperationResult();

        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setEAN(ean);
        shopProduct.setName("Product name");
        shopProduct.setBrand("Brand");
        shopProduct.setPrice(BigDecimal.valueOf(4.50));

        //przykład
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.FIND_PRODUCT_BY_EAN__PRODUCT, shopProduct);

        return operationResult;
    }

    public OperationResult findProductsByEANs(Set<String> productsEANs) {
        OperationResult operationResult = new OperationResult();

        List<ShopProduct> shopProducts = new ArrayList<ShopProduct>();

        Integer i = 1;
        Random random = new Random();
        for (String productEAN : productsEANs) {
            ShopProduct shopProduct = new ShopProduct();
            shopProduct.setEAN(productEAN);
            shopProduct.setName(String.format("Product%d name", i));
            shopProduct.setBrand(String.format("Brand%d", i));
            shopProduct.setPrice(BigDecimal.valueOf(i * 3.14));

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
            Integer hour = 8;
            for (int j = 1; j <= random.nextInt() % 10; j++) {
                TimePeriod timePeriod = new TimePeriod();
                timePeriod.setFrom(new LocalTime(hour, 0));
                timePeriod.setTo(new LocalTime(hour + 1, 0));

                //totalny random
                if (random.nextInt() % 5 >= 2) {
                    timePeriods.add(timePeriod);
                }

                hour++;
            }

            timetable.put(date, timePeriods);
        }

        //
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.GET_DELIVERY_TIMETABLE__TIMETABLE, timetable);

        return operationResult;
    }

    public OperationResult placeOrder() {
        OperationResult operationResult = new OperationResult();

        //
        operationResult.setResultCode(0);

        return operationResult;
    }
}
