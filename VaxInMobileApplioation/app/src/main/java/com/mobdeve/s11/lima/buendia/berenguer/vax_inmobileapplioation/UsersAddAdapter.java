package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersAddAdapter extends RecyclerView.Adapter<UsersAddViewHolder> {

    private DatabaseReference databaseReference;

    private ArrayList<Users> dataUsers;
    public UsersAddAdapter(ArrayList<Users> dataUsers){
        this.dataUsers = dataUsers;
    }
    @NonNull
    @Override
    public UsersAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_of_usersadd, parent, false);
        databaseReference = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");

        UsersAddViewHolder usersAddViewHolder = new UsersAddViewHolder(view);

        usersAddViewHolder.setAddClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HashMap hashMap = new HashMap();
//                hashMap.put("isSelected",!dataUsers.get(usersAddViewHolder.getBindingAdapterPosition()).isSelected);
//
//                databaseReference.child(dataUsers.get(usersAddViewHolder.getBindingAdapterPosition()).uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if(task.isSuccessful()){
//                            Log.e("ADDED USER",dataUsers.get(usersAddViewHolder.getBindingAdapterPosition()).firstname);
//
//                        }
//                    }
//                });
                dataUsers.get(usersAddViewHolder.getBindingAdapterPosition()).isSelected = !dataUsers.get(usersAddViewHolder.getBindingAdapterPosition()).isSelected;
                notifyItemChanged(usersAddViewHolder.getBindingAdapterPosition());



            }


        });
        return usersAddViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAddViewHolder holder, int position) {

        Users user = dataUsers.get(position);
        holder.setTvName(user.firstname +" "+ user.lastname);
        holder.setTvPriority(user.priority);
        holder.setTvAge(user.bday);
        holder.setTvBarangay(user.barangay);
        holder.setTvCity(user.city);
        holder.setTvSex(user.sex);

        holder.setIvAddbutton(user.isSelected);

    }

    @Override
    public int getItemCount() {
        return this.dataUsers.size();
    }

    public void setData(ArrayList<Users> dataUsers){
        this.dataUsers.clear();
        this.dataUsers.addAll(dataUsers);
        notifyDataSetChanged();
    }
}
