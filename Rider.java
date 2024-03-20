package cycling;

public class Rider {
    private String name;
    private String Nationality;
    private int Age;
    private int id;
    private Team team;
    private int yearOfBirth;

    //constructor
    public Rider(String name, int id, int yearOfBirth) {
        this.name = name;
        this.id = id;
        this.yearOfBirth = yearOfBirth;

    }
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
        return "Rider: " + name; 
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

}
