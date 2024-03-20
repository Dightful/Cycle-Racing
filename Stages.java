/** 
This class is used to represent the stages of the race, with the total distance required to be gained by each one
*/

package cycling;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

public class Stages {
    private StageType type; //
    private double length;
    private Checkpoint[] checkpoints;
    private int id;
    private LocalDateTime startTime;
    private String name;
    private StageState state;
    private CheckpointType checkpointtype;
    private PointsClassification pointsClassification;

    public Stages(String name, String description, double length, LocalDateTime startTime, StageType type) {
        this.type = type;
        this.length = length;
        this.startTime = startTime;
        this.name = name;
    }


    // getting and setting
    public CheckpointType getCheckpointType() {
        return checkpointtype;
    }

    public void setCheckpointType(CheckpointType checkpointtype) {
        this.checkpointtype = checkpointtype;
    }


    public StageType getStageType() {
        return type;
    }

    public void setStageType(StageType type) {
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
        return name;
    }
    public void setStageName(String stageName) {
        this.name = stageName;

    }

    private List<Result> resultsList;


    public LocalTime[] getRiderResults(int riderId) {
        for (Result result : resultsList) {
            if (result.getRider().getId() == riderId) {
                return result.getRiderTimes();
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

    public StageState getState() {
        return state;
    }

    public void setState(StageState newstate) {
        this.state = newstate;
    }

    public List<Result> getResults() {
        return resultsList;
    }

    public void calculatePointsClassificaiton(int points) {
        this.pointsClassification = new PointsClassification(points);
    }

}

//set stage method

//for each rider
//get rider id
