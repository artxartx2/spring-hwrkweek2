package pl.kurs.spring.hwrkweek2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.kurs.spring.hwrkweek2.model.Product;
import pl.kurs.spring.hwrkweek2.service.ProductService;

import java.math.BigDecimal;

@Controller
@Profile("PLUS")
public class ShopPlusController implements Shop {

    @Value("${shop.tax}")
    private BigDecimal tax;

    private ProductService productService;

    @Autowired
    public ShopPlusController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        BigDecimal taxPercent = BigDecimal.valueOf(1).add(tax.divide(BigDecimal.valueOf(100)));
        for (Product p : productService.getProductList()) {
            totalPrice = totalPrice.add(p.getPrice().multiply(taxPercent));
        }
        return totalPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void getInfo() {
        BigDecimal totalPrice = new BigDecimal(0);
        System.out.println("PLUS packet . List of products: ");
        productService.getProductList().forEach(product -> System.out.println(product.toString()));
        System.out.println("Total price including " + tax + "% tax: " + getTotalPrice());
    }

}
