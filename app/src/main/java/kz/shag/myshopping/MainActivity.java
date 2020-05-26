package kz.shag.myshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.lang.UProperty;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import kz.shag.myshopping.entity.Product;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recyclerView);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //data from Firebase
        List<Product> products = new ArrayList<Product>();
        //

        ProductAdapter productAdapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
