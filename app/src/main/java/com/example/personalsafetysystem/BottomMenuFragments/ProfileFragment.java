package com.example.personalsafetysystem.BottomMenuFragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.personalsafetysystem.Model.User;
import com.example.personalsafetysystem.R;
import com.example.personalsafetysystem.StepCounterHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private CircleImageView img;
    private Button btnEdit;
    private EditText edtPassword, edtEmail, edtPhone;
    private TextView textName, textEmail1, textPhone, heartBeatsTextView;
    private DatabaseReference userRef;
    private FirebaseUser currentUser;
    private User user;
    private StepCounterHelper stepCounterHelper;
    private TextView stepCountTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEdit = view.findViewById(R.id.btnEdit);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhone = view.findViewById(R.id.edtPhone);
        stepCountTextView = view.findViewById(R.id.NbrOfSteps);
         heartBeatsTextView = view.findViewById(R.id.Heartbeats);
        stepCounterHelper = new StepCounterHelper(requireContext(), stepCountTextView);
        stepCounterHelper.start();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
        }

        textName = view.findViewById(R.id.textName);
        textPhone = view.findViewById(R.id.edtPhone);
        textEmail1 = view.findViewById(R.id.textEmail);
        img = view.findViewById(R.id.profile_image);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    textName.setText(user.getName());
                    textPhone.setText(user.getPhone());
                    textEmail1.setText(currentUser.getEmail());
                    edtEmail.setText(currentUser.getEmail());
                    Glide.with(requireContext()).load(user.getImg_url()).placeholder(R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(R.drawable.common_google_signin_btn_icon_dark_normal).into(img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
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
        getHeartbeats();

    }
    private void getHeartbeats() {
        DatabaseReference heartRef = FirebaseDatabase.getInstance().getReference("HEART");
        heartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Récupérer la valeur du battement cardiaque en tant que String
                    String heartbeat = dataSnapshot.child("heartbeat").getValue(String.class);

                    // Assurez-vous d'avoir correctement initialisé votre TextView HeartBeats

                    // Afficher la valeur du battement cardiaque dans votre TextView
                    heartBeatsTextView.setText(heartbeat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer les erreurs d'annulation ici
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            img.setImageURI(imageUri);
        }
    }

    public void saveUserData() {
        String name = textName.getText().toString();
        String phone = textPhone.getText().toString();
        String textEmail = edtEmail.getText().toString();
        String textPassword = edtPassword.getText().toString();

        if (currentUser != null) {
            if (imageUri != null) {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + currentUser.getUid() + "/" + System.currentTimeMillis() + ".jpg");
                storageRef.putFile(imageUri);
            }

            if (user != null) {
                user.setName(name);
                user.setPhone(phone);

                if (imageUri != null) {
                    user.setImg_url(imageUri.toString());
                }

                currentUser.updateEmail(textEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            currentUser.updatePassword(textPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(requireContext(), "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(requireContext(), "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(requireContext(), "Failed to update password.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(requireContext(), "Failed to update email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        stepCounterHelper.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        stepCounterHelper.stop();
    }

}
