
package cycling;
import cycling.Stages;


public class Races {
    private Team[] teams;
    private String racename;
    private Stages[] stage;
    private int id;
    private String description;

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
        if (stage == null) {
            return stage.length;
        } else {
            return 0;
        }
    }
}

