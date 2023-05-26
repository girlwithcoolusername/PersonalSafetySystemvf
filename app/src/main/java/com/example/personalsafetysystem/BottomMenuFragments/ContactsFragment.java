package com.example.personalsafetysystem.BottomMenuFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalsafetysystem.Adapters.GridAdapter;
import com.example.personalsafetysystem.LoginActivity;
import com.example.personalsafetysystem.Model.User;
import com.example.personalsafetysystem.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ContactsFragment extends Fragment {
    RecyclerView recyclerView;
    GridAdapter gridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_contact, container, false);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            String uid = user.getUid();
            FirebaseRecyclerOptions<User> options =
                    new FirebaseRecyclerOptions.Builder<User>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("contacts_list"), User.class)
                            .build();
            gridAdapter = new GridAdapter(options);
            recyclerView.setAdapter(gridAdapter);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (gridAdapter != null) {
            gridAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (gridAdapter != null) {
            gridAdapter.stopListening();
        }
    }
}
