package cycling;

public class Team {
    private String name;
    private String nationality;
    private Riders[] riders;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Riders[] getRiders() {
        return riders;
    }

    public void setRiders(Riders[] riders) {
        this.riders = riders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
