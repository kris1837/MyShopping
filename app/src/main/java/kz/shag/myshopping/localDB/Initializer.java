package kz.shag.myshopping.localDB;

import android.content.Context;

import kz.shag.myshopping.dao.ProductDao;
import kz.shag.myshopping.entity.Product;

public class Initializer {
    public static void Init(ProductRepository productRepository){

        Product p1 = new Product(1, "Banana", "Yellow fruit",
                1.5, "https://upload.wikimedia.org/wikipedia/commons/8/8a/Banana-Single.jpg", 1);
        Product p2 = new Product(2, "Potato", "vegetable",
                1.1, "", 1);
        Product p3 = new Product(3, "Apple", "fruit",
                1.3, "", 1);

        productRepository.insertProduct(p1);
        /*productRepository.insertProduct(p2);
        productRepository.insertProduct(p3);*/
    }
}
