package kz.shag.myshopping.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kz.shag.myshopping.adapters.CartAdapter;
import kz.shag.myshopping.adapters.ProductAdapter;
import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.helpers.NavigationHelper;
import kz.shag.myshopping.localDB.ProductRepository;

public class MainActivity extends AppCompatActivity implements IProductClickListener {

    RecyclerView recyclerView;
    private ProductRepository productRepository;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Товары");
        toolbar.setSubtitleTextColor(R.color.white);
        setSupportActionBar(toolbar);

        //data from Firebase
        List<Product> products = new ArrayList<Product>();
        products.add(new Product(1, "A", "Description", 2.3, "none", 3));
        products.add(new Product(2, "B", "Description", 2.3, "none", 3));
        products.add(new Product(3, "C", "Description", 2.3, "none", 3));
        products.add(new Product(4, "D", "Description", 2.3, "none", 3));
        products.add(new Product(5, "E", "Description", 2.3, "none", 3));
        products.add(new Product(6, "F", "Description", 2.3, "none", 3));
        products.add(new Product(7, "G", "Description", 2.3, "none", 3));
        products.add(new Product(8, "H", "Description", 2.3, "none", 3));
        //

        ProductAdapter productAdapter = new ProductAdapter(products, this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
/*
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_main);*/

    }

    @Override
    public void onClick(Product product) {
        if (productRepository == null) {
            productRepository = new ProductRepository(this);
        }
        LiveData<Product> liveData = productRepository.getById(product.getId());
        liveData.observe(this, new Observer<Product>() {
            //here come product by id
            @Override
            public void onChanged(Product liveProduct) {
                if(liveProduct == null){
                    productRepository.insertProduct(product);
                }
                else{
                    liveProduct.setQuantity(liveProduct.getQuantity() + 1);
                    productRepository.updateProduct(liveProduct);
                } 
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                doSearch(SearchManager.QUERY);
                break;
            case R.id.action_bin:
                final Activity activity = this;
                NavigationHelper.goToCart(activity);
            default:
                return super.onOptionsItemSelected(item);
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
        Log.i("Your search: ", queryStr);
    }
}
