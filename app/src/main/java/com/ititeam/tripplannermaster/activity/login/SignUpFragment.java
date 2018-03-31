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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.HomeFragment;
import com.ititeam.tripplannermaster.activity.MainActivity;
import com.ititeam.tripplannermaster.activity.StartActivityDrawer;
import com.ititeam.tripplannermaster.model.User;


public class SignUpFragment extends Fragment implements OnSignUpListener{
    private static final String TAG = "SignUpFragment";
    EditText userName, email, password, confirmPassword;
    String uName, uEmail, uPassword, uConformPassword;
    private FirebaseAuth auth;


    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_signup, container, false);
        userName = inflate.findViewById(R.id.Fragment_SignUp_UserName);
        email = inflate.findViewById(R.id.Fragment_SignUp_Email);
        password = inflate.findViewById(R.id.Fragment_SignUp_Password);
        confirmPassword = inflate.findViewById(R.id.Fragment_SignUp_ConformPassword);


        YoYo.with(Techniques.RotateIn)
                .duration(3000)
                .playOn(userName);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(email);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(password);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .playOn(confirmPassword);

        return inflate;
    }

    @Override
    public void signUp() {
        if (isConnected()) {
            uName = userName.getText().toString().trim();
            uEmail = email.getText().toString().trim();
            uPassword = password.getText().toString().trim();
            uConformPassword = confirmPassword.getText().toString().trim();
            User.setEmail(uEmail);

            if (TextUtils.isEmpty(uName) || TextUtils.isEmpty(uEmail) || uPassword.length() < 6) {
                YoYo.with(Techniques.Tada)
                        .duration(1500)
                        .playOn(userName);
                YoYo.with(Techniques.Tada)
                        .duration(1500)
                        .playOn(email);
                YoYo.with(Techniques.Tada)
                        .duration(1500)
                        .playOn(password);
                Toast.makeText(getActivity(), "missing data !", Toast.LENGTH_SHORT).show();
                return;
            }



            final ProgressDialog prog = new ProgressDialog(getActivity());
            prog.setMessage("signing up !!!!!!");
            prog.setCancelable(false);
            prog.show();

            Log.i("userdatasignup", "" + uEmail);
            Log.i("userdatasignup", "" + uPassword);
            auth.createUserWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                    if (!task.isSuccessful()) {
                        prog.dismiss();
                        Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT).show();

                        Log.i("exceptionis", "" + task.getException());
                    } else {
                        User.setEmail(uEmail);
                        prog.dismiss();
                        Toast.makeText(getActivity(), "succefull", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getActivity(), StartActivityDrawer.class));
                    }
                }


            });
        } else {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }


        //Toast.makeText(getActivity(),uName +" "+uEmail, Toast.LENGTH_SHORT).show();

        //Intent i = new Intent(getActivity() , MainActivity.class);
        //startActivity(i);
    }
}
