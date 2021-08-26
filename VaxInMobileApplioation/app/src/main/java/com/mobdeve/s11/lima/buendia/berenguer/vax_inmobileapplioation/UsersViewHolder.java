package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersViewHolder extends RecyclerView.ViewHolder {
    private TextView tvName, tvPriority, tvSex, tvBday, tvBarangay, tvCity;
    private ImageView ivAvatar;
    private LinearLayout ll_users;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tvName = itemView.findViewById(R.id.tv_row_name);
        this.tvPriority = itemView.findViewById(R.id.tv_row_group);
        this.tvSex = itemView.findViewById(R.id.tv_row_gender);
        this.tvBday = itemView.findViewById(R.id.tv_row_age);
        this.tvBarangay = itemView.findViewById(R.id.tv_row_barangay);
        this.tvCity = itemView.findViewById(R.id.tv_row_city);

        this.ivAvatar = itemView.findViewById(R.id.iv_row_picture);

        this.ll_users = itemView.findViewById(R.id.ll_users);
    }

    public void setTvName(String name){
        this.tvName.setText(name);
    }
    public void setTvPriority(String priority){
        this.tvPriority.setText(priority);
    }
    public void setTvSex(String sex){
        this.tvSex.setText(sex);
    }
    public void setTvBday(String bday){
        this.tvBday.setText(bday);
    }
    public void setTvBarangay(String barangay){
        this.tvBarangay.setText(barangay);
    }
    public void setTvCity(String city){
        this.tvCity.setText(city);
    }

    public void setIvAvatar(int picture){
        this.ivAvatar.setImageResource(picture);
    }


    public void setUsersOnClick(View.OnClickListener click){
        this.ll_users.setOnClickListener(click);
    }


}
