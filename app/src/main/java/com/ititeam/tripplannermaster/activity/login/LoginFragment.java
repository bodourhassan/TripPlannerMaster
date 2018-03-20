package com.ititeam.tripplannermaster.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.*;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.AuthenticationActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment implements OnLoginListener{
    private static final String TAG = "LoginFragment";
    com.facebook.login.widget.LoginButton facebook_login_button;
    CallbackManager callbackManager;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
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

    @Override
    public void login() {
        Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
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
