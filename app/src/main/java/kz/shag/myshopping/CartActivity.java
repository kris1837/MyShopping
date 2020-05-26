package kz.shag.myshopping;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load data from local db


        //add to recycler view - Alibi
    }



    @Override
    protected void onStop() {
        //close connection with local db
        super.onStop();
    }
}
