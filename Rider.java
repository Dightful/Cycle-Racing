package cycling;

public class Rider {
    private String name;
    private String Nationality;
    private int Age;
    private int id;
    private Team team;

    //constructor
    public Rider(


    //getting and setting

    public String getname() {
        return name;
    }

    public void setname(String Name) {
        Name = name;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
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
