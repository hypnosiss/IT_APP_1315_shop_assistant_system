package pl.pwr.shopassistant.fridgeapiclient;

import pl.pwr.shopassistant.operationresult.OperationResult;

import java.util.Set;


public interface ShopApiClient {
    public static final String FIND_PRODUCT_BY_EAN__PRODUCT = "FIND_PRODUCT_BY_EAN__PRODUCT";
    public static final String FIND_PRODUCTS_BY_EANS__PRODUCTS = "FIND_PRODUCTS_BY_EANS__PRODUCTS";
    public static final String GET_DELIVERY_TIMETABLE__TIMETABLE = "GET_DELIVERY_TIMETABLE__TIMETABLE";

    /**
     *
     * @param ean
     * @return OperationResult (product: Product)
     */
    public OperationResult findProductByEAN(String ean);

    /**
     * nie zwraca produktów, których nie ma
     *
     * @param productsEANs
     * @return OperationResult (products: Set<Product>)
     */
    public OperationResult findProductsByEANs(Set<String> productsEANs);

    /**
     *  @return OperationResult (timetable: Map<LocalDate, List<TimePeriod>>)
     */
    public OperationResult getDeliveryTimetable();

    /**
     *  @return OperationResult
     */
    public OperationResult placeOrder();
}
