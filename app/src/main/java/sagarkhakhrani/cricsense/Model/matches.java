package sagarkhakhrani.cricsense.Model;

/**
 * Created by sagar.khakhrani on 24-03-2017.
 */

public class matches {
    String matchDate;
    String matchTime;
    String matchno;
    String team1;
    String team2;

    public matches() {
    }

    public matches(String matchDate, String matchTime, String matchno, String team1, String team2) {
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.matchno = matchno;
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMatchno() {
        return matchno;
    }

    public void setMatchno(String matchno) {
        this.matchno = matchno;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }
}
