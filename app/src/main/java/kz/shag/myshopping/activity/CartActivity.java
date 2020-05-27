package kz.shag.myshopping.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.adapters.CartAdapter;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.localDB.Initializer;
import kz.shag.myshopping.localDB.LocalDataBase;
import kz.shag.myshopping.localDB.ProductRepository;

public class CartActivity extends AppCompatActivity {
private ProductRepository productRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        final RecyclerView rvCart = findViewById(R.id.rvCart);

        productRepository = new ProductRepository(this);
        //Initializer.Init(productRepository);
        //load data from local db
        LiveData<List<Product>> liveData = LocalDataBase.getInstance(this).productDao().getAll();

        liveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                //add to recycler view - Alibi
                CartAdapter adapter = new CartAdapter(products);
                rvCart.setAdapter(adapter);
                rvCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });

    }



    @Override
    protected void onStop() {
        //close connection with local db
        super.onStop();
    }
}
