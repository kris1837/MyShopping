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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import kz.shag.myshopping.adapters.CartAdapter;
import kz.shag.myshopping.adapters.ProductAdapter;
import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.entity.Purchase;
import kz.shag.myshopping.fragments.HistoryFragment;
import kz.shag.myshopping.fragments.InfoFragment;
import kz.shag.myshopping.fragments.MainFragment;
import kz.shag.myshopping.fragments.ProfileFragment;
import kz.shag.myshopping.helpers.NavigationHelper;
import kz.shag.myshopping.localDB.ProductRepository;

public class MainActivity extends AppCompatActivity{

    private FrameLayout _frameLayout;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        _frameLayout = findViewById(R.id.uiFragmentHolder);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home: {
                        Fragment fragment = new MainFragment();
                        setFragment(fragment);
                        break;
                    }
                    case R.id.navigation_history:{
                        Fragment fragment = new HistoryFragment();
                        setFragment(fragment);
                        break;
                    }
                    case R.id.navigation_info: {
                        Fragment fragment = new InfoFragment();
                        setFragment(fragment);
                        break;
                    }
                    case R.id.navigation_profile:{
                        Fragment fragment = new ProfileFragment();
                        setFragment(fragment);
                        break;
                    }
                }
                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Товары");
        toolbar.setSubtitleTextColor(R.color.white);
        setSupportActionBar(toolbar);
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

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.uiFragmentHolder,fragment);
        fragmentTransaction.commit();
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
