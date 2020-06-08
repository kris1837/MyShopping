package kz.shag.myshopping.activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import kz.shag.myshopping.R;
import kz.shag.myshopping.di.AppModule;
import kz.shag.myshopping.di.DaggerAppComponent;
import kz.shag.myshopping.fragments.HistoryFragment;
import kz.shag.myshopping.fragments.InfoFragment;
import kz.shag.myshopping.fragments.MainFragment;
import kz.shag.myshopping.fragments.ProfileFragment;
import kz.shag.myshopping.helpers.NavigationHelper;

public class MainActivity extends AppCompatActivity {

    @Inject
    SharedPreferences preferences;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .build().inject(this);

        Fragment fragment = new MainFragment();
        setFragment(fragment);

        String address = preferences.getString("Address", "");

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home: {
                        Fragment fragment = new MainFragment();
                        getSupportActionBar().show();
                        setFragment(fragment);
                        break;
                    }
                    case R.id.navigation_history: {
                        Fragment fragment = new HistoryFragment();
                        getSupportActionBar().hide();
                        setFragment(fragment);
                        break;
                    }
                    case R.id.navigation_info: {
                        Fragment fragment = new InfoFragment();
                        getSupportActionBar().hide();
                        setFragment(fragment);
                        break;
                    }
                    case R.id.navigation_profile: {
                        Fragment fragment = new ProfileFragment();
                        getSupportActionBar().hide();
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.uiFragmentHolder, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);

        // Associate searchable configuration with the SearchView
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            if (searchManager != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
            }
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.uiFragmentHolder);
                if (fragment instanceof MainFragment) {
                    ((MainFragment) fragment).setSearch(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.uiFragmentHolder);
                if (fragment instanceof MainFragment) {
                    ((MainFragment) fragment).setSearch(newText);
                }
                return false;
            }
        });
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
