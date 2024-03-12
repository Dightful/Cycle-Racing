package cycling;

public class Result {
    private int riderId;
    private int points;
    private int mountainPoints;
    private int rank;
    private int pointClassificationRank;
    private int mountainPointClassificationRank;

    public Result(int riderId, int points, int mountainPoints, int rank, int pointClassificationRank, int mountainPointClassificationRank) {
        this.riderId = riderId;
        this.points = points;
        this.mountainPoints = mountainPoints;
        this.rank = rank;
        this.pointClassificationRank = pointClassificationRank;
        this.mountainPointClassificationRank  = mountainPointClassificationRank;
    }

    public int getRiderId() {
        return riderId;
    }

    public int getPoints() {
        return points;
    }

    public int getMountainPoints() {
        return mountainPoints;
    }

    public int getRank() {
        return rank;
    }

    public int getPointClassificationRank() {
        return pointClassificationRank;
    }

    public int getMountainPointClassificationRank() {
        return mountainPointClassificationRank;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setMountainPoints(int mountainPoints) {
        this.mountainPoints = mountainPoints;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setPointClassificationRank(int pointClassificationRank) {
        this.pointClassificationRank = pointClassificationRank;
    }

    public void setMountainPointClassificationRank(int mountainPointClassificationRank) {
        this.mountainPointClassificationRank = mountainPointClassificationRank;
    }
    
}
