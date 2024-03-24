package cycling;

public class CategorizedClimb {
    private static int idCount = 0;
    private int id;
    private Double location;
    private CheckpointType type;
    private Double averageGradient;
    private Double length;

    public CategorizedClimb(Double location, CheckpointType type, Double averageGradient, Double length) {
        this.id = idCount++;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public Double getLocation() {
        return location;
    }

    public CheckpointType getType() {
        return type;
    }

    public Double getAverageGradient() {
        return averageGradient;
    }

    public Double getLength() {
        return length;
    }
}
