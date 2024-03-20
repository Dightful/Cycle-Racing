package cycling;

public class Team {
    private String name;
    private String nationality;
    private Rider[] riders;
    private int id;

    private String description; 

    public Team(String name, int id, String description) {
        this.name = name;
        this.id = id;
        this.nationality = nationality;
        this.riders = riders;
        this.description = description;
    }

    
    public String getTeamName() {
        return name;
    }

    public void setTeamName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Rider[] getRiders() {
        return riders;
    }

    public void setRiders(Rider[] riders) {
        this.riders = riders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void addRider(Rider newRider) {
        if (riders == null) {
            riders = new Rider[1];
            riders[0] = newRider;
        } else {
            Rider[] newRiders = new Rider[riders.length + 1];
            System.arraycopy(riders, 0, newRiders, 0, riders.length);
            newRiders[riders.length] = newRider;
            riders = newRiders;
        }
    }


}

//get riders method