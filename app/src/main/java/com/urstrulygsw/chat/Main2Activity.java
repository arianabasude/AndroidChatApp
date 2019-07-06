package com.urstrulygsw.chat;

import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.urstrulygsw.chat.frangments.ChatFragment;
import com.urstrulygsw.chat.frangments.UserFragment;
import com.urstrulygsw.chat.sampledata.User;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

FirebaseUser firebaseUser;
ImageView imgProfile;
TextView txtDisplayUsername;
DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgProfile=findViewById(R.id.imgProfile);
        txtDisplayUsername=findViewById(R.id.txtDisplayUsername);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());




       databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               //User user=dataSnapshot.getValue(User.class);
                txtDisplayUsername.setText(dataSnapshot.child("username").getValue().toString());
                //txtDisplayUsername.setText(user.getStrUsername());

                if(dataSnapshot.child("imageUrl").getValue().toString().equals("default")){
                    imgProfile.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    //Glide.with(Main2Activity.this).load(user.getStrImageUrl()).into(imgProfile);
                }

               /* if(user.getStrImageUrl().equals("default")){
                    imgProfile.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    Glide.with(Main2Activity.this).load(user.getStrImageUrl()).into(imgProfile);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });


        TabLayout tabLayout=findViewById(R.id.tabLayout);
        ViewPager viewPager=findViewById(R.id.viewPager);
        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new ChatFragment(),"Chats");
        viewPageAdapter.addFragment(new UserFragment(),"Users");

        viewPager.setAdapter(viewPageAdapter);

      tabLayout.setupWithViewPager(viewPager);







    }
    //displaying three dots (Options Menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.btnLogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
            finish();



        }
        return false; //for staying it
    }








    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"hey "+firebaseUser.getUid(),Toast.LENGTH_LONG).show();
        if (firebaseUser == null) {
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
            finish();



        }}

        class ViewPageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentArrayList;
        private ArrayList<String> stringArrayList;


            public ViewPageAdapter(FragmentManager fm) {
                super(fm);
                this.fragmentArrayList=new ArrayList<>();
                this.stringArrayList=new ArrayList<>();



            }

            @Override
            public Fragment getItem(int i) {
                return fragmentArrayList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentArrayList.size();
            }

            public void addFragment(Fragment fragment,String title){
                fragmentArrayList.add(fragment);
                stringArrayList.add(title);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return stringArrayList.get(position);

            }
        }


}











        /*

    }

    //displaying three dots (Options Menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.btnLogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
            finish();



        }
        return false; //for staying it
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        imgProfile=findViewById(R.id.imgProfile);
        txtDisplayUsername=findViewById(R.id.txtDisplayUsername);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        txtDisplayUsername.setText(firebaseUser.getDisplayName());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                txtDisplayUsername.setText(user.getStrUsername());

                if(user.getStrImageUrl().equals("default")){
                    imgProfile.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    Glide.with(Main2Activity.this).load(user.getStrImageUrl()).into(imgProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/




