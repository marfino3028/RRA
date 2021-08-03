package com.example.asus.ta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    Button loginButton, registerButton, logoutButton;
    ProgressBar loginProgress;
    EditText loginEmail, loginPassword;
    String eml, pass;
    FirebaseAuth mAuth;
    String TAG="Ini Login";

    CallbackManager callbackManager;
    LoginButton loginFb;

    GoogleApiClient mGoogleApiClient;
    SignInButton googleBtn;
    int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);

        logoutButton = findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(this);
        logoutButton.setVisibility(View.INVISIBLE);

        loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(this);
        loginProgress = findViewById(R.id.login_progress);
        loginProgress.setVisibility(View.INVISIBLE);

        registerButton = findViewById(R.id.register_btn);
        registerButton.setOnClickListener(this);

        googleBtn = findViewById(R.id.google_btn);
        googleBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        callbackManager = CallbackManager.Factory.create();
        loginFb = findViewById(R.id.fb_btn);
        loginFb.setReadPermissions("email");
        loginFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginfb();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void loginfb() {
        loginFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void handleFacebookAccessToken(final AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error",""+e.getMessage());
                        updateUI(null);
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String email = authResult.getUser().getEmail();
                Toast.makeText(Login.this, "Anda login dengan email :"+email, Toast.LENGTH_SHORT).show();
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            }
        });
    }


    private void loginUser(String eml, String pass) {
        mAuth.signInWithEmailAndPassword(eml, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Login Success." + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent i = new Intent(this,Home.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this,Login.class);
            startActivity(i);
        }
    }

    private void register() {
        Intent i = new Intent(this,Register.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                loginProgress.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                logoutButton.setVisibility(View.VISIBLE);
                eml = loginEmail.getText().toString();
                pass = loginPassword.getText().toString();
                loginUser(eml,pass);
                break;
            case R.id.google_btn:
                loginGoogle();
                break;
            case R.id.register_btn:
                register();
                break;
            case R.id.fb_btn:
                loginfb();
            case R.id.logout_btn:
                loginProgress.setVisibility(View.INVISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.INVISIBLE);
                signOut();

        }
    }

    private void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }

    private void loginGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int request_code, int result_kode, Intent data){
        super.onActivityResult(request_code,result_kode,data);
        callbackManager.onActivityResult(request_code,result_kode,data);
        if(request_code == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }



    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if(result.isSuccess()){
            FirebaseUser user = mAuth.getCurrentUser();
            Toast.makeText(Login.this, "Login Success.",
                    Toast.LENGTH_SHORT).show();
            updateUI(user);
        }else{
            Toast.makeText(Login.this, "Login Gagal.",
                    Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG,"onConnectionFailed" + connectionResult);
    }
}
