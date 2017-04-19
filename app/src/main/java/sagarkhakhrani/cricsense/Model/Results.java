package sagarkhakhrani.cricsense.Model;

/**
 * Created by sagar.khakhrani on 04-04-2017.
 */

public class Results {
    String matchno;
    String winningTeam;

    public Results() {
    }

    public Results(String matchno, String winningTeam) {
        this.matchno = matchno;
        this.winningTeam = winningTeam;
    }

    public String getMatchno() {
        return matchno;
    }

    public void setMatchno(String matchno) {
        this.matchno = matchno;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }
}
