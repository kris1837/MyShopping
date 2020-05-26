package kz.shag.myshopping.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.adapters.CartAdapter;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.localDB.LocalDataBase;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load data from local db
        List<Product> products = LocalDataBase.getInstance(this).productDao().getAll();

        //add to recycler view - Alibi

        RecyclerView rvCart = findViewById(R.id.rvCart);
        CartAdapter adapter = new CartAdapter(products);
        rvCart.setAdapter(adapter);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    protected void onStop() {
        //close connection with local db
        super.onStop();
    }
}
