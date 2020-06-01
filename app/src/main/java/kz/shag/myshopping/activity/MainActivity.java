package kz.shag.myshopping.activity;

import androidx.annotation.NonNull;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kz.shag.myshopping.adapters.ProductAdapter;
import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.helpers.NavigationHelper;
import kz.shag.myshopping.localDB.ProductRepository;

public class MainActivity extends AppCompatActivity implements IProductClickListener{

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
        products.add(new Product("A","Description",2.3,"none",3));
        products.add(new Product("B","Description",2.3,"none",3));
        products.add(new Product("C","Description",2.3,"none",3));
        products.add(new Product("D","Description",2.3,"none",3));
        products.add(new Product("E","Description",2.3,"none",3));
        products.add(new Product("F","Description",2.3,"none",3));
        products.add(new Product("G","Description",2.3,"none",3));
        products.add(new Product("H","Description",2.3,"none",3));
        //

        ProductAdapter productAdapter = new ProductAdapter(products,this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
/*
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_main);*/

        ImageButton uiToCart = findViewById(R.id.uiToCart);
        final Activity activity = this;
        uiToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.goToCart(activity);
            }
        });
    }

    @Override
    public void onClick(Product product) {
        Toast.makeText(this, product.getTitle(), Toast.LENGTH_SHORT).show();
        if(productRepository == null){
            productRepository = new ProductRepository(this);
        }
        productRepository.insertProduct(product);
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
        Log.i("Your search: ",queryStr);
    }
}
