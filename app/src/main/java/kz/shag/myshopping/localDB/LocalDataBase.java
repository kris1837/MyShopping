package kz.shag.myshopping.localDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kz.shag.myshopping.dao.ProductDao;
import kz.shag.myshopping.entity.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class LocalDataBase extends RoomDatabase {
    public abstract ProductDao productDao();
}
