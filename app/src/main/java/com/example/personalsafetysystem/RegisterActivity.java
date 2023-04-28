package com.example.personalsafetysystem;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.personalsafetysystem.LoginActivity;
import com.example.personalsafetysystem.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    EditText textName, textEmail, textPassword, edtConfirmPassword,textPhone;
    Spinner spinner;
    TextView login;
    String txtFullName, txtEmail, txtName,txtPhone, txtPassword, txtConfirmPassword, selectedRole;

    Button btnSignUpAcc;

    String phonePattern = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        login = findViewById(R.id.login);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textPhone = findViewById(R.id.textPhone);
        textPassword = findViewById(R.id.textPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        spinner = findViewById(R.id.textRole);


        btnSignUpAcc = findViewById(R.id.btnSignUpAcc);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Créer une liste d'options pour le Spinner
        List<String> options = new ArrayList<>();
        options.add("Principal user");
        options.add("Emergency contact");

        // Créer un ArrayAdapter pour alimenter le Spinner avec la liste d'options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);

        // Spécifier un layout pour afficher chaque élément de la liste déroulante
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Appliquer l'ArrayAdapter au Spinner
        spinner.setAdapter(adapter);

        // Ajouter un écouteur pour gérer les sélections de l'utilisateur
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Faire quelque chose lorsque l'utilisateur sélectionne un élément de la liste déroulante
                String selectedOption = options.get(position);
                Log.d("Spinner", "Option selected: " + selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Gérer le cas où aucun élément n'est sélectionné

            }
        });


        btnSignUpAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtName = textName.getText().toString().trim();
                txtPhone = textPhone.getText().toString().trim();
                txtEmail = textEmail.getText().toString().trim();
                txtPassword = textPassword.getText().toString().trim();
                txtConfirmPassword = edtConfirmPassword.getText().toString().trim();
                selectedRole = spinner.getSelectedItem().toString();

                if (!TextUtils.isEmpty(txtName)) {
                    if (!TextUtils.isEmpty(txtPhone)) {
                        if (txtPhone.matches(phonePattern)) {
                            if (!TextUtils.isEmpty(txtEmail)) {
                                if (txtEmail.matches(emailPattern)) {
                                    if (!TextUtils.isEmpty(txtPassword)) {
                                        if (!TextUtils.isEmpty(txtConfirmPassword)) {
                                            if (txtConfirmPassword.equals(txtPassword)) {
                                                SignUpUser(txtName, txtPhone, txtEmail, txtPassword, selectedRole);
                                            } else {
                                                edtConfirmPassword.setError("Confirm Password and Password should be same.");
                                            }
                                        } else {
                                            edtConfirmPassword.setError("Confirm Password Field can't be empty");
                                        }
                                    } else {
                                        textPassword.setError("Password Field can't be empty");
                                    }

                                } else {
                                    textEmail.setError("Enter a valid Email Address");
                                }
                            } else {
                                textEmail.setError("Email Field can't be empty");
                            }
                        } else {
                            textPhone.setError("Enter a valid Phone Number");

                        }
                    }else {
                        textPhone.setError("Phone Field can't be empty");

                    }
                }
                else {
                    textName.setError("Name Field can't be empty");

                }
            }
        });

    }

    private void SignUpUser(String txtName, String textPhone,String txtEmail, String txtPassword, String selectedRole) {
        btnSignUpAcc.setVisibility(View.INVISIBLE);


        mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");


                        Map<String, Object> map = new HashMap<>();
                        map.put("UID", userId);
                        map.put("name", txtName);
                        map.put("role", selectedRole);
                        map.put("phone", textPhone);
                        map.put("contacts_list", 0);
                        map.put("heartbeats", 75);
                        map.put("nbrOfSteps", 1000);

                        userRef.setValue(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegisterActivity.this, "Sign Up Successful !", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        btnSignUpAcc.setVisibility(View.VISIBLE);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        btnSignUpAcc.setVisibility(View.VISIBLE);
                    }
                });
    }




}