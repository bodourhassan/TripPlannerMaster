package com.ititeam.tripplannermaster;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ititeam.tripplannermaster.activity.AuthenticationActivity;
import com.ititeam.tripplannermaster.activity.HomeFragment;
import com.ititeam.tripplannermaster.activity.MainActivity;
import com.ititeam.tripplannermaster.activity.StartActivityDrawer;

public class SplashScreenActivity extends Activity {
    String emailpref;
    String passwordpref;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        SharedPreferences myprefrances = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);
        emailpref = myprefrances.getString("user_email", "");
        passwordpref = myprefrances.getString("user_password", "");
        //Toast.makeText(SplashScreenActivity.this, "pref   "+emailpref, Toast.LENGTH_SHORT).show();
        //Toast.makeText(SplashScreenActivity.this, "pre   "+passwordpref, Toast.LENGTH_SHORT).show();



        Thread splashThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    //sleep(1500);
                    auth = FirebaseAuth.getInstance();
                    //Intent intent = new Intent(SplashScreenActivity.this , AuthenticationActivity.class);
                    //startActivity(intent);
                    //Toast.makeText(SplashScreenActivity.this, "before", Toast.LENGTH_SHORT).show();
                    Log.i("heshammm","braaaaa");

                }
                catch (Exception e)
                {
                    Log.e("Exception",e.getMessage());
                }finally {
                    //Intent i = new Intent(SplashScreenActivity.this , AuthenticationActivity.class);
                    //startActivity(i);

                    if(emailpref.isEmpty() || passwordpref.isEmpty())
                    {
                        Intent i = new Intent(SplashScreenActivity.this ,AuthenticationActivity.class );
                        startActivity(i);
                    }else{
                        auth.signInWithEmailAndPassword(emailpref, passwordpref)
                                .addOnCompleteListener(SplashScreenActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        // Toast.makeText(SplashScreenActivity.this, "after", Toast.LENGTH_SHORT).show();
                                        if (!task.isSuccessful()) {
                                            // there was an error
                                            Log.i("heshammm","unseceeful");
                                            Intent i = new Intent(SplashScreenActivity.this , AuthenticationActivity.class);
                                            startActivity(i);
                                        } else {
                                            //succecfull
                                            Log.i("heshammm","succefull");
                                            Intent i = new Intent(SplashScreenActivity.this , StartActivityDrawer.class);
                                            i.putExtra("login_user_email", emailpref);
                                            startActivity(i);
                                        }
                                    }
                                });
                    }

                }

            }
        };

        splashThread.start();

    }
}
