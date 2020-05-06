package pl.kurs.spring.hwrkweek2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import pl.kurs.spring.hwrkweek2.model.Product;
import pl.kurs.spring.hwrkweek2.service.ProductService;

import java.math.BigDecimal;

@Controller
@Profile("START")
public class ShopStartController implements Shop {

    private ProductService productService;

    @Autowired
    public ShopStartController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Product p : productService.getProductList()) {
            totalPrice = totalPrice.add(p.getPrice());
        }
        return totalPrice;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void getInfo() {
        System.out.println("START packet . List of products: ");
        productService.getProductList().forEach(product -> System.out.println(product.toString()));
        System.out.println("Total price: " + getTotalPrice());
    }

}
