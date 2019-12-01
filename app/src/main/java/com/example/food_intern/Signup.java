package com.example.food_intern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food_intern.Model.UserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText Name,Email,Password,MobNo,RollNo,Branch,Hostel,RoomNo;
    String name,email,password,mobNo,rollNo,branch,hostel,roomNo;
    DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child("User");
    DatabaseReference loginRef= FirebaseDatabase.getInstance().getReference().child("UserLoginDetails");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth= FirebaseAuth.getInstance();
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        MobNo=findViewById(R.id.phone_num);
        RollNo=findViewById(R.id.rno);
        Branch=findViewById(R.id.branch);
        Hostel=findViewById(R.id.hosteln);
        RoomNo=findViewById(R.id.room_no);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void signUp(View view)
    {
        name=Name.getText().toString();
        email=Email.getText().toString();
        password=Password.getText().toString();
        mobNo=MobNo.getText().toString();
        rollNo=RollNo.getText().toString();
        branch=Branch.getText().toString();
        hostel=Hostel.getText().toString();
        roomNo=RoomNo.getText().toString();
        if(!(email.isEmpty()||password.isEmpty()))
        {
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Signup.this, "Sucessfully Signed Up", Toast.LENGTH_SHORT).show();
                                addUserDatatoDB();
                            }
                               else
                                Toast.makeText(Signup.this, "Error Signing Up"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else
            Toast.makeText(this, "Invalid Fields", Toast.LENGTH_SHORT).show();




    }

    private void addUserDatatoDB() {
        final HashMap<String,Object> userData=new HashMap<>();
        userData.put("name",name);
        userData.put("idLogin",email);
        userData.put("Password",password);
        userData.put("phoneNo",mobNo);
        userData.put("idUserId",mAuth.getUid());
        userData.put("rollNo",rollNo);
        userData.put("branch",branch);
        userData.put("hostel",hostel);
        userData.put("roomNo",rollNo);




        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(mAuth.getUid()).exists())
                {

                }
                else
                {
                    userRef.child(mAuth.getUid()).updateChildren(userData).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Signup.this, "Value Updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        HashMap<String,Object> loginDetails=new HashMap<>();
        loginDetails.put("idLogin",email);
        loginDetails.put("username",name);
        loginDetails.put("password",password);
        loginDetails.put("lastLogin",email);

        loginRef.child(mAuth.getUid()).updateChildren(loginDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Signup.this, "And Login Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

}
