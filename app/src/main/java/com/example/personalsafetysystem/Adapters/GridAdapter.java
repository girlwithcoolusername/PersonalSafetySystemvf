
        package com.example.personalsafetysystem.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.personalsafetysystem.Model.User;
import com.example.personalsafetysystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class GridAdapter extends FirebaseRecyclerAdapter<User, GridAdapter.MyViewHolder> {

    public GridAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull User model) {
        // Bind the data to the views in your grid item layout
        holder.name.setText(model.getName());
        holder.number.setText(model.getPhone());
        holder.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        Glide.with(holder.img.getContext())
                .load(model.getImg_url())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycontact, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, number, email;
        Button btnEdit, btnDelete;
        RelativeLayout itemLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.nametext);
            number = itemView.findViewById(R.id.numbertext);
            email = itemView.findViewById(R.id.emailtext);

            itemLayout = itemView.findViewById(R.id.item);
        }
    }
}
