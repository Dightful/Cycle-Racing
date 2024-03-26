package cycling;

public class Checkpoint {
    private int CheckpointId; 
    private CheckpointType checkpointType;


    //Constructor
    public Checkpoint(int CheckpointId, CheckpointType checkpointType) {
        this.CheckpointId = CheckpointId;
        this.checkpointType = checkpointType;
    }

    //Get Checkpoint Id
    public int getCheckpointId() {
        return CheckpointId;
    }

    //Create an instance of checkpointType
    public CheckpointType getCheckpointType() {
        return checkpointType;
    }
}