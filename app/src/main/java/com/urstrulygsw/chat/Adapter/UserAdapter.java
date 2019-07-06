package com.urstrulygsw.chat.Adapter;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.urstrulygsw.chat.R;
import com.urstrulygsw.chat.sampledata.User;

import java.util.List;



public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
   private Context context;
    private List<User> userList;

   public UserAdapter(Context context, List<User> userList) {
      this.context = context;
      this.userList = userList;
   }



   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view=LayoutInflater.from(context).inflate(R.layout.user_item,viewGroup,false);
      return new UserAdapter.ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       User user=userList.get(i);
       viewHolder.var_txtUser.setText(user.getUsername());
      // viewHolder.txtUser.setText("hi");
       if(user.getImageUrl().equals("default")){
           viewHolder.var_imgUser.setImageResource(R.mipmap.ic_launcher);
       }
       else{
           Glide.with(context).load(user.getImageUrl()).into(viewHolder.var_imgUser);
       }

   }

   @Override
   public int getItemCount() {
      return userList.size();
      // return 3;
   }


public class ViewHolder extends RecyclerView.ViewHolder{

       public TextView var_txtUser;
       public ImageView var_imgUser;
   public ViewHolder(@NonNull View itemView) {
      super(itemView);

       var_txtUser=itemView.findViewById(R.id.txtUser);
       var_imgUser=itemView.findViewById(R.id.imgUser);
   }





}}
