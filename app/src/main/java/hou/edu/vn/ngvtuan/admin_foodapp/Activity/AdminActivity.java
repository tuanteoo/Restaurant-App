package hou.edu.vn.ngvtuan.admin_foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import hou.edu.vn.ngvtuan.admin_foodapp.Fragment.FoodFragment;
import hou.edu.vn.ngvtuan.admin_foodapp.Fragment.HistoryFragment;
import hou.edu.vn.ngvtuan.admin_foodapp.Fragment.OrderFragment;
import hou.edu.vn.ngvtuan.admin_foodapp.R;
import hou.edu.vn.ngvtuan.admin_foodapp.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new FoodFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.food){
                replaceFragment(new FoodFragment());
            }
            else if (id == R.id.order){
                replaceFragment(new OrderFragment());
            }
            else if (id == R.id.his_order){
                replaceFragment(new HistoryFragment());
            }
            return true;
        });
        binding.btnLogout.setOnClickListener(view -> {
            // Get SharedPreferences instance
            SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);

            // Edit and clear login state
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged_in", false);
            editor.apply();

            // Navigate back to LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish(); // Close AdminActivity
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_admin,fragment);
        fragmentTransaction.commit();
    }
}