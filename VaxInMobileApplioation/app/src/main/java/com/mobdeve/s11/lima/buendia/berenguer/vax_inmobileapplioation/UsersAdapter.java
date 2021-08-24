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
        return;
    }

    @Override
    public int getItemCount() {
        return this.dataUsers.size();
    }
}
