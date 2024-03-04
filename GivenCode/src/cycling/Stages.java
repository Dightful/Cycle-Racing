'''
This class is used to represent the stages of the race, with the total distance required to be gained by each one
'''
package cycling;

public class Stages {
    private int type;
    private double distance;
    private Checkpoint[] checkpoints;
    private int id;


    // getting and setting
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Checkpoint[] checkpoints) {
        this.checkpoints = checkpoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}