package com.urstrulygsw.chat.frangments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.urstrulygsw.chat.Adapter.UserAdapter;
import com.urstrulygsw.chat.R;
import com.urstrulygsw.chat.sampledata.User;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {


    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user,container,false);
       recyclerView=view.findViewById(R.id.rvUser);
         recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        userList=new ArrayList<>();

        getUsers();



        return view;
    }

    private void getUsers() {
        final FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("User");
        Toast.makeText(getContext(),currentUser.getUid(),Toast.LENGTH_LONG).show();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               userList.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    User user=dataSnapshot1.getValue(User.class);
                    if(!user.getId().equals(currentUser.getUid())){
                        userList.add(user);

                    }



                }

               userAdapter=new UserAdapter(getContext(),userList);
               recyclerView.setAdapter(userAdapter);
                //Toast.makeText(getContext(),currentUser.getUid(),Toast.LENGTH_LONG).show();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
