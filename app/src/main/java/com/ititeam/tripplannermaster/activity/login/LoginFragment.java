package com.ititeam.tripplannermaster.activity.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.AuthenticationActivity;
import com.ititeam.tripplannermaster.activity.MainActivity;
import com.ititeam.tripplannermaster.activity.StartActivityDrawer;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment implements OnLoginListener{
    private static final String TAG = "LoginFragment";
    com.facebook.login.widget.LoginButton facebook_login_button;
    CallbackManager callbackManager;
    EditText etEmail, etPassword;
    String uEmail, uPassword;
    private FirebaseAuth auth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        //auth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = inflate.findViewById(R.id.FragmentSignInEmail);
        etPassword = inflate.findViewById(R.id.fragmentSignInPassword);
        inflate.findViewById(R.id.forgot_password).setOnClickListener(v ->
                Toast.makeText(getContext(), "Forgot password clicked", Toast.LENGTH_SHORT).show());

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
            auth = FirebaseAuth.getInstance();

            Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
            uEmail = etEmail.getText().toString().trim();
            uPassword = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(uEmail)) {
                Toast.makeText(getActivity(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(uPassword)) {
                Toast.makeText(getActivity(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }
            //f@yah.com
            //12345678

            Log.i("userdatalogin", "" + uEmail);
            Log.i("userdatalogin", "" + uPassword);
            //authenticate user
            final ProgressDialog prog = new ProgressDialog(getActivity());
            prog.setMessage("signing up !!!!!!");
            prog.setCancelable(false);
            prog.show();
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
                                Intent intent = new Intent(getActivity(), StartActivityDrawer.class);
                                startActivity(intent);
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void getUserInfo(JSONObject object){
        String first_name,last_name,email,id;
        try {
            first_name=object.getString("first_name");
            last_name=object.getString("last_name");
            email=object.getString("email");
            id=object.getString("id");
            Toast.makeText(getContext(), first_name+last_name, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
