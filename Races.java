
package cycling;
import cycling.Stages;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Races {
    private Team[] teams;
    private String racename;
    private Stages[] stage;
    private int id;
    private String description;
    //private list<list<object>> generalClassification

    public Races(String name, String description) {
        this.racename = name;
        this.description = description;
        this.id = generateId();
    }


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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getTotalStages() {
        if (stage != null) {
            return stage.length;
        } else {
            return 0;
        }
    }

    public void addTeam(Team team) {
        if (teams == null) {
            teams = new Team[] { team };
        } else {
            List<Team> teamList = new ArrayList<>(Arrays.asList(teams));
            teamList.add(team);
            teams = teamList.toArray(new Team[0]);
        }
    }

    public void removeTeam(Team team) {
        if (teams != null) {
            int index = -1;
            for (int i = 0; i < teams.length; i++) {
                if (teams[i].equals(team)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Team[] newTeams = new Team[teams.length - 1];
                for (int i = 0, j = 0; i < teams.length; i++) {
                    if (i != index) {
                        newTeams[j++] = teams[i];
                        j++;
                    }
                }
                teams = newTeams;
            }
        }
    }

    public void addStage(Stages stage) {
        if (this.stage == null) {
            this.stage = new Stages[] { stage };
        }
        else {
            List<Stages> stageList = new ArrayList<>(Arrays.asList(this.stage));
            stageList.add(stage);
            this.stage = stageList.toArray(new Stages[0]);
        }
    }

    public void removeStage(Stages stage) {
        if (this.stage != null) {
            int index = -1;
            for (int i = 0; i < this.stage.length; i++) {
                if (this.stage[i].equals(stage)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Stages[] newStages = new Stages[this.stage.length - 1];
                for (int i = 0, j = 0; i < this.stage.length; i++) {
                    if (i != index) {
                        newStages[j++] = this.stage[i];
                        j++;
                    }
                }
                this.stage = newStages;
            }
        }
    }

    
}

//Sum of riders times