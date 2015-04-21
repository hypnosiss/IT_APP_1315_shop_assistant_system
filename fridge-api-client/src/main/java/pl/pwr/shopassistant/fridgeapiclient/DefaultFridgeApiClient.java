package pl.pwr.shopassistant.fridgeapiclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.joda.time.DateTime;
import pl.pwr.shopassistant.apiutils.ResponseDTO;
import pl.pwr.shopassistant.operationresult.OperationResult;

import java.util.UUID;

public class DefaultFridgeApiClient implements FridgeApiClient {

    Client client = Client.create();

    private String host;
    private String apiKey;

    public DefaultFridgeApiClient(String host, String apiKey) {
        this.host = host;
        this.apiKey = apiKey;
    }

    @Override
    public OperationResult changeProductStatus(String ean, UUID uuid, ProductStatus status, DateTime dateTime) {
        OperationResult operationResult = new OperationResult();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ProductStatusChangeDTO productStatusChangeDTO = new ProductStatusChangeDTO();
        productStatusChangeDTO.setEan(ean);
        productStatusChangeDTO.setUuid(uuid);
        productStatusChangeDTO.setStatus(status);
        productStatusChangeDTO.setDateTime(dateTime);
        String jsonString = gson.toJson(productStatusChangeDTO);

        //wysłanie zapytanie
        WebResource webResource = client.resource(host + "/products/change-status");
        ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, jsonString);

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

        //przykład
        operationResult.setResultCode(0);

        return operationResult;
    }
}
