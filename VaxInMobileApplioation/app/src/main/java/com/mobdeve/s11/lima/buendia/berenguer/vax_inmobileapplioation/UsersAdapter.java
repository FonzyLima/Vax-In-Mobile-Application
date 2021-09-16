package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private ArrayList<Users> dataUsers;
    public UsersAdapter(ArrayList<Users> dataUsers){
        this.dataUsers = dataUsers;
    }
    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_of_users, parent,false);

        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        Users user = dataUsers.get(position);
        holder.setTvName(user.firstname+" "+user.lastname);
        holder.setTvBday(user.bday);
        holder.setTvPriority(user.priority);
        holder.setTvBarangay(user.barangay);
        holder.setTvSex(user.sex);
        holder.setTvCity(user.city);
        holder.setIvAvatar(user.sex);
    }

    @Override
    public int getItemCount() {
        return this.dataUsers.size();
    }
}
