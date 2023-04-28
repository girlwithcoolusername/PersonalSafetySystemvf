package com.example.personalsafetysystem.UserDashboard;

<<<<<<< HEAD
import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
=======
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

>>>>>>> a1a1fd5f9b1fe95d4a3cb19fe0bb7426bef456b5
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.personalsafetysystem.LoginActivity;
import com.example.personalsafetysystem.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements OnMapReadyCallback {

    private Button btn_start_route;
    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap myMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    //map search view
    private SearchView mapSearchView ;

    private ImageView iv_get_location,btnLogout;

    //pop up start route menu
    Dialog myDialog;
    private EditText etFromLocation,etToLocation ;
    private Button btnGetDirection ;


<<<<<<< HEAD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        btnLogout = findViewById(R.id.logout);
        btn_start_route = findViewById(R.id.btn_start_route);
        mapSearchView = findViewById(R.id.mapSearch);
        iv_get_location = findViewById(R.id.iv_get_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastlocation();

        //handle the log out button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //handle the start route button
        myDialog = new Dialog(this);
        btn_start_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowPopup();
            }
        });

        //handle the get current location button
        iv_get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastlocation();
            }
        });

        // initialize the map search view
        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null ;

                if (location != null){
                    Geocoder geocoder = new Geocoder(UserDashboard.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
=======
    private CardView btnLogout;
    private CardView btnHome;
    private CardView btnNotifs;
    private CardView btnProfile;
    private CardView btnTracking;
    private CardView btnListusers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_dashboard);
        btnLogout = findViewById(R.id.card_logout);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
>>>>>>> a1a1fd5f9b1fe95d4a3cb19fe0bb7426bef456b5
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    //the shop up start route menu


    public void ShowPopup() {
        myDialog.setContentView(R.layout.activity_start_route);
        etFromLocation =(EditText) myDialog.findViewById(R.id.etFromLocation);
        etToLocation =(EditText) myDialog.findViewById(R.id.etToLocation);
        btnGetDirection = (Button) myDialog.findViewById(R.id.btnGetDirection);
        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userLocation = etFromLocation.getText().toString();
                String userDestination = etToLocation.getText().toString();

                if (userLocation.equals("") || userDestination.equals("")) {
                    Toast.makeText(UserDashboard.this,"Please enter your location and destination", Toast.LENGTH_SHORT).show();
                }else {
                    getDirections(userLocation, userDestination);
                }
            }

            private void getDirections(String from, String to) {
                try{
                    Uri uri = Uri.parse("https://www.google.com/maps/dir/"+from+"/"+to);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }catch (ActivityNotFoundException exception ){
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=en&gl=US");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void getLastlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_PERMISSION_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location ;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(UserDashboard.this);
                }
            }
        });
    }

    //get current location
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap ;
        LatLng sydney = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        myMap.addMarker(new MarkerOptions().position(sydney).title("My Location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastlocation();
            }else {
                Toast.makeText(this,"Location permission is denied, please allow the permission",Toast.LENGTH_SHORT).show();

            }
        }

    }


}