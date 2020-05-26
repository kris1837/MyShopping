package kz.shag.myshopping;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.localDB.LocalDataBase;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load data from local db
        List<Product> products = LocalDataBase.getInstance(this).productDao().getAll();

        //add to recycler view - Alibi
    }



    @Override
    protected void onStop() {
        //close connection with local db
        super.onStop();
    }
}
