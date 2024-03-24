package cycling;

public class IntermediateSprint {
    private static int idCounter = 0;
    private int id;
    private Double location;

    public IntermediateSprint(Double location) {
        this.id = idCounter++;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public Double getLocation() {
        return location;
    }
}
