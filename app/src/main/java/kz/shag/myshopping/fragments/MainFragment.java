package kz.shag.myshopping.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.activity.IProductClickListener;
import kz.shag.myshopping.adapters.ProductAdapter;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.localDB.ProductRepository;

public class MainFragment extends Fragment implements IProductClickListener {
    RecyclerView recyclerView;
    private ProductRepository productRepository;
    private List<Product> mProducts;

    private final String TAG = "MainActivity";

    List<Product> products = new ArrayList<Product>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);

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
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                });

        if (productRepository == null) {
            productRepository = new ProductRepository(getActivity());
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
}
