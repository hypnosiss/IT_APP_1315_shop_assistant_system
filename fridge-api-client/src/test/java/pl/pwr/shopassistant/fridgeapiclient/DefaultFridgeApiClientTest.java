package pl.pwr.shopassistant.fridgeapiclient;

import org.joda.time.DateTime;
import pl.pwr.shopassistant.operationresult.OperationResult;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class DefaultFridgeApiClientTest {

    @org.junit.Test
    public void testChangeProductStatus() throws Exception {
        DefaultFridgeApiClient fridgeApiClient = new DefaultFridgeApiClient("http://127.0.0.1", "apiKey");

        String ean = "1234567891011";
        ProductStatus productStatus = ProductStatus.in;
        DateTime dateTime = DateTime.now();
        UUID uuid = UUID.randomUUID();

        OperationResult operationResult = fridgeApiClient.changeProductStatus(ean, uuid, productStatus, dateTime);

        System.out.println(operationResult.getResultCode());
    }
}