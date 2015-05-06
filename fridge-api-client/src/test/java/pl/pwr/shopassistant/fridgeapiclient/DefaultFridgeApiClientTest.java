package pl.pwr.shopassistant.fridgeapiclient;

import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Test;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.services.hash.Sha1HashService;

import java.util.UUID;

import static org.junit.Assert.*;

public class DefaultFridgeApiClientTest {

    @Test
    public void testChangeProductStatusIn() throws Exception {
        Sha1HashService sha1HashService = new Sha1HashService();

        String login = "vansel";
        String password = "test";
        String apiKey = sha1HashService.hash(login + password);

        DefaultFridgeApiClient fridgeApiClient = new DefaultFridgeApiClient("http://127.0.0.1:8080", login, apiKey);

        String ean = "1234567891011";
        ProductStatus productStatus = ProductStatus.in;
        DateTime dateTime = DateTime.now();
        UUID uuid = UUID.randomUUID();

        OperationResult operationResult = fridgeApiClient.changeProductStatus(ean, uuid, productStatus, dateTime);
        System.out.println(operationResult.getErrorMessage());

        assertEquals((Integer) 0, operationResult.getResultCode());
    }

    @Test
    public void testChangeProductStatusOut() throws Exception {
        Sha1HashService sha1HashService = new Sha1HashService();

        String login = "vansel";
        String password = "test";
        String apiKey = sha1HashService.hash(login + password);

        DefaultFridgeApiClient fridgeApiClient = new DefaultFridgeApiClient("http://127.0.0.1:8080", login, apiKey);

        String ean = "1234567891011";
        ProductStatus productStatus = ProductStatus.out;
        DateTime dateTime = DateTime.now();
        UUID uuid = UUID.randomUUID();

        OperationResult operationResult = fridgeApiClient.changeProductStatus(ean, uuid, productStatus, dateTime);
        System.out.println(operationResult.getErrorMessage());

        assertEquals((Integer) 0, operationResult.getResultCode());
    }
}