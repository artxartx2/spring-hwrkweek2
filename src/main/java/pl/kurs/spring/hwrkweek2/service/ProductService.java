package pl.kurs.spring.hwrkweek2.service;

import org.springframework.stereotype.Service;
import pl.kurs.spring.hwrkweek2.model.Product;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {

    private final int MIN_PRICE = 50;
    private final int MAX_PRICE = 300;

    private List<Product> productList;

    public ProductService() {
        productList = new ArrayList<>();
        productList.add(new Product("Matrix", getRandomPrice()));
        productList.add(new Product("Star Wars ep. 3", getRandomPrice()));
        productList.add(new Product("Star Wars ep. 4", getRandomPrice()));
        productList.add(new Product("Cube", getRandomPrice()));
        productList.add(new Product("Pulp Fiction", getRandomPrice()));
    }

    public List<Product> getProductList() {
        return productList;
    }

    private BigDecimal getRandomPrice() {
        int randInt = MIN_PRICE + (new Random().nextInt((MAX_PRICE - MIN_PRICE) * 100));
        return new BigDecimal(BigInteger.valueOf(randInt), 2);
    }
}
