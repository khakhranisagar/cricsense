package sagarkhakhrani.cricsense.Match;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sagarkhakhrani.cricsense.Model.matches;
import sagarkhakhrani.cricsense.R;

/**
 * Created by sagar.khakhrani on 24-03-2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchHolder> {
    Context context;
    ArrayList<matches> matches;

    public MatchAdapter(Context context, ArrayList<sagarkhakhrani.cricsense.Model.matches> matches) {
        this.context = context;
        this.matches = matches;
    }



    @Override
    public MatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.match_card,parent,false);
        MatchHolder holder=new MatchHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MatchHolder holder, int position) {
        switch (matches.get(position).getTeam1()){
            case "srh":
                holder.team1.setImageResource(R.drawable.srh);
                break;
            case "dd":
                holder.team1.setImageResource(R.drawable.dd);
                break;
            case "gl":
                holder.team1.setImageResource(R.drawable.gl);
                break;
            case "kkr":
                holder.team1.setImageResource(R.drawable.kkr);
                break;
            case "kvip":
                holder.team1.setImageResource(R.drawable.kvip);
                break;
            case "mi":
                holder.team1.setImageResource(R.drawable.mi);
                break;
            case "rcb":
                holder.team1.setImageResource(R.drawable.rcb);
                break;
            case "rps":
                holder.team1.setImageResource(R.drawable.rps);
                break;
            default:
                holder.team1.setImageResource(R.mipmap.ic_launcher);
                break;

        }

        switch (matches.get(position).getTeam2()){
            case "srh":
                holder.team2.setImageResource(R.drawable.srh);
                break;
            case "dd":
                holder.team2.setImageResource(R.drawable.dd);
                break;
            case "gl":
                holder.team2.setImageResource(R.drawable.gl);
                break;
            case "kkr":
                holder.team2.setImageResource(R.drawable.kkr);
                break;
            case "kvip":
                holder.team2.setImageResource(R.drawable.kvip);
                break;
            case "mi":
                holder.team2.setImageResource(R.drawable.mi);
                break;
            case "rcb":
                holder.team2.setImageResource(R.drawable.rcb);
                break;
            case "rps":
                holder.team2.setImageResource(R.drawable.rps);
                break;
            default:
                holder.team2.setImageResource(R.mipmap.ic_launcher);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}
