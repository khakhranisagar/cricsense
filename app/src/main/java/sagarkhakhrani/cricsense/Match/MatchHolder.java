package sagarkhakhrani.cricsense.Match;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import sagarkhakhrani.cricsense.R;

/**
 * Created by sagar.khakhrani on 24-03-2017.
 */

public class MatchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView team1,team2;


    public MatchHolder(View itemView) {
        super(itemView);
        team1=(ImageView)itemView.findViewById(R.id.team1imageView);
        team2=(ImageView)itemView.findViewById(R.id.team2imageView);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

    }
}
