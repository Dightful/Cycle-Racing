package cycling;

public class Rider {
    private String name;
    private String Nationality;
    private int Age;
    private int RiderId;
    private Team team;
    private int YearOfBirth;

    //constructor
    public Rider(String name, int RiderId, int YearOfBirth) {
        this.name = name;
        this.RiderId = RiderId;
        this.YearOfBirth = YearOfBirth;

    }
    //getting and setting for all attributes

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
        return RiderId;
    }

    public void setId(int RiderId) {
        this.RiderId = RiderId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
    public int getYearOfBirth() {
        return YearOfBirth;
    }

    public void setYearOfBirth(int YearOfBirth) {
        this.YearOfBirth = YearOfBirth;
    }

    // Returning the name in a more readable format
    public String toString() {
        return "Rider: " + name; 
    }

}
