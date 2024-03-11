'''
This class is used to represent the stages of the race, with the total distance required to be gained by each one
'''
package cycling;
import java.time.LocalDateTime

public class Stages {
    private int type;
    private double length;
    private Checkpoint[] checkpoints;
    private int id;
    private LocalDateTime startTime;
    private String stageName;

    public Stages(int type, double length, Checkpoint[] checkpoints, LocalDateTime startTime, String stageName) {
        this.type = type;
        this.length = length;
        this.checkpoints = checkpoints;
        this.startTime = startTime;
        this.id = generateId();
        this.stageName = stageName;
    }


    // getting and setting
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getlength() {
        return length;
    }

    public void setlength(double length) {
        this.length = length;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public String getStageName() {
        return stageName;
    }
    public void setStageName(String stageName) {
        this.stageName = stageName;

    }
}

public LocalTime[] getRiderResults(int riderId) {
    for (Result result : resultsList) {
        if (result.getRider().getId() == riderId) {
            return result.getTimes();
        }
    }
    return null;
}

//need a calculatedAdjustedTime method

public boolean deleteRiderResults(int riderId) {
    for (Iterator<Result> iterator = resultsList.iterator(); iterator.hasNext();) {
        Result result = iterator.next();
        if (result.getRiderId() == riderId) {
            iterator.remove();
            return true;
        }
    }
    return false;
}