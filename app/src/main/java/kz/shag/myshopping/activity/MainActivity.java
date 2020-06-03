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

import com.google.firebase.firestore.FirebaseFirestore;

import kz.shag.myshopping.adapters.CartAdapter;
import kz.shag.myshopping.adapters.ProductAdapter;
import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.entity.Purchase;
import kz.shag.myshopping.helpers.NavigationHelper;
import kz.shag.myshopping.localDB.ProductRepository;

public class MainActivity extends AppCompatActivity implements IProductClickListener {

    RecyclerView recyclerView;
    private ProductRepository productRepository;
    private List<Product> mProducts;

    private final String TAG = "MainActivity";

    List<Product> products = new ArrayList<Product>();

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").get()
                .addOnSuccessListener(documentSnapshots -> {
                    if (!documentSnapshots.isEmpty()) {
                        List<Product> productsFromDb = documentSnapshots.toObjects(Product.class);
                        products.addAll(productsFromDb);
                        Log.d(TAG, "onSuccess: " + products.get(0).getTitle());

                        ProductAdapter productAdapter = new ProductAdapter(products, this);
                        recyclerView.setAdapter(productAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    }
                });

        if (productRepository == null) {
            productRepository = new ProductRepository(this);
        }

        LiveData<List<Product>> liveData = productRepository.getAllProducts();
        liveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mProducts = products;
            }
        });
    }

    @Override
    public void onClick(Product product) {
        Product cartProd = null;
        for (int i =0; i< mProducts.size(); i++){
            if(product.getId() == mProducts.get(i).getId()){
                cartProd = mProducts.get(i);
                break;
            }
        }

        if(cartProd != null){
            int count = cartProd.getQuantity();
            cartProd.setQuantity(count + 1);
            productRepository.updateProduct(cartProd);
        } else {
            int count = product.getQuantity();
            if(count != 1){
                product.setQuantity( 1 );
            }
            productRepository.insertProduct(product);
        }

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
                NavigationHelper.goToCart(MainActivity.this);
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
