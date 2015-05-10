package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pwr.shopassistant.apiutils.ResponseDTO;
import pl.pwr.shopassistant.dao.ProductDao;
import pl.pwr.shopassistant.entities.Product;
import pl.pwr.shopassistant.entities.enums.UserProductStatus;
import pl.pwr.shopassistant.shopapiclient.ShopApiClient;
import pl.pwr.shopassistant.shopapiclient.ShopProduct;
import pl.pwr.shopassistant.shopapiclient.mock.MockApiClient;
import pl.pwr.shopassistant.model.AddProductForm;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.services.auth.AuthService;
import pl.pwr.shopassistant.dao.UserProductDao;
import pl.pwr.shopassistant.entities.User;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.entities.comparators.UserProductsByStatusComparator;
import pl.pwr.shopassistant.services.notifications.NotificationsService;

import java.util.*;

@Controller
@Transactional
@RequestMapping(value = "/products")
public class ProductsController {

    @Autowired
    private UserProductDao userProductDao;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProductDao productDao;


    @RequestMapping(value = { "", "/", "/list" }, method = RequestMethod.GET)
    public String list(Model model) {

        User currentUser = authService.getCurrentUser();
        List<UserProduct> userProducts = userProductDao.getUserProductsByUser(currentUser);

        Collections.sort(userProducts, new UserProductsByStatusComparator());

        model.addAttribute("userProducts", userProducts);
        return "products/list";
    }

    @RequestMapping(value = { "/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public @ResponseBody ResponseDTO changeProductStatus(@RequestBody AddProductForm addProductForm) {

        ResponseDTO responseDTO = new ResponseDTO();

        String ean = addProductForm.getEan();

        User currentUser = authService.getCurrentUser();

        Product product = productDao.findProductByEan(ean);
        if (product == null) {
            String name = "unknown";
            String brand = "unknown";

            ShopApiClient shopApiClient = new MockApiClient();
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

        UserProduct userProduct = userProductDao.findByUserAndProduct(currentUser, product);
        if (userProduct == null) {
            userProduct = new UserProduct();
            userProduct.setQuantity(0);
            userProduct.setName(null);
            userProduct.setUser(currentUser);
            userProduct.setProduct(product);
            userProduct.setStatus(UserProductStatus.unknown);
            userProductDao.saveOrUpdate(userProduct);
        } else {
            notificationsService.addInfoMessage("Product already in the list");
            return responseDTO;
        }

        notificationsService.addSuccessMessage("Product added");

        responseDTO.setResultCode(0);
        responseDTO.setData(null);
        responseDTO.setErrorMessage(null);
        return responseDTO;
    }
}
