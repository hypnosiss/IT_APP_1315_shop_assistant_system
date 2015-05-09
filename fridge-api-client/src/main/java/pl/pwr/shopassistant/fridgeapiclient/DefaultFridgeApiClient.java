package pl.pwr.shopassistant.fridgeapiclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import pl.pwr.shopassistant.apiutils.ResponseDTO;
import pl.pwr.shopassistant.operationresult.OperationResult;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

public class DefaultFridgeApiClient implements FridgeApiClient {
    private static final Logger LOGGER = Logger.getLogger(DefaultFridgeApiClient.class);

    Client client = Client.create();

    private String host;
    private String login;
    private String apiKey;

    public DefaultFridgeApiClient(String host, String login, String apiKey) {
        this.host = host;
        this.login = login;
        this.apiKey = apiKey;
    }

    public OperationResult changeProductStatus(String ean, UUID uuid, ProductStatus status, DateTime dateTime) {
        OperationResult operationResult = new OperationResult();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ProductStatusChangeDTO productStatusChangeDTO = new ProductStatusChangeDTO();
        productStatusChangeDTO.setUuid(uuid);
        productStatusChangeDTO.setStatus(status);
        productStatusChangeDTO.setTimestamp(dateTime.getMillis());
        String jsonString = gson.toJson(productStatusChangeDTO);

        LOGGER.info(jsonString);

        //wysłanie zapytanie
        String apiUrl = String.format("%s/api/products/%s/change-status", host, ean);
        LOGGER.info(apiUrl);

        WebResource webResource = client.resource(apiUrl);
        ClientResponse response = webResource
                .header("X-API-USER", login)
                .header("X-API-KEY", apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, jsonString);

        String jsonOutput = response.getEntity(String.class);
        LOGGER.info(jsonOutput);

        //obsługa błędów zapytania
        if (response.getStatus() != 200) {
            operationResult.setResultCode(1);
            operationResult.setErrorMessage(
                    String.format("%d: %s", response.getStatus(), response.getClientResponseStatus().getReasonPhrase()));
            return operationResult;
        }

        //obsługa błędów sklepu
        ResponseDTO responseDTO = gson.fromJson(jsonOutput, ResponseDTO.class);
        if (responseDTO.getResultCode() != 0) {
            operationResult.setResultCode(responseDTO.getResultCode());
            operationResult.setErrorMessage(responseDTO.getErrorMessage());
            return operationResult;
        }

        operationResult.setResultCode(0);
        return operationResult;
    }
}
