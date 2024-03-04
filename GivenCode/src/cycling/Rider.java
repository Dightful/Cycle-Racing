package cycling;

public class Rider {
    private String Firstname;
    private String Lastname;
    private String Nationality;
    private int Age;
    private int id;
    private Team team;

    //getting and setting

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationility) {
        Nationalty = nationality;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String toString() {
        return "Rider: " + Firstname + " " + Lastname; 
    }

}
