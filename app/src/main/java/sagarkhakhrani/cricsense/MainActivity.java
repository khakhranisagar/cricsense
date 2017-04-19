package sagarkhakhrani.cricsense;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import sagarkhakhrani.cricsense.Model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;

    private static int RC_SIGNIN=0;
    private static String TAG="MAIN_ACTIVITY";
    ProgressDialog progressDialog;
    DatabaseReference rootReference;
    Boolean userExists=false;
    //Added A comment for Git Twice

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Logging You In");
        progressDialog.setCanceledOnTouchOutside(false);
        rootReference = FirebaseDatabase.getInstance().getReference().child("users");




        GoogleSignInOptions signInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id  )).requestEmail().build();

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();



        findViewById(R.id.buttonGoogleSignup).setOnClickListener(this);
        Download();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonGoogleSignup:
                signUp();
                break;
            default:
                break;

        }
    }

    private void signUp() {
        Toast.makeText(getApplicationContext(),"Signing In Now",Toast.LENGTH_SHORT).show();
        Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGNIN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),"Connection Failed"+connectionResult.getErrorMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGNIN)
        {

            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account=result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            }else {

                Log.d(TAG,"Google Login Failed"+result.getStatus().toString());
            }
        }


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("AUTH","signInwithCredential:onComplete"+task.isSuccessful());

                        final String userEmail=task.getResult().getUser().getEmail();
                       // Toast.makeText(MainActivity.this, userEmail, Toast.LENGTH_SHORT).show();


                        progressDialog.show();

                        rootReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds:dataSnapshot.getChildren())
                                {
                                    if(ds.getValue(User.class).getmEmail().equalsIgnoreCase(userEmail))
                                    {
                                        //Toast.makeText(MainActivity.this,"Users \n"+ ds.getValue(User.class).getmEmail(), Toast.LENGTH_SHORT).show();
                                        userExists=true;

                                    }
                                }
                                redirectUser(userEmail);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


//                        Intent intent=new Intent(getApplicationContext(),Dashboard.class);
//         +               startActivity(intent);

                    }
                });

    }

    private void redirectUser(String userEmail){
        if(userExists)
        {
            Intent intent=new Intent(MainActivity.this,Dashboard.class);
            progressDialog.dismiss();

            startActivity(intent);
            Toast.makeText(MainActivity.this, "Welcome Back..!", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent=new Intent(MainActivity.this,SignUp.class);

            intent.putExtra("mEmail",userEmail);
            progressDialog.dismiss();
            startActivity(intent);

            Toast.makeText(MainActivity.this, "Complete Your SignUp", Toast.LENGTH_SHORT).show();

        }

    }



    private void showToast(String message){
        Log.i(TAG,message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void Download(){
        int count=0;
        try {
            URL url = new URL("https://cricsense-5826e.firebaseio.com/results.json?print=pretty&format=export&download=cricsense-5826e-results-export.json");
            URLConnection conection = url.openConnection();
            conection.connect();
// this will be useful so that you can show a tipical 0-100% progress bar
            int lenghtOfFile = conection.getContentLength();

// download the file
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

// Output stream
            //  OutputStream output = new FileOutputStream("sdcard/downloadedfile.png");

            byte data[] = new byte[1024];
            StringBuffer stringBuffer=new StringBuffer();
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
// publishing the progress….
// After this onProgressUpdate will be called
                stringBuffer.append(data);
// writing data to file
                // output.write(data, 0, count);
            }

            Log.i("MyData",stringBuffer.toString());
// flushing output
            // output.flush();

// closing streams
            //  output.close();
            input.close();

        } catch (Exception e) {
//            Log.i("Error:", e.getMessage());
        }

    }
}
