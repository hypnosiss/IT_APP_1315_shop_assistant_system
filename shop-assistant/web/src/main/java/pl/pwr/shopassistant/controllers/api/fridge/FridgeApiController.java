package pl.pwr.shopassistant.controllers.api.fridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.pwr.shopassistant.apiutils.ResponseDTO;
import pl.pwr.shopassistant.dao.EventDao;
import pl.pwr.shopassistant.dao.ProductDao;
import pl.pwr.shopassistant.dao.UserDao;
import pl.pwr.shopassistant.dao.UserProductDao;
import pl.pwr.shopassistant.entities.Event;
import pl.pwr.shopassistant.entities.Product;
import pl.pwr.shopassistant.entities.User;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.entities.enums.EventType;
import pl.pwr.shopassistant.entities.enums.UserProductStatus;
import pl.pwr.shopassistant.forms.ProductStatusChangeForm;
import pl.pwr.shopassistant.fridgeapiclient.ShopApiClient;
import pl.pwr.shopassistant.fridgeapiclient.ShopProduct;
import pl.pwr.shopassistant.fridgeapiclient.tesco.TescoApiClient;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.services.hash.HashService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Transactional
@RequestMapping(value = { "/api" })
public class FridgeApiController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserProductDao userProductDao;

    @Autowired
    @Resource(name="sha1HashService")
    private HashService hashService;


    @RequestMapping(value = { "/products/{ean}/change-status" }, method = RequestMethod.POST, consumes = "application/json")
    public ResponseDTO changeProductStatus(@RequestBody ProductStatusChangeForm productStatusChangeForm,
                                           @PathVariable(value = "ean") String ean,
                                           HttpServletRequest request) {

        ResponseDTO responseDTO = new ResponseDTO();

        //authentication
        String login = request.getHeader("X-API-USER");
        String apiKey = request.getHeader("X-API-KEY");

        if (login == null || login.isEmpty() || apiKey == null || apiKey.isEmpty()) {
            responseDTO.setResultCode(1);
            responseDTO.setErrorMessage("API key invalid or not provided");

            return responseDTO;
        }

        User user = userDao.findByUsername(login);
        if (user == null) {
            responseDTO.setResultCode(1);
            responseDTO.setErrorMessage("API key invalid or not provided");

            return responseDTO;
        }

        String userKey = hashService.hash(user.getUsername() + user.getPassword());
        if (!apiKey.equals(userKey)) {
            responseDTO.setResultCode(1);
            responseDTO.setErrorMessage("API key invalid or not provided");

            return responseDTO;
        }

        Product product = productDao.findProductByEan(ean);
        if (product == null) {
            String name = "unknown";
            String brand = "unknown";

            ShopApiClient shopApiClient = new TescoApiClient("http://localhost:8080");
            OperationResult operationResult = shopApiClient.findProductByEAN(ean);
            if (operationResult.getResultCode() == 0) {
                ShopProduct shopProduct =
                        (ShopProduct) operationResult.getValue(ShopApiClient.FIND_PRODUCT_BY_EAN__PRODUCT);

                if (shopProduct != null) {
                    name = shopProduct.getName();
                    brand = shopProduct.getBrand();
                }
            }

            product = new Product();
            product.setName(name);
            product.setBrand(brand);
            product.setEan(ean);
            productDao.save(product);
        }

        Event event = new Event();
        event.setProduct(product);
        event.setUuid(productStatusChangeForm.getUuid());
        event.setTriggerTimestamp(new Date(productStatusChangeForm.getTimestamp()));
        event.setUser(user);

        EventType eventType = null;
        UserProductStatus userProductStatus = null;
        switch (productStatusChangeForm.getStatus()) {
            case in:
                eventType = EventType.in;
                userProductStatus = UserProductStatus.in;
                break;

            case out:
                eventType = EventType.out;
                userProductStatus = UserProductStatus.out;
                break;
        }
        event.setType(eventType);
        eventDao.save(event);

        UserProduct userProduct = userProductDao.findByUserAndProduct(user, product);
        if (userProduct == null) {
            Integer quantity = userProductStatus.equals(UserProductStatus.in) ? 1 : 0;

            userProduct = new UserProduct();
            userProduct.setQuantity(quantity);
            userProduct.setName(null);
            userProduct.setUser(user);
            userProduct.setProduct(product);
            userProduct.setStatus(userProductStatus);
        } else {
            Integer quantity = userProduct.getQuantity();
            if (productStatusChangeForm.getStatus().equals(ProductStatus.in)) {
                quantity += 1;
            } else if (productStatusChangeForm.getStatus().equals(ProductStatus.out)) {
                quantity -= 1;
            }

            if (quantity <= 0) {
                quantity = 0;
                userProductStatus = UserProductStatus.out;
            } else {
                userProductStatus = UserProductStatus.in;
            }
            userProduct.setQuantity(quantity);
            userProduct.setStatus(userProductStatus);
        }
        userProductDao.saveOrUpdate(userProduct);

        responseDTO.setResultCode(0);
        responseDTO.setData(null);
        responseDTO.setErrorMessage(null);
        return responseDTO;
    }
}
