package com.example.personalsafetysystem.UserDashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.personalsafetysystem.BottomMenuFragments.ContactsFragment;
import com.example.personalsafetysystem.BottomMenuFragments.PlaceFragment;
import com.example.personalsafetysystem.BottomMenuFragments.ProfileFragment;
import com.example.personalsafetysystem.HomeFragment;
import com.example.personalsafetysystem.R;
import com.example.personalsafetysystem.databinding.ActivityMainBinding;

public class UserDashboard extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeBottomMenu:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.contactsBottomMenu:
                    replaceFragment(new ContactsFragment());
                    break;
                case R.id.placeBottomMenu:
                    replaceFragment(new PlaceFragment());
                    break;
                case R.id.profileBottomMenu:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}