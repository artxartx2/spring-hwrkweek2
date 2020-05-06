package pl.kurs.spring.hwrkweek2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.kurs.spring.hwrkweek2.model.Product;
import pl.kurs.spring.hwrkweek2.service.ProductService;

import java.math.BigDecimal;
import java.util.HashMap;

@Controller
@Profile("PRO")
public class ShopProController implements Shop {

    @Value("${shop.tax}")
    private BigDecimal tax;
    @Value("${shop.discount}")
    private BigDecimal discount;

    private ProductService productService;

    @Autowired
    public ShopProController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        BigDecimal discountPercent = discount.divide(BigDecimal.valueOf(100));
        BigDecimal taxPercent = BigDecimal.valueOf(1).add(tax.divide(BigDecimal.valueOf(100)));

        for (Product p : productService.getProductList()) {
            totalPrice = totalPrice.add(p.getPrice().subtract(p.getPrice().multiply(discountPercent)).multiply(taxPercent));
        }
        return totalPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void getInfo() {
        System.out.println("PRO packet . List of products: ");
        productService.getProductList().forEach(product -> System.out.println(product.toString()));
        System.out.println("Total price including " + tax + "% tax and  " + discount + "% discount : " + getTotalPrice());
    }

}
