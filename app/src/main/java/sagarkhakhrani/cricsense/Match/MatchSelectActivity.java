package sagarkhakhrani.cricsense.Match;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sagarkhakhrani.cricsense.Dashboard;
import sagarkhakhrani.cricsense.FirebaseClient.FirebaseInstance;
import sagarkhakhrani.cricsense.Model.Prediction;
import sagarkhakhrani.cricsense.Model.rates;
import sagarkhakhrani.cricsense.R;

public class MatchSelectActivity extends AppCompatActivity {

    String team1,team2,matchno,selectedRate,sTeam1;
    String[] selected;
    ArrayList<rates> ratesArrayList;
    SeekBar seekBar;
    TextView pointsTextview;
    RadioButton team1Radio,team2Radio;
    Button predictButton;
    RadioGroup rateGroup;
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_select);

        team1=getIntent().getStringExtra("team1");
        team2=getIntent().getStringExtra("team2");
        matchno=getIntent().getStringExtra("matchno");
        Toast.makeText(this, "Teams\n"+team1+"\n"+team2, Toast.LENGTH_SHORT).show();


        pointsTextview=(TextView)findViewById(R.id.points);
        rateGroup=(RadioGroup)findViewById(R.id.radioGroup);
        seekBar=(SeekBar)findViewById(R.id.seekBar2);
        seekBar.setMax(Integer.parseInt(Dashboard.myUser.getmUserPoints()));
        //seekBar.setProgress(500,true);
        predictButton=(Button)findViewById(R.id.predictButton);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pointsTextview.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ratesArrayList=new ArrayList<>();





        FirebaseInstance.getDatabaseReference().child("rates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Data Changed", Toast.LENGTH_SHORT).show();
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

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=Dashboard.myUser.getmUsername();
                String rate=selectedRate;
                int pointsOnBet=Integer.parseInt(pointsTextview.getText().toString().trim());
                String selectedTeam=sTeam1;
                String matchNo=matchno;
                Toast.makeText(MatchSelectActivity.this, "Rate \n"+rate+"\n Team"+selectedTeam, Toast.LENGTH_SHORT).show();
                if(userName!=null && rate!=null && pointsOnBet>0 && matchNo!=null && selectedTeam!=null)
                {
                    Prediction prediction=new Prediction(matchNo,userName,selectedTeam,rate,String.valueOf(pointsOnBet));
                    String rateKey=FirebaseInstance.getDatabaseReference().child("predictions").push().getKey();
                    FirebaseInstance.getDatabaseReference().child("predictions").child(rateKey).setValue(prediction);
                    Toast.makeText(MatchSelectActivity.this, "Prediction  saved", Toast.LENGTH_SHORT).show();
                    finish();


                }else {
                    Toast.makeText(MatchSelectActivity.this, "Please Fill Everything..!!", Toast.LENGTH_SHORT).show();
                }



            }
        });

        rateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton=(RadioButton)findViewById(rateGroup.getCheckedRadioButtonId());
                selected = new String[2];
                selectedRate=((RadioButton)findViewById(rateGroup.getCheckedRadioButtonId())).getText().toString();
                selected = selectedRate.split("\\s");
                selectedRate = selected[0];

                if(team1Radio.isSelected())
                {
                    sTeam1=team1;
                }else {
                    sTeam1=team2;
                }




            }
        });
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
                team1Radio.setText(ratesArrayList.get(i).getTeam1()+" %");
                team2Radio.setText(ratesArrayList.get(i).getTeam2()+" %");
            }
        }


    }
}
