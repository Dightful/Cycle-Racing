package cycling;

public class Checkpoint {
    private int id; 
    private CheckpointType checkpointType;


    //Constructor
    public Checkpoint(int id, CheckpointType checkpointType) {
        this.id = id;
        this.checkpointType = checkpointType;
    }

    public int getCheckpointId() {
        return id;
    }

    //Create an instance of checkpointType
    public CheckpointType getCheckpointType() {
        return checkpointType;
    }
}