package com.urstrulygsw.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    TextView txtEmail,txtUsername;
    TextView txtPassword;
    Button btnSignUp;
    String strEmail, strPassword,strUsername;
    TextView linkLogin;
    FirebaseUser firebaseUser;

    private FirebaseAuth mAuth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        btnSignUp=findViewById(R.id.btnSignUp);
        txtUsername=findViewById(R.id.txtUsername);
        linkLogin=findViewById(R.id.linkLogin);




        linkLogin.setMovementMethod(LinkMovementMethod.getInstance());

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });

        mAuth=FirebaseAuth.getInstance();







        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strEmail=txtEmail.getText().toString();
                strPassword=txtPassword.getText().toString();
                strUsername=txtUsername.getText().toString();

                if(strEmail.isEmpty() || strUsername.isEmpty() || strPassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter all fields",Toast.LENGTH_SHORT).show();
                }else{


                createAccount(strUsername,strEmail,strPassword);}






            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       // FirebaseUser currentUser = mAuth.getCurrentUser();
        //intent
       // Toast.makeText(this,"already in"+currentUser,Toast.LENGTH_SHORT).show();

        //best line ever. Luved it. to get into mainpage directly.
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

    }

    private void createAccount(final String username, String strEmail, String strPassword){




       mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in u Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user!=null;

                            String strUserId=user.getUid();
                            reference=FirebaseDatabase.getInstance().getReference("User").child(strUserId);
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id",strUserId);
                            hashMap.put("username",username);
                            hashMap.put("imageUrl","default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                            Toast.makeText(MainActivity.this,"Successfully added your account"+strUserId,Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this,"Authentication failed.",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

}
