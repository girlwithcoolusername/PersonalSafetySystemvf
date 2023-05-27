package com.example.personalsafetysystem.UserDashboard;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.personalsafetysystem.BottomMenuFragments.ContactsFragment;
import com.example.personalsafetysystem.BottomMenuFragments.EmergencyFragment;
import com.example.personalsafetysystem.BottomMenuFragments.ProfileFragment;
import com.example.personalsafetysystem.HomeFragment;
import com.example.personalsafetysystem.R;
import com.example.personalsafetysystem.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserDashboard extends AppCompatActivity implements HospitalFragment.HospitalsListener, DoctorFragment.DoctorsListener , PoliceFragment.PolicesListener , PharmacyFragment.PharmacysListener {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        setMenuItemColor(binding.bottomNavigationView, R.id.homeBottomMenu, true); // Set the active item color

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fab:
                    replaceFragment(new EmergencyFragment());
                    setMenuItemColor(binding.bottomNavigationView,R.id.fab,true);
                case R.id.homeBottomMenu:
                    replaceFragment(new HomeFragment());
                    setMenuItemColor(binding.bottomNavigationView, R.id.homeBottomMenu, true);
                    break;
                case R.id.contactsBottomMenu:
                    replaceFragment(new ContactsFragment());
                    setMenuItemColor(binding.bottomNavigationView, R.id.contactsBottomMenu, true);
                    break;
                case R.id.placeBottomMenu:
                    replaceFragment(new PlaceFragment());
                    setMenuItemColor(binding.bottomNavigationView, R.id.placeBottomMenu, true);
                    break;
                case R.id.profileBottomMenu:
                    replaceFragment(new ProfileFragment());
                    setMenuItemColor(binding.bottomNavigationView, R.id.profileBottomMenu, true);
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

    private void setMenuItemColor(BottomNavigationView navigationView, int menuItemId, boolean isActive) {
        int activeColor = ContextCompat.getColor(this, R.color.colorPurple);
        int inactiveColor = ContextCompat.getColor(this, R.color.colorWhite);
        int color = isActive ? activeColor : inactiveColor;

        navigationView.getMenu().findItem(menuItemId).getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
    @Override
    public void gotoHospitalDetails(Hospital hospital) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DetailsHospitalFragment.newInstance(hospital))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoDoctorDetails(Doctor doctor) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DetailsDoctorFragment.newInstance(doctor))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoPoliceDetails(Police police) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DetailsPoliceFragment.newInstance(police))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoPharmacyDetails(Pharmacy pharmacy) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DetailsPharmacyFragment.newInstance(pharmacy))
                .addToBackStack(null)
                .commit();
    }

}
