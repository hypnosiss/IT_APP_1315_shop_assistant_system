package pl.pwr.shopassistant.fridgeapiclient;

import org.joda.time.DateTime;
import pl.pwr.shopassistant.operationresult.OperationResult;

import java.util.UUID;


public interface FridgeApiClient {

    /**
     *
     * @param ean
     * @param uuid
     * @param status
     * @param dateTime
     * @return OperationResult (resultCode = 0)
     */
    public OperationResult changeProductStatus(String ean, UUID uuid, ProductStatus status, DateTime dateTime);
}
