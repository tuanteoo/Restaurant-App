package hou.edu.vn.ngvtuan.admin_foodapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hou.edu.vn.ngvtuan.admin_foodapp.R;
import hou.edu.vn.ngvtuan.admin_foodapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);

        // Check if user is already logged in
        if (sharedPreferences.getBoolean("is_logged_in", false)){
            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
            finish();
        }

        binding.btnLogin.setOnClickListener(view -> {
            String account = binding.logNameAccount.getText().toString().trim();
            String password = binding.logPassword.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(account, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            // Sign in success
                            sharedPreferences.edit().putBoolean("is_logged_in", true).apply();
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            finish();
                        }else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
}
