package kz.shag.myshopping.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kz.shag.myshopping.R;
import kz.shag.myshopping.validators.TextValidator;

public class PurchaseDataActivity extends AppCompatActivity {
    private final String APP_PREFERENCES = "purchase_preferences";
    private final String APP_PREFERENCES_ADDRESS = "Address";
    private final String APP_PREFERENCES_PHONE = "Phone";

    private EditText addressEditText;
    private EditText phoneEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_data);

        addressEditText = findViewById(R.id.address);
        phoneEditText = findViewById(R.id.phone);

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
    }
}
