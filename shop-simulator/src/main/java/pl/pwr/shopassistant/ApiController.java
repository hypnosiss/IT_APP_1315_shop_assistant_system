package pl.pwr.shopassistant;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.pwr.shopassistant.apiutils.ResponseDTO;
import pl.pwr.shopassistant.dao.ProductDao;
import pl.pwr.shopassistant.entities.Product;
import pl.pwr.shopassistant.shopapiclient.ShopProduct;
import pl.pwr.shopassistant.shopapiclient.TimePeriod;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.*;

@RestController
@Transactional
public class ApiController {

    @Autowired
    ProductDao productDao;

    @RequestMapping(value = { "/products/{ean}" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseDTO findProductByEAN(@PathVariable String ean) {
        ResponseDTO responseDTO = new ResponseDTO();

        Product product = productDao.findProductByEan(ean);

        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setName(product.getName());
        shopProduct.setBrand(product.getBrand());
        shopProduct.setEAN(ean);
        shopProduct.setPrice(BigDecimal.valueOf(product.getPrice()));

        responseDTO.setResultCode(0);
        responseDTO.setData(shopProduct);
        return responseDTO;
    }

    @RequestMapping(value = { "/products/eans" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseDTO findProductsByEANs(@RequestParam List<String> eans) {
        ResponseDTO responseDTO = new ResponseDTO();

        List<ShopProduct> shopProducts = new ArrayList<>();
        for (String ean : eans) {
            Product product = productDao.findProductByEan(ean);

            ShopProduct shopProduct = new ShopProduct();
            shopProduct.setName(product.getName());
            shopProduct.setBrand(product.getBrand());
            shopProduct.setEAN(ean);
            shopProduct.setPrice(BigDecimal.valueOf(product.getPrice()));
            shopProducts.add(shopProduct);
        }

        responseDTO.setResultCode(0);
        responseDTO.setData(shopProducts);
        return responseDTO;
    }

    @RequestMapping(value = { "/timetable" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseDTO getDeliveryTimetable() {
        ResponseDTO responseDTO = new ResponseDTO();

        Random random = new Random();
        Map<LocalDate, List<TimePeriod>> timetable = new HashMap<LocalDate, List<TimePeriod>>();
        LocalDate today = new LocalDate();
        for (int i = 1; i <= 5; i++) {
            LocalDate date = today.plusDays(i);

            List<TimePeriod> timePeriods = new ArrayList<TimePeriod>();
            for (int hour = 8; hour < 20; hour++) {
                if (random.nextInt() % 5 >= 2) {
                    TimePeriod timePeriod = new TimePeriod();
                    timePeriod.setFrom(new LocalTime(hour, 0));
                    timePeriod.setTo(new LocalTime(hour + 1, 0));
                    timePeriods.add(timePeriod);
                }
            }
            timetable.put(date, timePeriods);
        }

        responseDTO.setResultCode(0);
        responseDTO.setData(timetable);
        return responseDTO;
    }

    @RequestMapping(value = { "/orders/place" }, method = RequestMethod.GET, produces = "application/json")
    public ResponseDTO placeOrder() {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setResultCode(0);
        return responseDTO;
    }
}
