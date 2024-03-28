

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
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException, IOException, ClassNotFoundException{
        System.out.println("The system compiled and started the execution...");
        CyclingPortal portal = new BadCyclingPortalImpl();
        


        assert (portal.getRaceIds().length == 0)
                : "Initial Portal not empty as required or not returning an empty array.";
        assert (portal.getTeams().length == 0)
                : "Initial Portal not empty as required or not returning an empty array.";

        //Run tests
        //portal = testCreateTeam(portal); - success
        //portal = testviewRaceDetails(portal); - success
        //portal = testRemoveRaceById(portal); - success
        //portal = testgetRaceStages(portal); - success
        //portal = testgenerateTeamId(portal);
        //portal = testcreateRace(portal); - success
        //portal = testGetStageLength(portal); - success
        //portal = testRemoveStageById(portal); - success
        //portal = testAddCategorizedClimbToStage(portal);
        //portal = testAddIntermediateSprintToStage(portal);

        //portal = testConcludeStagePreparation(portal); - success(Matt)
        //portal = testGetStageCheckpoints(portal); - succeess
        //portal = testRemoveTeam(portal); - success   
        //portal = testaddStageToRace(portal); - success
        //portal = testGetTeams(portal); - success
        //portal = testGetTeamRiders(portal); - success
        //portal = testCreateRider(portal); - success
        //portal = testRemoveRider(portal); - success
        //portal = testEraseCyclingPortal(portal); - success
        //portal = testSaveCyclingPortal(portal); - success
        //portal = testLoadCyclingPortal(portal); - success
        //portal = testRemoveRaceByName(portal); - success

        


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

        int raceId = portal.createRace("Test Race for add stages", "Tests add Stage to Race");

        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Hill", "Steep", 100, stageStartTime, StageType.HIGH_MOUNTAIN);

        CheckpointType decidedCheckpointType = CheckpointType.C3;
        CheckpointType decidedCheckpointType3 = CheckpointType.C2;
        portal.addCategorizedClimbToStage(stageId, 50d, decidedCheckpointType, 80d, 40d);
        portal.addCategorizedClimbToStage(stageId, 30d, decidedCheckpointType3, 30d, 40d);
        portal.concludeStagePreparation(stageId);

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
    

    // testRemoveCheckpoints

    public static CyclingPortal testGetStageCheckpoints(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        // Create a race
        int raceId = portal.createRace("Test Race", "This is a test race");

        // Add a stage to the race
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(raceId, "Flattest", "Easy", 100, stageStartTime, StageType.FLAT);

        // Add a categorizedClimb to the stage
        int checkpointId = portal.addCategorizedClimbToStage(stageId, 50.0, CheckpointType.C1, 10.0, 5.0);

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
        return portal;
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
            portal.createRider(teamId, "Rider1", 1990);
            portal.createRider(teamId, "Rider2", 1991);
            portal.createRider(teamId, "Rider3", 1992);

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

    public static CyclingPortal testCreateRider(CyclingPortal portal) throws IllegalNameException, InvalidNameException, IDNotRecognisedException{
        // Create a new team
        int teamId = portal.createTeam("TestTeamcreateRider", "This is a test team to create rider");
    
        // Create a new rider for the team
        int riderId = portal.createRider(teamId, "Rider 1", 1990);
        int riderId2 = portal.createRider(teamId, "Rider 2", 1991);
        //int riderId3 = portal.createRider(teamId, "Rider3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333", 1992); 
    
        // Print the ID of the new rider
        System.out.println("Rider ID: " + riderId);
        System.out.println("Rider ID: " + riderId2);
        //System.out.println("Rider ID: " + riderId3);
        int[] riderIds = portal.getTeamRiders(teamId);
        System.out.println(Arrays.toString(riderIds));
        return portal;
    }

    public static CyclingPortal testRemoveRider(CyclingPortal portal) throws IllegalNameException, InvalidNameException, IDNotRecognisedException {
        int teamId = portal.createTeam("TestTeamremoverider", "This is a test team to remove rider");
        int riderId = portal.createRider(teamId, "Rider69", 1995);
        int riderId2 = portal.createRider(teamId, "Rider420", 1996);
        System.out.println("Rider ID: " + riderId);
        System.out.println("Rider ID: " + riderId2);

        //Remove the Rider1
        portal.removeRider(riderId);
        //Show Rider2 still exists
        int[] riderIds = portal.getTeamRiders(teamId);
        System.out.println(Arrays.toString(riderIds));

        

        return portal;
    }



    //These are the functions that Matt asked me to do
    public static CyclingPortal testEraseCyclingPortal(CyclingPortal portal) throws IllegalNameException, InvalidNameException, IDNotRecognisedException{
        int teamId = portal.createTeam("TestTeamerasecheck", "This is a test team to chheck erasecylingportal");
        int riderId = portal.createRider(teamId, "Rider730", 1987);
        int riderId2 = portal.createRider(teamId, "Rider1080", 1964);
        System.out.println("Rider ID: " + riderId);
        System.out.println("Rider ID: " + riderId2);

        // Show the team riders before erasing the portal
        System.out.println(Arrays.toString(portal.getTeamRiders(teamId)));

        portal.eraseCyclingPortal();
        // Show everything is erased
        System.out.println(Arrays.toString(portal.getTeams()));

        return portal;
    }

    

    public static CyclingPortal testSaveCyclingPortal(CyclingPortal portal) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, IOException{
        int teamId = portal.createTeam("TestTeamsavecheck", "This is a test team to chheck sacecyclingportal");
        int riderId = portal.createRider(teamId, "Rider4", 1987);
        int riderId2 = portal.createRider(teamId, "Rider56", 1964);
        System.out.println("Rider ID: " + riderId);
        System.out.println("Rider ID: " + riderId2);

        // Show the team riders before erasing the portal
        System.out.println(Arrays.toString(portal.getTeamRiders(teamId)));
        String filename = "cyclingPortal.dat";
        portal.saveCyclingPortal(filename);
        
        

        return portal;
    }


    public static CyclingPortal testLoadCyclingPortal(CyclingPortal portal) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, IOException, ClassNotFoundException{
        int teamId = portal.createTeam("TestTeamLoadcheck", "This is a test team to chheck loadcyclingportal");
        int riderId = portal.createRider(teamId, "Rider43", 1977);
        int riderId2 = portal.createRider(teamId, "Rider59", 1999);
        System.out.println("Rider ID: " + riderId);
        System.out.println("Rider ID: " + riderId2);

        // Show the team riders before erasing the portal
        System.out.println(Arrays.toString(portal.getTeamRiders(teamId)));
        String filename = "cyclingPortal.dat";
        portal.saveCyclingPortal(filename);
        //Load it
        portal.loadCyclingPortal(filename);
        // Show that everything is loaded
        System.out.println(Arrays.toString(portal.getTeams()));
        
        

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




        
        

    
