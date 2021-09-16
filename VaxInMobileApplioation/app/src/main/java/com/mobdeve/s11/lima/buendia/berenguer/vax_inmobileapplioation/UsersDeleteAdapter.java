package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersDeleteAdapter extends RecyclerView.Adapter<UsersDeleteViewHolder> {

    private DatabaseReference databaseReference;
    private ArrayList<Users> dataUsers;
    private HashMap hashMap;
    public UsersDeleteAdapter(ArrayList<Users> dataUsers){
        this.dataUsers = dataUsers;
    }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public UsersDeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_of_usersdelete, parent, false);
        databaseReference = FirebaseDatabase.getInstance("https://vax-in-60807-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");

        UsersDeleteViewHolder usersDeleteViewHolder = new UsersDeleteViewHolder(view);

        usersDeleteViewHolder.setDelClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(usersDeleteViewHolder.itemView.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to remove this persons schedule?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(dataUsers.get(usersDeleteViewHolder.getBindingAdapterPosition()).isFirstDose){
                                    hashMap = new HashMap();
                                    hashMap.put("secondSchedule","TBA");
                                    hashMap.put("secondTime","TBA");
                                    hashMap.put("vacSite","TBA");

                                    databaseReference.child(dataUsers.get(usersDeleteViewHolder.getBindingAdapterPosition()).uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            notifyItemChanged(usersDeleteViewHolder.getBindingAdapterPosition());
                                        }
                                    });
                                }
                                else{
                                    hashMap = new HashMap();
                                    hashMap.put("isScheduled",false);
                                    hashMap.put("firstSchedule","TBA");
                                    hashMap.put("firstTime","TBA");
                                    hashMap.put("secondSchedule","TBA");
                                    hashMap.put("secondTime","TBA");
                                    hashMap.put("vacSite","TBA");

                                    databaseReference.child(dataUsers.get(usersDeleteViewHolder.getBindingAdapterPosition()).uID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {

                                            Log.e("HEllo","DELEEEETE");
                                            notifyItemChanged(usersDeleteViewHolder.getBindingAdapterPosition());
                                            notifyDataSetChanged();
//                                            Intent intent = new Intent(view.getContext(),AdminEditSchedActivity.class);
//                                            view.getContext().startActivity(intent);

                                        }
                                    });

                                }


                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return usersDeleteViewHolder;

    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getBindingAdapterPosition()} which
     * will have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull UsersDeleteViewHolder holder, int position) {
        Users user = dataUsers.get(position);
        holder.setTvName(user.firstname +" "+ user.lastname);
        holder.setTvPriority(user.priority);
        holder.setTvAge(user.bday);
        holder.setTvBarangay(user.barangay);
        holder.setTvCity(user.city);
        holder.setTvSex(user.sex);
        holder.setIvAvatar(user.sex);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.dataUsers.size();
    }
}
