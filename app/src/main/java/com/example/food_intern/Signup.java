package com.example.food_intern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText Name,Email,Password,MobNo;
    String name,email,password,mobNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth= FirebaseAuth.getInstance();
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        MobNo=findViewById(R.id.phone_num);

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
        if(!(email.isEmpty()||password.isEmpty()))
        {
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                                Toast.makeText(Signup.this, "Sucessfully Signed Up", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Signup.this, "Error Signing Up"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else
            Toast.makeText(this, "Invalid Fields", Toast.LENGTH_SHORT).show();


    }
}
