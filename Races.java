
package cycling;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Races {
    private Team[] teamsArray;
    private String racename;
    private Stages[] stagesArray;
    private int id;
    private String description;
    //private list<list<object>> generalClassification

    public Races(String name, String description) {
        this.racename = name;
        this.description = description;
        
    }


    public Team[] getTeams() {
        return teamsArray;
    }

    public void setTeams(Team[] teamsArray) {
        this.teamsArray = teamsArray;
    }

    public String getRacename() {
        return racename;
    }

    public void setRacename(String racename) {
        this.racename = racename;
    }

    public Stages[] getStages() {
        return stagesArray;
    }

    public void setStages(Stages[] stages) {
        this.stagesArray = stages; 
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

    public void setDescription(String description) {
        this.description = description;
    }


    public int getTotalStages() {
        if (stagesArray != null) {
            return stagesArray.length;
        } else {
            return 0;
        }
    }


    public void addTeam(Team team) {
        if (teamsArray == null) {
            teamsArray = new Team[] { team };
        } else {
            List<Team> teamList = new ArrayList<>(Arrays.asList(teamsArray));
            teamList.add(team);
            teamsArray = teamList.toArray(new Team[0]);
        }
    }

    public void removeTeam(Team team) {
        if (teamsArray != null) {
            int index = -1;
            for (int i = 0; i < teamsArray.length; i++) {
                if (teamsArray[i].equals(team)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Team[] newTeams = new Team[teamsArray.length - 1];
                for (int i = 0, j = 0; i < teamsArray.length; i++) {
                    if (i != index) {
                        newTeams[j++] = teamsArray[i];
                        
                    }
                }
                teamsArray = newTeams;
            }
        }
    }

    public void addStage(Stages stage) {
        if (this.stagesArray == null) {
            this.stagesArray = new Stages[] { stage };
        }
        else {
            List<Stages> stageList = new ArrayList<>(Arrays.asList(this.stagesArray));
            stageList.add(stage);
            this.stagesArray = stageList.toArray(new Stages[0]);
        }
    }

    public void removeStage(Stages stage) {
        if (this.stagesArray != null) {
            int index = -1;
            for (int i = 0; i < this.stagesArray.length; i++) {
                if (this.stagesArray[i].equals(stage)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Stages[] newStages = new Stages[this.stagesArray.length - 1];
                for (int i = 0, j = 0; i < this.stagesArray.length; i++) {
                    if (i != index) {
                        newStages[j++] = this.stagesArray[i];
                        j++;
                    }
                }
                this.stagesArray = newStages;
            }
        }
    }

    public String getTeamName(int teamid) {
        if (teamsArray != null) {
            for (Team team : teamsArray) {
                if (team.getId() == teamid) {
                    return team.getTeamName();
                }
            }
        }
        return null;
    }
//--------------
    public List<List<Result>> getRaceResults() {
        List<List<Result>> raceResults = new ArrayList<>();
        if (this.stagesArray != null) {
            for (Stages stage : stagesArray) {
                raceResults.add(stage.getResults());
            }
        }
        return raceResults;
    }

    

//--------------
//Sum of riders times



//Calculate the sum of the points of the riders at the end of the race for points classification
//Im going to assume I want to sum all of the riders points up and store them in an array that is unsorted


}