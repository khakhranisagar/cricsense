package sagarkhakhrani.cricsense.FirebaseClient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import sagarkhakhrani.cricsense.Match.MatchAdapter;
import sagarkhakhrani.cricsense.Model.matches;

/**
 * Created by sagar.khakhrani on 24-03-2017.
 */

public class FirebaseClient {
    Context context;
    String DatabaseUrl;
    RecyclerView recyclerView,upcoming;
    Firebase firebase;
    ArrayList<matches> matchesArrayList,currentMatch,upComingMatches;
    MatchAdapter matchAdapter;

    public FirebaseClient(Context context, String databaseUrl, RecyclerView recyclerView,RecyclerView upcoming) {
        this.context = context;
        DatabaseUrl = databaseUrl;
        this.recyclerView = recyclerView;
        this.upcoming=upcoming;

        firebase.setAndroidContext(context);
        firebase=new Firebase(databaseUrl);
        matchesArrayList =new ArrayList<>();


    }

    public void refreshMatches(){
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void getUpdates(DataSnapshot dataSnapshot) {
        matchesArrayList.clear();
        for(DataSnapshot ds:dataSnapshot.getChildren())
        {
            matches match=new matches();
            match.setMatchDate(ds.getValue(matches.class).getMatchDate());
            match.setMatchno(ds.getValue(matches.class).getMatchno());
            match.setMatchTime(ds.getValue(matches.class).getMatchTime());
            match.setTeam1(ds.getValue(matches.class).getTeam1());
            match.setTeam2(ds.getValue(matches.class).getTeam2());

            matchesArrayList.add(match);
        }

        if(matchesArrayList.size()>0)
        {
            matchAdapter=new MatchAdapter(context,matchesArrayList);
            recyclerView.setAdapter(matchAdapter);

        }else {
            Toast.makeText(context,"No Data Found to add in Adapter",Toast.LENGTH_LONG).show();

        }





    }

    public ArrayList<matches> getMatchesArrayList() {
        return matchesArrayList;
    }
}
