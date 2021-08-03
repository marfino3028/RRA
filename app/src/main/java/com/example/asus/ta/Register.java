package com.example.asus.ta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    Button registerButton;
    ProgressBar registerProgress;
    EditText registerNama, registerEmail, registerPassword;
    String nma, eml, pass;
    FirebaseAuth mAuth;
    String TAG="Ini Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerNama = findViewById(R.id.register_nama);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        registerProgress = findViewById(R.id.register_progress);
        registerProgress.setVisibility(View.INVISIBLE);
        registerButton = findViewById(R.id.register_btn);
        registerButton.setVisibility(View.VISIBLE);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eml = registerEmail.getText().toString();
                pass = registerPassword.getText().toString();
                register(eml, pass);
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void register(String eml, String pass) {
        if (TextUtils.isEmpty(eml)) {
            Log.d(TAG, "belum lengkap");
            Toast.makeText(getApplicationContext(), "Masukkan email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Log.d(TAG, "belum lengkap");
            Toast.makeText(getApplicationContext(), "Masukkan password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 6) {
            Log.d(TAG, "belum lengkap");
            Toast.makeText(getApplicationContext(), "Password terlalu pendek, masukkan min 6 char!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(eml, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            registerProgress.setVisibility(View.VISIBLE);
                            registerButton.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Registrasi berhasil.",
                                    Toast.LENGTH_SHORT).show();
                            berhasil(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Registrasi gagal.",
                                    Toast.LENGTH_SHORT).show();
                            registerProgress.setVisibility(View.INVISIBLE);
                            registerButton.setVisibility(View.VISIBLE);
                        }

                        // ...
                    }
                });
    }

    private void berhasil(FirebaseUser user) {
        Intent i = new Intent(this,Login.class);
        startActivity(i);
    }

}
