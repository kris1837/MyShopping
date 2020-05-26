package kz.shag.myshopping.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kz.shag.myshopping.entity.Product;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    List<Product> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM products WHERE id = (:id)")
    Product findProduct(int id);

    @Query("SELECT * FROM products WHERE title = (:title)")
    Product findProduct(String title);
}
