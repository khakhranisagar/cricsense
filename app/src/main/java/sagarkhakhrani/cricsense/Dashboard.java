package sagarkhakhrani.cricsense;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sagarkhakhrani.cricsense.FirebaseClient.FirebaseClient;
import sagarkhakhrani.cricsense.FirebaseClient.FirebaseInstance;
import sagarkhakhrani.cricsense.Match.MatchSelectActivity;
import sagarkhakhrani.cricsense.Match.RecyclerItemClickListener;
import sagarkhakhrani.cricsense.Model.Prediction;
import sagarkhakhrani.cricsense.Model.Results;
import sagarkhakhrani.cricsense.Model.User;
import sagarkhakhrani.cricsense.Model.matches;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final static String DB_URL = "https://cricsense-5826e.firebaseio.com/matches";
    FirebaseClient firebaseClient;
    RecyclerView recyclerView, upComingRecyclerView;
    private StaggeredGridLayoutManager stagaggeredGridLayoutManager;
    FirebaseAuth.AuthStateListener listener;
    User currentUser;
    int finalPoints;
    public  static User myUser;
    TextView userName;
    MenuItem userPoints;
    ImageView imageView;

    ArrayList<Prediction> userPredictions;
    ArrayList<Results> resultsArrayList;
    Boolean newP=false,newR=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userPredictions=new ArrayList<>();
        resultsArrayList=new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.matchRecycler);
        upComingRecyclerView = (RecyclerView) findViewById(R.id.upcomingRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        upComingRecyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));


        firebaseClient = new FirebaseClient(this, DB_URL, recyclerView, upComingRecyclerView);

        firebaseClient.refreshMatches();

        final ArrayList<matches> matchesArrayList = firebaseClient.getMatchesArrayList();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //FragmentManager fragmentManager = getSupportFragmentManager();
                //SelectedMatchFragment matchFragment=new SelectedMatchFragment();
                Bundle bundle = new Bundle();
                bundle.putString("team1", matchesArrayList.get(position).getTeam1());
                bundle.putString("team2", matchesArrayList.get(position).getTeam2());
                bundle.putString("matchno", matchesArrayList.get(position).getMatchno());
                //matchFragment.setArguments(bundle);
                Intent intent = new Intent(Dashboard.this, MatchSelectActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

//                FragmentTransaction transaction=fragmentManager.beginTransaction();
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.add(android.R.id.content,matchFragment).addToBackStack(null).commit();
            }
        }));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
        FirebaseInstance.getDatabaseReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(ds.getValue(User.class).getmEmail().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getEmail().trim()))
                    {
                        currentUser=ds.getValue(User.class);

                        updateDetails(currentUser);
                        Toast.makeText(Dashboard.this, "Current USer Latest \n" + currentUser.getmUsername() + "\n" + currentUser.getmPhone(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        userPoints=menu.findItem(R.id.points);
        View header=navigationView.getHeaderView(0);

        userName=(TextView)header.findViewById(R.id.userNameTextView);
        imageView=(ImageView)header.findViewById(R.id.avatarImageView);


    }


    private void updateDetails(User user)
    {
        myUser=user;
        userName.setText(user.getmUsername());
        userPoints.setTitle("My Points\t"+user.getmUserPoints());
        String avatar=user.getmAvatar();
        switch (avatar){
            case "Mandy":
                imageView.setImageResource(R.drawable.av1);
                break;
            case "Joe":
                imageView.setImageResource(R.drawable.av4);
                break;
            case "Olivia":
                imageView.setImageResource(R.drawable.av5);
                break;
            case "Ethan":
                imageView.setImageResource(R.drawable.av8);
                break;
            default:
                break;
        }

        viewPredictions();

    }

    private void viewPredictions(){
        updatePredictions();

    }

    private void finalUpdate() {
        Log.i("Sizes",String.valueOf(userPredictions.size()));
        Log.i("Sizes",String.valueOf(resultsArrayList.size()));
        for (int i=0;i<userPredictions.size();i++){
            for (int j=0;j<resultsArrayList.size();j++)
            {
                if(userPredictions.get(i).getMatchno().equalsIgnoreCase(resultsArrayList.get(j).getMatchno()))
                {
                    finalPoints=(Integer.parseInt(userPredictions.get(i).getSelectedRate())*Integer.parseInt(userPredictions.get(i).getPointsOnBet())
                    )/100;

                    Toast.makeText(this, "Points to Lose/Win"+finalPoints, Toast.LENGTH_SHORT).show();
                    if(userPredictions.get(i).getSelectedTeam().equalsIgnoreCase(resultsArrayList.get(j).getWinningTeam())){
                        currentUser.setmUserPoints(String.valueOf(Integer.parseInt(currentUser.getmUserPoints())+finalPoints));
                        Toast.makeText(this, "You've Won..!!", Toast.LENGTH_SHORT).show();
                    }else {

                        currentUser.setmUserPoints(String.valueOf(Integer.parseInt(currentUser.getmUserPoints())-finalPoints));
                        Toast.makeText(this, "Oops \n You've Lost..!!", Toast.LENGTH_SHORT).show();
                    }



                }
            }


        }
    }

    private void updatePredictions() {
        Log.i("Printing Snapshots","Now");
        FirebaseInstance.getDatabaseReference().child("predictions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newP=true;
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    if (ds.getValue(Prediction.class).getUserName().equalsIgnoreCase(currentUser.getmUsername())) {
                        userPredictions.add(dataSnapshot.getValue(Prediction.class));
                        Log.i("Snapshot Predictions",ds.toString());
                    }
                }
                updateResults();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void updateResults() {



        FirebaseInstance.getDatabaseReference().child("results").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newR=true;
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    resultsArrayList.add(ds.getValue(Results.class));
                    Log.i("Snapshot Results",ds.toString());
                }

                updatePoints();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updatePoints() {

        FirebaseInstance.getDatabaseReference().child("users").orderByChild("mUsername")
                .equalTo(currentUser.getmUsername()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            String key=ds.getKey().toString();
                            Log.i("Snapshot Key",key);
                            Log.i("Snapshot1 Users",ds.toString());
                            FirebaseInstance.getDatabaseReference().child("users").child(key).setValue(currentUser);
                        }
                        finalUpdate();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.points) {
            // Handle the camera action
        } else if (id == R.id.userRank) {

        } else if (id == R.id.group_predict) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
