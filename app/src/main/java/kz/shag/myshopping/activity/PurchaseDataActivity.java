package kz.shag.myshopping.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Product;
import kz.shag.myshopping.entity.Purchase;
import kz.shag.myshopping.helpers.NavigationHelper;
import kz.shag.myshopping.validators.TextValidator;

public class PurchaseDataActivity extends AppCompatActivity {
    private final String APP_PREFERENCES = "purchase_preferences";
    private final String APP_PREFERENCES_ADDRESS = "Address";
    private final String APP_PREFERENCES_PHONE = "Phone";

    private List<Product> productList;

    private EditText addressEditText;
    private EditText phoneEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_data);

        addressEditText = findViewById(R.id.address);
        phoneEditText = findViewById(R.id.phone);

        Intent intent = getIntent();
        productList = intent.getParcelableArrayListExtra("products");
        if (productList == null) productList = new ArrayList<>();

        TextValidator textValidator = new TextValidator(addressEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.replace(" ", "").isEmpty()) {
                    textView.setError("The field cannot be empty");
                }
            }
        };
        addressEditText.addTextChangedListener(textValidator);
        phoneEditText.addTextChangedListener(textValidator);

        SharedPreferences sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        addressEditText.setText(sPref.getString(APP_PREFERENCES_ADDRESS, ""));
        phoneEditText.setText(sPref.getString(APP_PREFERENCES_PHONE, ""));
    }

    public void purchase(View view) {
        if (addressEditText.getError() != null || phoneEditText.getError() != null) {
            return;
        }
        SharedPreferences sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(APP_PREFERENCES_ADDRESS, addressEditText.getText().toString());
        ed.putString(APP_PREFERENCES_PHONE, phoneEditText.getText().toString());
        ed.apply();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Purchase purchase = new Purchase();
        purchase.setAddress(addressEditText.getText().toString());
        purchase.setPhoneNumber(phoneEditText.getText().toString());
        purchase.setProducts(productList);
        double costSum = 0;
        for (Product product: productList) {
            costSum += product.getCost();
        }
        purchase.setFinalCost(costSum);
        db.collection("purchases").document(UUID.randomUUID().toString()).set(purchase);
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Order successfully placed")
               .setTitle("Successfully");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onPositiveButtonClick();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onPositiveButtonClick() {
        NavigationHelper.goToMainActivity(this);
    }
}
