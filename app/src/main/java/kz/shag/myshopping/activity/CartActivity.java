package kz.shag.myshopping.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        final RecyclerView recyclerView = findViewById(R.id.rvCart);
        Button purchaseBtn = findViewById(R.id.purchaseBtn);
        purchaseBtn.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.cart_toolbar);
        toolbar.setTitle("Корзина");
        toolbar.setSubtitleTextColor(R.color.white);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        productRepository = new ProductRepository(this);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void doSearch(String queryStr) {
        Toast.makeText(this, queryStr, Toast.LENGTH_LONG).show();
        Log.i("Your search: ",queryStr);
    }


    @Override
    public void onPlusButtonClick(Product product) {
        product.setQuantity(product.getQuantity() + 1);
        productRepository.updateProduct(product);
    }

    @Override
    public void onMinusButtonClick(Product product) {
        if(product.getQuantity() > 1){
            product.setQuantity(product.getQuantity() - 1);
            productRepository.updateProduct(product);
        }else{
            productRepository.deleteProduct(product);
        }
    }

    @Override
    public void onDeleteButtonClick(Product product) {
        productRepository.deleteProduct(product);
    }

    @Override
    public void onClick(View v) {
        NavigationHelper.goToPurchase(this, (ArrayList<Product>) productArrayList);
    }
}
