package com.example.personalsafetysystem.UserDashboard;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.personalsafetysystem.Model.User;
import com.example.personalsafetysystem.R;
import com.example.personalsafetysystem.StepCounterHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private StepCounterHelper stepCounterHelper;

    Uri imageUri;
    private CircleImageView img;
    private Button btnEdit;
    private EditText edtPassword,edtEmail,edtPhone;
    private TextView textName,textEmail1,textPhone,nbrOfSteps;
    private DatabaseReference userRef;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        btnEdit = findViewById(R.id.btnEdit);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        nbrOfSteps = findViewById(R.id.NbrOfSteps);



        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        textName = findViewById(R.id.textName);
        textPhone = findViewById(R.id.edtPhone);
        textEmail1 = findViewById(R.id.textEmail);
        img = findViewById(R.id.profile_image);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    textName.setText(user.getName());
                    textPhone.setText(user.getPhone());
                    textEmail1.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    Glide.with(img.getContext()).load(user.getImg_url()).placeholder(com.google.firebase.appcheck.interop.R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal).into(img);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfile.this, "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData(view);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            CircleImageView imageView =findViewById(R.id.img1);
            imageView.setImageURI(imageUri);
        }
    }

    public void saveUserData(View view) {
        String name = textName.getText().toString();
        String phone = textPhone.getText().toString();
        String textEmail = textEmail1.getText().toString();
        if(imageUri!=null)
        {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + System.currentTimeMillis() + ".jpg");
            storageRef.putFile(imageUri);
        }


        if (user != null) {
            user.setName(name);
            user.setPhone(phone);
            if(imageUri!=null)
            {
                user.setImg_url(imageUri.toString());

            }


            FirebaseAuth.getInstance().getCurrentUser().updateEmail(textEmail);
            userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserProfile.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserProfile.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }






}
