package com.example.asus.ta;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreen extends AppCompatActivity {
    ImageView logo; //membuat Variabel foto
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        getHashkey();
        logo = findViewById(R.id.logo); //Deklarasi foto
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.transisi); //Membuat animasi splashscreen
        logo.setAnimation(anim);
        final Intent i = new Intent(this,Login.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }

//    public void getHashkey(){
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//
//                Log.i("Base64", Base64.encodeToString(md.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d("Name not found", e.getMessage(), e);
//
//        } catch (NoSuchAlgorithmException e) {
//            Log.d("Error", e.getMessage(), e);
//        }
//    }




}
