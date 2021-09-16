package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersDeleteViewHolder extends RecyclerView.ViewHolder {
    private TextView tvName, tvPriority, tvSex, tvAge, tvBarangay, tvCity;
    private ImageView ivDelbutton, ivAvatar;

    public UsersDeleteViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tvName = itemView.findViewById(R.id.tv_rowdel_name);
        this.tvPriority = itemView.findViewById(R.id.tv_rowdel_group);
        this.tvSex = itemView.findViewById(R.id.tv_rowdel_gender);
        this.tvAge = itemView.findViewById(R.id.tv_rowdel_age);
        this.tvBarangay = itemView.findViewById(R.id.tv_rowdel_barangay);
        this.tvCity = itemView.findViewById(R.id.tv_rowdel_city);

        this.ivDelbutton = itemView.findViewById(R.id.iv_rowdel_btn);
        this.ivAvatar = itemView.findViewById(R.id.iv_rowdel_picture);
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
    public void setTvAge(String age){
        this.tvAge.setText(age);
    }
    public void setTvBarangay(String barangay){
        this.tvBarangay.setText(barangay);
    }
    public void setTvCity(String city){
        this.tvCity.setText(city);
    }

    public void setIvAvatar(String sex){

        if(sex.equals("Male")){
            this.ivAvatar.setImageResource(R.drawable.mavatar);
        }
        else{
            this.ivAvatar.setImageResource(R.drawable.favatar);
        }

    }

    public void setIvDelbutton(boolean added){
        if(added){
            this.ivDelbutton.setImageResource(R.drawable.row_check);
        }
        else{
            this.ivDelbutton.setImageResource(R.drawable.row_delete);
        }
    }

    public void setDelClick(View.OnClickListener click){
        this.ivDelbutton.setOnClickListener(click);
    }
}
