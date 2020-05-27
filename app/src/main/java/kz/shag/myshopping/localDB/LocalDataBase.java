package kz.shag.myshopping.localDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kz.shag.myshopping.dao.ProductDao;
import kz.shag.myshopping.entity.Product;

@Database(entities = {Product.class}, version = 2)
public abstract class LocalDataBase extends RoomDatabase {
    private static final String DB_NAME  = "cart_db";
    private static LocalDataBase instance;

    public static synchronized LocalDataBase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), LocalDataBase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ProductDao productDao();
}
