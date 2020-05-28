package kz.shag.myshopping.localDB;

import android.content.Context;

import kz.shag.myshopping.dao.ProductDao;
import kz.shag.myshopping.entity.Product;

public class Initializer {
    public static void Init(ProductRepository productRepository){

        Product p1 = new Product("Banana", "Yellow fruit",
                1.5, "", 1);
        Product p2 = new Product("Potato", "vegetable",
                1.1, "", 1);
        Product p3 = new Product("Apple", "fruit",
                1.3, "", 1);

        productRepository.insertProduct(p1);
        productRepository.insertProduct(p2);
        productRepository.insertProduct(p3);
    }
}
