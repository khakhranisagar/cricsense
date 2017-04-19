package sagarkhakhrani.cricsense;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.Random;

import sagarkhakhrani.cricsense.FirebaseClient.FirebaseInstance;
import sagarkhakhrani.cricsense.Model.User;


public class SignUp extends AppCompatActivity {
Button floatingActionButton;
    FirebaseInstance firebaseInstance;
    private String android_id;
    String mUserName = null;
    String mUserPhone = null;
    String mAvatar = null;
    String mUserReferal=null;
    String mUserReferCode=null;
    RadioGroup mAvatarGroup;
    DatabaseReference databaseReference;
    String mEmail = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_sign_up);

        firebaseInstance = FirebaseInstance.getFirebaseInstance();
        databaseReference=FirebaseInstance.getDatabaseReference();
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        android_id = tm.getDeviceId();


        mAvatarGroup = (RadioGroup) findViewById(R.id.avatarRadioGroup);
        floatingActionButton=(Button)findViewById(R.id.fab_signup);
        mAvatarGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                mAvatar=radioButton.getText().toString();
            }
        });


        if (getIntent().hasExtra("mEmail")) {
            mEmail = getIntent().getStringExtra("mEmail");
            Toast.makeText(this, "Your Email \n" + mEmail, Toast.LENGTH_SHORT).show();
        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserName = ((EditText) findViewById(R.id.userFnameEditText)).getText().toString();
                mUserPhone=((EditText)findViewById(R.id.editText4)).getText().toString();
                mUserReferal=((EditText)findViewById(R.id.ReferalEditText)).getText().toString().trim();
                mUserReferCode=getRandomReferCode();
                if(mUserName!=null && mUserPhone!=null && mAvatar!=null && android_id!=null){
                    String userId = databaseReference.push().getKey();

                    User user = new User(mUserName,mEmail,mUserPhone,mAvatar,"12000",android_id,mUserReferCode,mUserReferal);


                    databaseReference.child("users").child(userId).setValue(user);
                }
                else {
                    Toast.makeText(SignUp.this, "Please Fill All the Details", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    private static String getRandomReferCode(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
      return sb.toString();
    }
}
