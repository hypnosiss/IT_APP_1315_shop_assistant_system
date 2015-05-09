package pl.pwr.shopassistant.fridgeapiclient.tesco;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Service;
import pl.pwr.shopassistant.apiutils.ResponseDTO;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.fridgeapiclient.ShopProduct;
import pl.pwr.shopassistant.fridgeapiclient.ShopApiClient;

import java.util.Set;

public class TescoApiClient implements ShopApiClient {
    Client client = Client.create();

    private String host;

    public TescoApiClient(String host) {
        this.host = host;
    }

    public OperationResult findProductByEAN(String ean) {
        OperationResult operationResult = new OperationResult();

        //wysłanie zapytanie
        WebResource webResource = client.resource(host + "/products/" + ean);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        //obsługa błędów zapytania
        if (response.getStatus() != 200) {
            operationResult.setResultCode(1);
            operationResult.setErrorMessage(response.getClientResponseStatus().getReasonPhrase());
            return operationResult;
        }

        //obsługa błędów sklepu
        ResponseDTO responseDTO = response.getEntity(ResponseDTO.class);
        if (responseDTO.getResultCode() != 0) {
            operationResult.setResultCode(responseDTO.getResultCode());
            operationResult.setErrorMessage(responseDTO.getErrorMessage());
        }

        ShopProduct shopProduct = (ShopProduct)responseDTO.getData();

        //przykład
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.FIND_PRODUCT_BY_EAN__PRODUCT, shopProduct);

        return operationResult;
    }

    public OperationResult findProductsByEANs(Set<String> productsEANs) {
        OperationResult operationResult = new OperationResult();

        //

        return operationResult;
    }

    public OperationResult getDeliveryTimetable() {
        OperationResult operationResult = new OperationResult();

        //

        return operationResult;
    }

    public OperationResult placeOrder() {
        OperationResult operationResult = new OperationResult();

        //

        return operationResult;
    }
}
