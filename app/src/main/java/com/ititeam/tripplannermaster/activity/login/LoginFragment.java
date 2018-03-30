package com.ititeam.tripplannermaster.activity.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.ititeam.tripplannermaster.DB.TripTableOperations;
import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.StartActivityDrawer;
import com.ititeam.tripplannermaster.activity.TripConstant;
import com.ititeam.tripplannermaster.classes.DownLoadDataFromFirebase;
import com.ititeam.tripplannermaster.model.Note;
import com.ititeam.tripplannermaster.model.Trip;

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


    public LoginFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        //auth = FirebaseAuth.getInstance();
        /*
        Trip trip = new Trip();

        Note note1 = new Note();
        note1.setNoteBody("any note1");
        note1.setStatus("not determined");
        trip.getTripNotes().add(note1);
        Note note2 = new Note();
        note2.setNoteBody("any note2");
        note2.setStatus("not determined");
        trip.getTripNotes().add(note2);

        trip.setTripName("0");
        trip.setTripStartPoint("cairo");
        trip.setTripEndPoint("alexandria");
        trip.setTripDate("12345");
        trip.setTripTime("mon");
        trip.setTripStatus("Done");
        trip.setTripDirection("qwo");
        trip.setTripDescription("asdfg");
        trip.setTripRepetition("122334");
        trip.setTripCategory("cat1");
        trip.setUserId(1);
        boolean flag = new TripTableOperations(getActivity()).insertTrip(trip);
        // Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();

        Trip trip1 = new Trip();
        trip1.setTripName("1");
        trip1.setTripStartPoint("cairo");
        trip1.setTripEndPoint("alexandria");
        trip1.setTripDate("2018-01-14");
        trip1.setTripTime("mon");
        trip1.setTripStatus("Done");
        trip1.setTripDirection("qwo");
        trip1.setTripDescription("asdfg");
        trip1.setTripRepetition("122334");
        trip1.setTripCategory("cat1");
        trip1.setUserId(1);
        boolean flag1 = new TripTableOperations(getActivity()).insertTrip(trip1);
        // Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();


        Trip trip2 = new Trip();
        trip2.setTripName("2");
        trip2.setTripStartPoint("cairo");
        trip2.setTripEndPoint("alexandria");
        trip2.setTripDate("2018-01-23");
        trip2.setTripTime("mon");
        trip2.setTripStatus("qwertyui");
        trip2.setTripDirection("qwo");
        trip2.setTripDescription("asdfg");
        trip2.setTripRepetition("122334");
        trip2.setTripCategory("cat1");
        trip2.setUserId(1);
        boolean flag2 = new TripTableOperations(getActivity()).insertTrip(trip2);
        // Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();

        Trip trip3 = new Trip();
        trip3.setTripName("3");
        trip3.setTripStartPoint("cairo");
        trip3.setTripEndPoint("alexandria");
        trip3.setTripDate("2018-02-01");
        trip3.setTripTime("mon");
        trip3.setTripStatus("qwertyui");
        trip3.setTripDirection("qwo");
        trip3.setTripDescription("asdfg");
        trip3.setTripRepetition("122334");
        trip3.setTripCategory("cat1");
        trip3.setUserId(1);
        boolean flag3 = new TripTableOperations(getActivity()).insertTrip(trip3);
        // Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();

        Trip trip4 = new Trip();
        trip4.setTripName("4");
        trip4.setTripStartPoint("cairo");
        trip4.setTripEndPoint("alexandria");
        trip4.setTripDate("2018-02-23");
        trip4.setTripTime("mon");
        trip4.setTripStatus("qwertyui");
        trip4.setTripDirection("qwo");
        trip4.setTripDescription("asdfg");
        trip4.setTripRepetition("122334");
        trip4.setTripCategory("cat1");
        trip4.setUserId(1);
        boolean flag4 = new TripTableOperations(getActivity()).insertTrip(trip4);
        // Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();

        Trip trip5 = new Trip();
        trip5.setTripName("5");
        trip5.setTripStartPoint("cairo");
        trip5.setTripEndPoint("alexandria");
        trip5.setTripDate("2018-04-12");
        trip5.setTripTime("mon");
        trip5.setTripStatus("qwertyui");
        trip5.setTripDirection("qwo");
        trip5.setTripDescription("asdfg");
        trip5.setTripRepetition("122334");
        trip5.setTripCategory("cat1");
        trip5.setUserId(1);
        boolean flag5 = new TripTableOperations(getActivity()).insertTrip(trip5);
        //Toast.makeText(getApplicationContext(), flag+"", Toast.LENGTH_LONG).show();

        Trip trip6 = new Trip();
        trip6.setTripName("6");
        trip6.setTripStartPoint("cairo");
        trip6.setTripEndPoint("alexandria");
        trip6.setTripDate("2018-05-10");
        trip6.setTripTime("mon");
        trip6.setTripStatus("qwertyui");
        trip6.setTripDirection("qwo");
        trip6.setTripDescription("asdfg");
        trip6.setTripRepetition("122334");
        trip6.setTripCategory("cat1");
        trip6.setUserId(1);
        boolean flag6 = new TripTableOperations(getActivity()).insertTrip(trip6);
*/
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
                                if(checkBoxRememberMe.isChecked())
                                {
                                    SharedPreferences myprefrances = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor = myprefrances.edit();
                                    editor.putString("user_email",uEmail);
                                    editor.putString("user_password",uPassword);
                                    editor.commit();
                                }else{
                                    PreferenceManager.getDefaultSharedPreferences(getActivity()).
                                            edit().clear().apply();
                                    Toast.makeText(getActivity(), "remove prefrences", Toast.LENGTH_SHORT).show();
                                }

                                DownLoadDataFromFirebase2 downLoadDataFromFirebase=new DownLoadDataFromFirebase2(getActivity());
                                downLoadDataFromFirebase.execute();


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
                            new TripTableOperations(getApplicationContext()).getTripFromFirebase(trips);
                           prog.dismiss();

                            Intent intent = new Intent(context, StartActivityDrawer.class);
                            intent.putExtra("login_user_email", User.getEmail());
                            context.startActivity(intent);
                        }
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
