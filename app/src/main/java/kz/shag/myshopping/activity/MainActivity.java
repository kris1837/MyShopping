package kz.shag.myshopping.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kz.shag.myshopping.adapters.ProductAdapter;
import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.localDB.ProductRepository;

public class MainActivity extends AppCompatActivity implements IProductClickListener{

    RecyclerView recyclerView;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Товары");
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
    }

    @Override
    public void onClick(Product product) {
        Toast.makeText(this, product.getTitle(), Toast.LENGTH_SHORT).show();
        if(productRepository == null){
            productRepository = new ProductRepository(this);
        }
        productRepository.insertProduct(product);
    }
}
