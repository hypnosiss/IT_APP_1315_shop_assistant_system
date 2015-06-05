package pl.pwr.shopassistant.shopapiclient.tesco;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.joda.time.LocalDate;
import pl.pwr.shopassistant.apiutils.ResponseDTO;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.shopapiclient.ShopProduct;
import pl.pwr.shopassistant.shopapiclient.ShopApiClient;
import pl.pwr.shopassistant.shopapiclient.TimePeriod;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        //obsługa błędów zapytania
        if (response.getStatus() != 200) {
            operationResult.setResultCode(1);
            operationResult.setErrorMessage(response.getClientResponseStatus().getReasonPhrase());
            return operationResult;
        }

        //obsługa błędów sklepu
        String jsonString = response.getEntity(String.class);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ResponseDTO responseDTO = gson.fromJson(jsonString, ResponseDTO.class);

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

        MultivaluedMap<String, String> eans = new MultivaluedMapImpl();
        eans.put("ean", new ArrayList<String>(productsEANs));

        //wysłanie zapytanie
        WebResource webResource = client.resource(host + "/products/eans").queryParams(eans);
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

        List<ShopProduct> shopProducts = (List<ShopProduct>)responseDTO.getData();

        //przykład
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.FIND_PRODUCTS_BY_EANS__PRODUCTS, shopProducts);

        return operationResult;
    }

    public OperationResult getDeliveryTimetable() {
        OperationResult operationResult = new OperationResult();

        //wysłanie zapytanie
        WebResource webResource = client.resource(host + "/timetable");
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

        Map<LocalDate, List<TimePeriod>> timetable = ( Map<LocalDate, List<TimePeriod>>)responseDTO.getData();

        //przykład
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.GET_DELIVERY_TIMETABLE__TIMETABLE, timetable);

        return operationResult;
    }

    public OperationResult placeOrder(Set<String> eans) {
        OperationResult operationResult = new OperationResult();

        //wysłanie zapytanie
        String input = "";

        WebResource webResource = client.resource(host + "/orders/place");
        ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, input);

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

        Map<LocalDate, List<TimePeriod>> timetable = ( Map<LocalDate, List<TimePeriod>>)responseDTO.getData();

        //przykład
        operationResult.setResultCode(0);
        operationResult.setValue(ShopApiClient.GET_DELIVERY_TIMETABLE__TIMETABLE, timetable);

        return operationResult;
    }
}
