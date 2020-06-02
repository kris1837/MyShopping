package kz.shag.myshopping.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Purchase;

public class HistoryActivity extends AppCompatActivity {

    private final String TAG = "HistoryActivity";

    private EditText phoneNumber;
    private List<Purchase> purchaseList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //закоментировал чтобы не было ошибки
        //phoneNumber = findViewById(R.id.phone_number);
    }

    //должен отрабатывать при нажатии на кнопку
    private void findPurchasesByPhoneNumber() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("purchases").whereEqualTo("phoneNumber", phoneNumber.getText()).get()
                .addOnSuccessListener(documentSnapshots -> {
                    if (documentSnapshots.isEmpty()) {
                        Log.d(TAG, "onSuccess: LIST EMPTY");
                        return;
                    } else {
                        List<Purchase> purchases = documentSnapshots.toObjects(Purchase.class);
                        purchaseList.addAll(purchases);
                        Log.d(TAG, "onSuccess: " + purchases);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show());
    }
}
