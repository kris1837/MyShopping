package kz.shag.myshopping.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kz.shag.myshopping.R;
import kz.shag.myshopping.helpers.NavigationHelper;

public class SignInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(v -> {
            signIn();
        });
    }

    private void signIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        signInComplete();
                    } else {
                        signInFailure();
                    }
                });
    }

    private void signInComplete() {
        NavigationHelper.goToMainActivity(this);
    }

    private void signInFailure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setPositiveButton("Ok", (dialog, id) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
