

import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.LocalTime;
import cycling.StageType;
import cycling.Races;
import cycling.Stages;
import cycling.Team;
import java.io.File;
import java.io.IOException;

import cycling.BadCyclingPortalImpl;
import cycling.CheckpointType;
import cycling.IllegalNameException;
import cycling.CyclingPortal;
import cycling.InvalidNameException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.IDNotRecognisedException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.NameNotRecognisedException;

public class Impl_tests {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException{
        System.out.println("The system compiled and started the execution...");
        CyclingPortal portal = new BadCyclingPortalImpl();
        


        assert (portal.getRaceIds().length == 0)
                : "Initial Portal not empty as required or not returning an empty array.";
        assert (portal.getTeams().length == 0)
                : "Initial Portal not empty as required or not returning an empty array.";

        //Run tests
        // portal = testCreateTeam(portal);
        // portal = testviewRaceDetails(portal);
        // portal = testRemoveRaceById(portal);
        // portal = testgetRaceStages(portal);
        // portal = testgenerateTeamId(portal);
        //portal = testcreateRace(portal);
        // portal = testGetStageLength(portal);
        // portal = testRemoveStageById(portal);
        // portal = testAddCategorizedClimbToStage(portal);
        // portal = testAddIntermediateSprintToStage(portal);

        // portal = testConcludeStagePreparation(portal);
        // portal = testGetStageCheckpoints(portal);
        // portal = testRemoveTeam(portal);
        // portal = testaddStageToRace(portal);
        // portal = testGetTeams(portal);
        // portal = testGetTeamRiders(portal);
        // portal = testCreateRider(portal);
        // portal = testRemoveRider(portal);
        // portal = testEraseCyclingPortal(portal);
        // portal = testSaveCyclingPortal(portal);
        // portal = testLoadCyclingPortal(portal);
        // portal = testRemoveRaceByName(portal);

        


        //System.out.println("All tests passed.");
    }

    public static CyclingPortal testcreateRace(CyclingPortal portal) throws IllegalNameException, InvalidNameException {
        int raceId = portal.createRace("Test Create Race", "This is a test to check create Race");
        int raceId2 = portal.createRace("Test Create Race2", "This is a test to check create Race2");
        int[] raceIds = portal.getRaceIds();
        boolean foundrace = false;
        for (int id : raceIds) {
            if (id == raceId) {
                foundrace = true;
                break;
            }
        }

        if (foundrace) {
            System.out.println("Test create race was passed");
        } else {
            System.out.println("Test failed");
        }
        
        
        
        return portal;
    }

    
    public static CyclingPortal testgenerateTeamId(CyclingPortal portal) throws IllegalNameException, InvalidNameException {
        int teamId = portal.createTeam("Test Id team", "This is a test to check id generation");
        int [] teamIds = portal.getTeams();
        assert Arrays.stream(teamIds).anyMatch(id -> id == teamId) : "Team ID not found in the list of team IDs";
        return portal;
    }

    public static CyclingPortal testCreateTeam(CyclingPortal portal) throws IllegalNameException, InvalidNameException {
        int teamId = portal.createTeam("Test Team", "This is a test team");
        int[] teamIds = portal.getTeams();

        // Check that the team was created and its ID is in the list of team IDs
        assert Arrays.stream(teamIds).anyMatch(id -> id == teamId) : "Team ID not found in the list of team IDs";
        return portal;
    }

    public static CyclingPortal testviewRaceDetails(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException{
        int raceId = portal.createRace("Test Race", "This is a test race");
        String expectedDetails = "Name: Test Race\nDescription: This is a test race;";
        String actualDetails = portal.viewRaceDetails(raceId);
        assert expectedDetails.equals(actualDetails) : "Race details do not match expected details";
        return portal;
    }
    
    public static CyclingPortal testRemoveRaceById(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException {
        int raceId = portal.createRace("Test Race2", "This is another test race");
        // Remove race
        portal.removeRaceById(raceId);
        //Check that race has been removed
        try {
            portal.viewRaceDetails(raceId);
            assert false : "Race was not removed";
        } catch (IDNotRecognisedException e) {
            //This is expected
        }
        return portal;
    }
    //-------------
    
    public static CyclingPortal testaddStageToRace(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {

        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(0, "Hill", "Steep", 100, stageStartTime, StageType.HIGH_MOUNTAIN);

        System.out.println(portal.getStageLength(stageId));

        return portal;
    }
    ///----------------
    public static CyclingPortal testgetRaceStages(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        // Create a race
        int raceId = portal.createRace("Test Race", "This is a test race");

        // Add a stage to the race
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Hill", "Steep", 100, stageStartTime, StageType.HIGH_MOUNTAIN);
        

        // Get the stages of the race
        int[] stageIds = portal.getRaceStages(raceId);

        // Print the stage IDs
        int[] stages = portal.getRaceStages(raceId);
        for (int i = 0; i < stages.length; i++) {
            System.out.println(stages[i]);
        } // Should print the ID of the stage we just added

        return portal;
    }
    
    public static CyclingPortal testGetStageLength(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        // Create a race
        int raceId = portal.createRace("Test Race", "This is a test race");

        // Add a stage to the race
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Hill", "Steep", 100, stageStartTime, StageType.HIGH_MOUNTAIN);

        // Get the length of the stage
        double stageLength = portal.getStageLength(stageId);

        // Print the stage length
        System.out.println("Stage length: " + stageLength);  // Should print 100

        // Try to get the length of a non-existent stage
        try {
            portal.getStageLength(-1);
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No stage with ID -1 was found"
        }
        return portal;

    }

    public static CyclingPortal testRemoveStageById(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        // Create a race
        int raceId = portal.createRace("Test Race", "This is a test race");

        // Add a stage to the race
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Hill", "Steep", 100, stageStartTime, StageType.HIGH_MOUNTAIN);

        // Remove the stage by its ID
        portal.removeStageById(stageId);

        // Try to get the length of the removed stage
        try {
            portal.getStageLength(stageId);
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No stage with ID [stageId] was found"
        }

        // Try to remove a non-existent stage
        try {
            portal.removeStageById(-1);
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No stage with ID -1 was found"
        }
        return portal;
    }

    public static CyclingPortal testAddCategorizedClimbToStage(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        // Create a race
        int raceId = portal.createRace("Test Race", "This is a test race");
        // Add a stage to race
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Hill", "Steep", 100, stageStartTime, StageType.HIGH_MOUNTAIN);
         // Add a categorized climb to the stage
        int climbId = portal.addCategorizedClimbToStage(stageId, 50.0, CheckpointType.C1, 10.0, 5.0);
        //Print the climb ID
        System.out.println(climbId);
        return portal;


    }
    
    public static CyclingPortal testAddIntermediateSprintToStage(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
            // Create a race
        int raceId = portal.createRace("Test Race", "This is a test race");

        // Add a stage to the race
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Flat", "Easy", 100, stageStartTime, StageType.SPRINT);

        // Add an intermediate sprint to the stage
        int sprintId = portal.addIntermediateSprintToStage(stageId, 50.0);

        // Print the sprint ID
        System.out.println("Sprint ID: " + sprintId);
        return portal;
    }

    // testRemoveCheckpoints

    public static CyclingPortal testConcludeStagePreparation(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        // Create a race
        int raceId = portal.createRace("Test Race", "This is a test race");

        // Add a stage to the race
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Flat", "Easy", 100, stageStartTime, StageType.SPRINT);

        // Conclude the stage preparation
        portal.concludeStagePreparation(stageId);

        // Try to conclude the preparation of a non-existent stage
        try {
            portal.concludeStagePreparation(-1);
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No stage with ID -1 was found"
        }
        return portal;
    }

    public static CyclingPortal testGetStageCheckpoints(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        // Create a race
        int raceId = portal.createRace("Test Race", "This is a test race");

        // Add a stage to the race
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Flat", "Easy", 100, stageStartTime, StageType.SPRINT);

        // Add a checkpoint to the stage
        int checkpointId = portal.addCheckpointToStage(stageId, 50.0);

        // Get the checkpoints of the stage
        int[] checkpointIds = portal.getStageCheckpoints(stageId);

        // Print the checkpoint IDs
        for (int id : checkpointIds) {
            System.out.println("Checkpoint ID: " + id);
        }

        // Try to get the checkpoints of a non-existent stage
        try {
            portal.getStageCheckpoints(-1);
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No stage with ID -1 was found"
        }
    }
    
    public static CyclingPortal testRemoveTeam(CyclingPortal portal) {
        // Test with valid inputs
        try {
            int teamId = portal.createTeam("Test Team", "This is a test team");
            portal.removeTeam(teamId);
        } catch (IllegalNameException | InvalidNameException | IDNotRecognisedException e) {
            System.out.println(e.getMessage());
        }

        // Test with invalid inputs
        try {
            portal.removeTeam(-1);
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No team with ID -1 was found"
        }
        return portal;
    }

    public static CyclingPortal testGetTeams(CyclingPortal portal) {
        try {
            portal.createTeam("Test Team 1", "This is test team 1");
            portal.createTeam("Test Team 2", "This is test team 2");
            portal.createTeam("Test Team 3", "This is test team 3");
        } catch (IllegalNameException | InvalidNameException e) {
            System.out.println(e.getMessage());
        }
    
        // Get the IDs of all teams
        int[] teamIds = portal.getTeams();
    
        // Print the team IDs
        for (int id : teamIds) {
            System.out.println("Team ID: " + id);
        }
        return portal;
    }

    public static CyclingPortal testGetTeamRiders(CyclingPortal portal) {
        // Create a team and add a few riders
        try {
            int teamId = portal.createTeam("Test Team", "This is a test team");
            portal.createRider(teamId, "Rider 1", 1990);
            portal.createRider(teamId, "Rider 2", 1991);
            portal.createRider(teamId, "Rider 3", 1992);

            // Get the IDs of all riders in the team
            int[] riderIds = portal.getTeamRiders(teamId);

            // Print the rider IDs
            for (int id : riderIds) {
                System.out.println("Rider ID: " + id);
            }
        } catch (IllegalNameException | InvalidNameException | IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return portal;
    }

    public static CyclingPortal testCreateRider(CyclingPortal portal) {
        // Create a team
        int teamId = -1;
        try {
            teamId = portal.createTeam("Test Team", "This is a test team");
        } catch (IllegalNameException | InvalidNameException e) {
            System.out.println(e.getMessage());
        }

        // Test with valid inputs
        try {
            int riderId = portal.createRider(teamId, "Rider 1", 1990);
            System.out.println("Rider ID: " + riderId);
        } catch (IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test with invalid inputs
        try {
            portal.createRider(teamId, "", 1990);
        } catch (IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());  // Should print "Name must not be null or empty, must not exceed character limit of 50 and characters must contain not only whitespace"
        }

        try {
            portal.createRider(teamId, "Rider 2", 1890);
        } catch (IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());  // Should print "Year of birth must be between 1900 and 2005"
        }

        // Test with a non-existent team ID
        try {
            portal.createRider(-1, "Rider 3", 1990);
        } catch (IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());  // Should print "No team with ID -1 was found"
        }
        return portal;
    }

    public static CyclingPortal testRemoveRider(CyclingPortal portal) {
        // Create a team and add a few riders
        int teamId = -1;
        int riderId = -1;
        try {
            teamId = portal.createTeam("Test Team", "This is a test team");
            riderId = portal.createRider(teamId, "Rider 1", 1990);
            portal.createRider(teamId, "Rider 2", 1991);
            portal.createRider(teamId, "Rider 3", 1992);
        } catch (IllegalNameException | InvalidNameException | IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test with valid inputs
        try {
            portal.removeRider(riderId);
            System.out.println("Rider removed successfully");
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());
        }

        // Test with invalid inputs
        try {
            portal.removeRider(-1);
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No rider with ID -1 was found"
        }
        return portal;
    }
    //These are the functions that Matt asked me to do
    public static CyclingPortal testEraseCyclingPortal(CyclingPortal portal) {
        // Create a team and add a few riders
        int teamId = -1;
        try {
            teamId = portal.createTeam("Test Team", "This is a test team");
            portal.createRider(teamId, "Rider 1", 1990);
            portal.createRider(teamId, "Rider 2", 1991);
            portal.createRider(teamId, "Rider 3", 1992);
        } catch (IllegalNameException | InvalidNameException | IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Call eraseCyclingPortal
        portal.eraseCyclingPortal();

        // Verify that all data has been erased
        try {
            portal.getTeamRiders(teamId);
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No team with ID <teamId> was found"
        }
        return portal;
    }

    

    public static CyclingPortal testSaveCyclingPortal(CyclingPortal portal) {
        // Create a team and add a few riders
        int teamId = -1;
        try {
            teamId = portal.createTeam("Test Team", "This is a test team");
            portal.createRider(teamId, "Rider 1", 1990);
            portal.createRider(teamId, "Rider 2", 1991);
            portal.createRider(teamId, "Rider 3", 1992);
        } catch (IllegalNameException | InvalidNameException | IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Call saveCyclingPortal
        String filename = "cyclingPortal.dat";
        try {
            portal.saveCyclingPortal(filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Verify that the file exists and is not empty
        File file = new File(filename);
        if (file.exists() && file.length() > 0) {
            System.out.println("File saved successfully");
        } else {
            System.out.println("File not saved or is empty");
        }
        return portal;
    }

    public static CyclingPortal testLoadCyclingPortal(CyclingPortal portal) {
        // Create a portal, a team, and add a few riders
        BadCyclingPortalImpl portal1 = new BadCyclingPortalImpl();
        int teamId = -1;
        try {
            teamId = portal1.createTeam("Test Team", "This is a test team");
            portal1.createRider(teamId, "Rider 1", 1990);
            portal1.createRider(teamId, "Rider 2", 1991);
            portal1.createRider(teamId, "Rider 3", 1992);
        } catch (IllegalNameException | InvalidNameException | IDNotRecognisedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Save the state of the portal to a file
        String filename = "cyclingPortal.dat";
        try {
            portal1.saveCyclingPortal(filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Create a new portal and load the state from the file
        BadCyclingPortalImpl portal2 = new BadCyclingPortalImpl();
        try {
            portal2.loadCyclingPortal(filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Verify that the state of the portal has been restored
        try {
            int[] riderIds = portal2.getTeamRiders(teamId);
            for (int id : riderIds) {
                System.out.println("Rider ID: " + id);
            }
        } catch (IDNotRecognisedException e) {
            System.out.println(e.getMessage());
        }
        return portal;
    }

    public static CyclingPortal testRemoveRaceByName(CyclingPortal portal) {
        // Create a few races
        try {
            portal.createRace("Test Race 1", "This is test race 1");
            portal.createRace("Test Race 2", "This is test race 2");
            portal.createRace("Test Race 3", "This is test race 3");
        } catch (IllegalNameException | InvalidNameException e) {
            System.out.println(e.getMessage());
        }

        // Test with valid inputs
        try {
            portal.removeRaceByName("Test Race 1");
            System.out.println("Race removed successfully");
        } catch (NameNotRecognisedException e) {
            System.out.println(e.getMessage());
        }

        // Test with invalid inputs
        try {
            portal.removeRaceByName("Invalid Race");
        } catch (NameNotRecognisedException e) {
            System.out.println(e.getMessage());  // Should print "No race with name Invalid Race was found"
        }
        return portal;
    }
        







}




        
        

    
