package pl.pwr.shopassistant.fridgeapiclient.tesco;

import org.joda.time.LocalDate;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.shopapiclient.ShopApiClient;
import pl.pwr.shopassistant.shopapiclient.ShopProduct;
import pl.pwr.shopassistant.shopapiclient.TimePeriod;
import pl.pwr.shopassistant.shopapiclient.mock.MockApiClient;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MockApiClientTest {

    MockApiClient mockApiClient = new MockApiClient();

    @org.junit.Test
    public void testFindProductByEAN() throws Exception {
        String ean = "123456789";

        OperationResult operationResult = mockApiClient.findProductByEAN(ean);
        ShopProduct shopProduct = (ShopProduct) operationResult.getValue(ShopApiClient.FIND_PRODUCT_BY_EAN__PRODUCT);

        assertEquals((Integer)0, operationResult.getResultCode());
        assertEquals(ean, shopProduct.getEAN());
        assertEquals("Product name", shopProduct.getName());
        assertEquals("Brand", shopProduct.getBrand());
        assertEquals(4.5, shopProduct.getPrice());
    }

    @org.junit.Test
    public void testFindProductsByEANs() throws Exception {

    }

    @org.junit.Test
    public void testGetDeliveryTimetable() throws Exception {
        OperationResult operationResult = mockApiClient.getDeliveryTimetable();
        Map<LocalDate, List<TimePeriod>> timetable =
                (Map<LocalDate, List<TimePeriod>>) operationResult.getValue(ShopApiClient.GET_DELIVERY_TIMETABLE__TIMETABLE);

        for (Map.Entry<LocalDate, List<TimePeriod>> entry : timetable.entrySet()) {
            LocalDate date = entry.getKey();
            List<TimePeriod> timePeriods = entry.getValue();

            System.out.println(date.toString());
            for (TimePeriod timePeriod : timePeriods) {
                System.out.println("   " + timePeriod.getFrom() + " - " + timePeriod.getTo());
            }
        }

    }

    @org.junit.Test
    public void testPlaceOrder() throws Exception {

    }
}