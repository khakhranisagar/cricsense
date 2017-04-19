package sagarkhakhrani.cricsense.Match;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sagarkhakhrani.cricsense.FirebaseClient.FirebaseInstance;
import sagarkhakhrani.cricsense.MainActivity;
import sagarkhakhrani.cricsense.Model.rates;
import sagarkhakhrani.cricsense.R;

/**
 * Created by sagar.khakhrani on 27-03-2017.
 */

public class SelectMatch extends AppCompatActivity {
    String team1,team2,matchno;
    ArrayList<rates> ratesArrayList;
    ProgressDialog progressDialog;

RadioButton team1Radio,team2Radio;
String myData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_match);

        ratesArrayList=new ArrayList<>();
        progressDialog=new ProgressDialog(SelectMatch.this);
        progressDialog.setMessage("Getting Rates");

        team1=getIntent().getStringExtra("team1");
        team2=getIntent().getStringExtra("team2");
        matchno=getIntent().getStringExtra("matchno");
        Toast.makeText(this, "Teams\n"+team1+"\n"+team2, Toast.LENGTH_SHORT).show();

        FirebaseInstance.getDatabaseReference().child("rates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               progressDialog.show();
                Toast.makeText(SelectMatch.this, "Data Changed", Toast.LENGTH_SHORT).show();
                updaterates(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        team1Radio=(RadioButton)findViewById(R.id.Rate1RadioButton);
        team2Radio=(RadioButton)findViewById(R.id.Rate2RadioButton);
//        team1Radio.setButtonDrawable(R.drawable.kvip);
//        team2Radio.setButtonDrawable(R.drawable.kkr);
      /*  new MaterialDialog.Builder(this)
                .title(R.string.input)
                .inputRangeRes(2, 20, R.color.colorPrimary)
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        myData=input.toString();
                        Toast.makeText(getApplicationContext(), "You Entered"+myData, Toast.LENGTH_SHORT).show();
                    }
                }).show();*/

/*

        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width=metrics.widthPixels;
        int height=metrics.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*.8));*/


    }

    private void updaterates(DataSnapshot dataSnapshot) {
        ratesArrayList.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()){

            ratesArrayList.add(ds.getValue(rates.class));
        }

        for(int i=0;i<ratesArrayList.size();i++)
        {
            if(ratesArrayList.get(i).getMatchno().equalsIgnoreCase(matchno))
            {
                team1Radio.setText(ratesArrayList.get(i).getTeam1());
                team2Radio.setText(ratesArrayList.get(i).getTeam2());
            }
        }
        progressDialog.hide();


    }

    private void setLayout(){


    }

}
