package kz.shag.myshopping.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.adapters.CartAdapter;
import kz.shag.myshopping.adapters.OnCartAdapterEventListener;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.helpers.NavigationHelper;
import kz.shag.myshopping.localDB.Initializer;
import kz.shag.myshopping.localDB.LocalDataBase;
import kz.shag.myshopping.localDB.ProductRepository;

public class CartActivity extends AppCompatActivity implements OnCartAdapterEventListener, View.OnClickListener {
    private ProductRepository productRepository;

    List<Product> productArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        final RecyclerView recyclerView = findViewById(R.id.rvCart);
        Button purchaseBtn = findViewById(R.id.purchaseBtn);
        purchaseBtn.setOnClickListener(this);

        productRepository = new ProductRepository(this);
        //Initializer.Init(productRepository);
        //load data from local db
        LiveData<List<Product>> liveData = LocalDataBase.getInstance(this).productDao().getAll();
        final OnCartAdapterEventListener listener = this;

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setHasFixedSize(true);

        liveData.observe(this, new Observer<List<Product>>() {

            @Override
            public void onChanged(@Nullable List<Product> products) {
                //add to recycler view - Alibi
                productArrayList = products;
                CartAdapter adapter = new CartAdapter();
                adapter.setmProducts(products);
                adapter.setListener(listener);

                recyclerView.setAdapter(adapter);
            }
        });


    }



    @Override
    protected void onStop() {
        //close connection with local db
        super.onStop();
    }

    @Override
    public void onPlusButtonClick(Product product) {
        Toast.makeText(getApplicationContext(), "onPlusButtonClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMinusButtonClick(Product product) {
        Toast.makeText(getApplicationContext(), "onMinusButtonClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteButtonClick(Product product) {
        Toast.makeText(getApplicationContext(), "onDeleteButtonClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        NavigationHelper.goToPurchase(this, (ArrayList<Product>) productArrayList);
    }
}
