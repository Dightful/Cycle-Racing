'''
This is the Races class that stores Teams, RaceName and Stages as attributes along with its getters and setters
'''
package cycling;
import cycling.Stages;


public class Races {
    private Team[] teams;
    private String racename;
    private Stages[] stage;

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public String getRacename() {
        return racename;
    }

    public void setRacename(String racename) {
        this.racename = racename;
    }

    public Stages[] getStages() {
        return stage;
    }

    public void setStages(Stages[] stages) {
        this.stage = stages; 
    }
}