package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAddViewHolder extends RecyclerView.ViewHolder {
    private TextView tvName, tvPriority, tvSex, tvAge, tvBarangay, tvCity;
    private ImageView ivAddbutton, ivAvatar;

    public UsersAddViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initizalizes components
        this.tvName = itemView.findViewById(R.id.tv_rowadd_name);
        this.tvAge = itemView.findViewById(R.id.tv_rowadd_age);
        this.tvPriority = itemView.findViewById(R.id.tv_rowadd_group);
        this.tvSex = itemView.findViewById(R.id.tv_rowadd_gender);
        this.tvBarangay = itemView.findViewById(R.id.tv_rowadd_barangay);
        this.tvCity = itemView.findViewById(R.id.tv_rowadd_city);

        this.ivAddbutton = itemView.findViewById(R.id.iv_rowadd_btn);
        this.ivAvatar = itemView.findViewById(R.id.iv_rowadd_picture);


    }
    // Sets viewholder's data with user
    public void setTvName(String name){
        this.tvName.setText(name);
    }
    public void setTvPriority(String priority){
        this.tvPriority.setText(priority);
    }
    public void setTvSex(String sex){
        this.tvSex.setText(sex);
    }
    public void setTvAge(String age){
        this.tvAge.setText(age);
    }
    public void setTvBarangay(String barangay){
        this.tvBarangay.setText(barangay);
    }
    public void setTvCity(String city){
        this.tvCity.setText(city);
    }

    // Sets Avatar based on user's sex
    public void setIvAvatar(String sex){

        if(sex.equals("Male")){
            this.ivAvatar.setImageResource(R.drawable.mavatar);
        }
        else{
            this.ivAvatar.setImageResource(R.drawable.favatar);
        }

    }

    // Changes image depending if user is selected
    public void setIvAddbutton(boolean added){
        if(added){
            this.ivAddbutton.setImageResource(R.drawable.row_check);
        }
        else{
            this.ivAddbutton.setImageResource(R.drawable.row_add);
        }
    }

    public void setAddClick(View.OnClickListener click){
        this.ivAddbutton.setOnClickListener(click);
    }
}
