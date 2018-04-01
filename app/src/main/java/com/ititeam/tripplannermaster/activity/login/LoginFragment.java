package com.ititeam.tripplannermaster.activity.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.ResetPasswordActivity;
import com.ititeam.tripplannermaster.activity.StartActivityDrawer;
import com.ititeam.tripplannermaster.activity.TripConstant;
import com.ititeam.tripplannermaster.classes.DownLoadDataFromFirebase;
import com.ititeam.tripplannermaster.model.Note;
import com.ititeam.tripplannermaster.model.Trip;
import com.ititeam.tripplannermaster.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class LoginFragment extends Fragment implements OnLoginListener , View.OnClickListener{
    private static final String TAG = "LoginFragment";
    com.facebook.login.widget.LoginButton facebook_login_button;
    CallbackManager callbackManager;
    EditText etEmail, etPassword;
    CheckBox checkBoxRememberMe;
    String uEmail, uPassword;
    private FirebaseAuth auth;
    ProgressDialog   prog;
    String first_name,last_name,email,id;
    TextView tbForget ;


    public LoginFragment() {
        // Required empty public constructor

    }

    @Override
    public void onResume() {
        super.onResume();
        YoYo.with(Techniques.RotateIn)
                .duration(2000)
                .playOn(etEmail);

        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(etPassword);

        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(checkBoxRememberMe);

        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(tbForget);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        prog = new ProgressDialog(getActivity());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = inflate.findViewById(R.id.FragmentSignInEmail);
        etPassword = inflate.findViewById(R.id.fragmentSignInPassword);
        checkBoxRememberMe = inflate.findViewById(R.id.FragmentLoginKeepMeLoggedIn);
        checkBoxRememberMe.setChecked(true);
        tbForget = inflate.findViewById(R.id.forgot_password);


        YoYo.with(Techniques.RotateIn)
                .duration(3000)
                .playOn(etEmail);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(etPassword);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(checkBoxRememberMe);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(tbForget);

        tbForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Forgot password clicked", Toast.LENGTH_SHORT).show();

            }
        });


        facebook_login_button=inflate.findViewById(R.id.facebook_login_button);
        facebook_login_button.setFragment(this);
        facebook_login_button.setReadPermissions("email","public_profile");
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        callbackManager=CallbackManager.Factory.create();
        facebook_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String user_id=loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        getUserInfo(object);
                        ///////////////////////////////////////////////////////////////////////////////////////




                        auth.signInWithEmailAndPassword(email, id)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        User.setEmail(email);
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        prog.dismiss();
                                        if (!task.isSuccessful()) {
                                            // there was an error
                                            //Toast.makeText(getActivity(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                            //////////////////////////////////////////////////////////////////////////////////////////////////

                                            auth.createUserWithEmailAndPassword(email, id).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                                    if (!task.isSuccessful()) {
                                                        prog.dismiss();
                                                        Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                                                Toast.LENGTH_SHORT).show();

                                                        Log.i("exceptionis", "" + task.getException());
                                                    } else {
                                                        User.setEmail(email);
                                                        prog.dismiss();
                                                        Toast.makeText(getActivity(), "sign up", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getActivity(), StartActivityDrawer.class));
                                                    }
                                                }


                                            });


                                            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            Log.i("myExce", "" + task.getException().getMessage());

                                        } else {
                                            /*
                                            if(checkBoxRememberMe.isChecked())
                                            {
                                                SharedPreferences myprefrances = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                                SharedPreferences.Editor editor = myprefrances.edit();
                                                editor.putString("user_email",uEmail);
                                                editor.putString("user_password",uPassword);
                                                User.setEmail(uEmail);
                                                editor.commit();
                                            }else{

                                            }
                                                PreferenceManager.getDefaultSharedPreferences(getActivity()).
                                                        edit().clear().apply();
                                                        */
                                            User.setEmail(email);
                                            Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();



                                            DownLoadDataFromFirebase2 downLoadDataFromFirebase=new DownLoadDataFromFirebase2(getActivity());
                                            downLoadDataFromFirebase.execute();


                                        }
                                    }
                                });





                        //////////////////////////////////////////////////////////////////////////////////////////
                    }
                });
                Bundle bundle=new Bundle();
                bundle.putString("fields","first_name,last_name,email,id");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

        return inflate;
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    @Override
    public void login() {
        if (isConnected()) {

            Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
            uEmail = etEmail.getText().toString().trim();
            uPassword = etPassword.getText().toString().trim();


            if (TextUtils.isEmpty(uEmail) || TextUtils.isEmpty(uPassword)) {
                YoYo.with(Techniques.Tada)
                        .duration(1500)
                        .playOn(etEmail);
                YoYo.with(Techniques.Tada)
                        .duration(1500)
                        .playOn(etPassword);
                YoYo.with(Techniques.Tada)
                        .duration(1500)
                        .playOn(checkBoxRememberMe);
                YoYo.with(Techniques.Tada)
                        .duration(1500)
                        .playOn(tbForget);
                Toast.makeText(getActivity(), "missing Data !", Toast.LENGTH_SHORT).show();

                return;
            }

            //f@yah.com
            //12345678

            Log.i("userdatalogin", "" + uEmail);
            Log.i("userdatalogin", "" + uPassword);
            //authenticate user

            prog.setMessage("signing up !!!!!!");
            prog.setCancelable(false);
            prog.show();

            loginUser(uEmail , uPassword);
        } else {
            //Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();


            //int gravity = Gravity.CENTER; // the position of toast
            //int xOffset = 0; // horizontal offset from current gravity
            //int yOffset = 0; // vertical offset from current gravity

            Toast toast= Toast.makeText(getActivity(), "no internet connection", Toast.LENGTH_SHORT);
            TextView textView= (TextView) toast.getView().findViewById(android.R.id.message);
            textView.setTextColor(Color.YELLOW);
            toast.getView().setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
           // toast.setGravity(gravity, xOffset, yOffset);
            toast.show();
        }


    }
    private void loginUser(String uEmail , String uPassword)
    {
        auth.signInWithEmailAndPassword(uEmail, uPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        prog.dismiss();
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (uPassword.length() < 6) {
                                etPassword.setError(getString(R.string.minimum_password));
                            } else {
                                //Toast.makeText(getActivity(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Log.i("myExce", "" + task.getException().getMessage());
                            }
                        } else {
                            if(checkBoxRememberMe.isChecked())
                            {
                                SharedPreferences myprefrances = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor editor = myprefrances.edit();
                                editor.putString("user_email",uEmail);
                                editor.putString("user_password",uPassword);
                                User.setEmail(uEmail);
                                editor.commit();
                            }else{
                                PreferenceManager.getDefaultSharedPreferences(getActivity()).
                                        edit().clear().apply();
                                User.setEmail(uEmail);
                                Toast.makeText(getActivity(), "remove prefrences", Toast.LENGTH_SHORT).show();
                            }


                            DownLoadDataFromFirebase2 downLoadDataFromFirebase=new DownLoadDataFromFirebase2(getActivity());
                            downLoadDataFromFirebase.execute();
                            prog.dismiss();

                            Intent intent = new Intent(getActivity(), StartActivityDrawer.class);
                            intent.putExtra("login_user_email", User.getEmail());

                            getActivity().startActivity(intent);


                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void getUserInfo(JSONObject object){

        try {
            first_name=object.getString("first_name");
            last_name=object.getString("last_name");
            email=object.getString("email");
            id=object.getString("id");
            Toast.makeText(getContext(), id.toString(), Toast.LENGTH_SHORT).show();



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {

    }

    public void signOut() {
        auth.signOut();
    }


    public class DownLoadDataFromFirebase2 extends AsyncTask<String, Integer, Object> {

        private DatabaseReference databaseReference, getDatabaseReference;
        Context context;
        OnTaskComplete onTaskComplete=null;

        public DownLoadDataFromFirebase2(Context context) {
            this.context=context;
        }

        @Override
        protected void onPostExecute(Object o) {
            // prog.dismiss();
        }

        @Override
        protected Object doInBackground(String... strings) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
            String path= User.getEmail().replace(".","_");
            path=path.replace("#","_");
            path=path.replace("$","_");
            path=path.replace("[","_");
            path=path.replace("]","_");
            User.setFirebasePath(path);
            if ((getDatabaseReference = databaseReference.child(User.getFirebasePath())) != null) {
                getDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Trip> trips = null;
                        GenericTypeIndicator<ArrayList<Trip>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Trip>>() {
                        };
                        trips = dataSnapshot.getValue(genericTypeIndicator);
                        if (trips != null) {
                            Toast.makeText(getApplicationContext(), "download" + trips.size(), Toast.LENGTH_SHORT).show();
                            TripTableOperations tripTableOperations =new TripTableOperations(getApplicationContext());
                           // tripTableOperations.deleteAllTrips();
                            tripTableOperations.getTripFromFirebase(trips);

                        }
                     ;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(context, "Download Error", Toast.LENGTH_SHORT).show();

                    }

                });
            }
            return null;
        }
    }

}
