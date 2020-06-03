package kz.shag.myshopping.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(v -> {
            signUp();
        });
    }

    private void signUp() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        if (currentUser == null) {
            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            signUpComplete();
                        } else {
                            signUpFailure();
                        }
                    });
        }
    }

    private void signUpComplete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("User has been created")
                .setTitle("Successfully");
        builder.setPositiveButton("Ok", (dialog, id) -> onPositiveButtonClick());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void signUpFailure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setPositiveButton("Ok", (dialog, id) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onPositiveButtonClick() {
        NavigationHelper.goToSignInActivity(this);
    }
}
