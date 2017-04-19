package sagarkhakhrani.cricsense.Model;

/**
 * Created by sagar.khakhrani on 03-04-2017.
 */

public class Prediction {
    String matchno;
    String userName;
    String selectedTeam;
    String selectedRate;
    String pointsOnBet;

    public Prediction() {
    }

    public Prediction(String matchno, String userName, String selectedTeam, String selectedRate, String pointsOnBet) {
        this.matchno = matchno;
        this.userName = userName;
        this.selectedTeam = selectedTeam;
        this.selectedRate = selectedRate;
        this.pointsOnBet = pointsOnBet;
    }

    public String getMatchno() {
        return matchno;
    }

    public void setMatchno(String matchno) {
        this.matchno = matchno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(String selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public String getSelectedRate() {
        return selectedRate;
    }

    public void setSelectedRate(String selectedRate) {
        this.selectedRate = selectedRate;
    }

    public String getPointsOnBet() {
        return pointsOnBet;
    }

    public void setPointsOnBet(String pointsOnBet) {
        this.pointsOnBet = pointsOnBet;
    }
}
