

import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.LocalTime;
import cycling.StageType;
import cycling.Races;
import cycling.Stages;
import cycling.Team;

import cycling.BadCyclingPortalImpl;
import cycling.IllegalNameException;
import cycling.CyclingPortal;
import cycling.InvalidNameException;
import cycling.IDNotRecognisedException;
import cycling.InvalidLengthException;

public class Impl_tests {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException {
        System.out.println("The system compiled and started the execution...");
        CyclingPortal portal = new BadCyclingPortalImpl();
        


        assert (portal.getRaceIds().length == 0)
                : "Initial Portal not empty as required or not returning an empty array.";
        assert (portal.getTeams().length == 0)
                : "Initial Portal not empty as required or not returning an empty array.";

        //Run tests
        portal = testCreateTeam(portal);
        portal = testviewRaceDetails(portal);
        portal = testRemoveRaceById(portal);
        portal = testgenerateTeamId(portal);
        testaddStageToRace();



        System.out.println("All tests passed.");
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
    
    public static CyclingPortal testaddStageToRace(CyclingPortal portal) throws IDNotRecognisedException, IllegalNameException, InvalidNameException {
        int raceId = portal.
        
        // Retrieve the stages array
        int[] stagesArray = portal.getRaceStages(raceId);

        // Check that the stageId is found in the stagesArray
        boolean found = false;
        for (int stageId : stagesArray) {
            if (stageId == stageId) {
                found = true;
                break;
            }
        }

        assert found : "The stageId was not found in the stagesArray";
        return portal;
    }
    
    public static void testgetRaceStages() throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException{
        BadCyclingPortalImpl portal = new BadCyclingPortalImpl();
        int raceId = portal.createRace("Test Race", "This is a test race");
        String stageName = "Stage 1";
        String stageDescription = "This is the first stage";
        double stageLength = 100;
        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        StageType stageType = StageType.HIGH_MOUNTAIN;
        Stages stage = new Stages(stageName, stageDescription, stageLength, stageStartTime, stageType);
        int stageId = portal.addStageToRace(raceId, stageName, stageDescription, stageLength, stageStartTime, stageType);
        int[] stageIds = portal.getRaceStages(1);
        System.out.println(Arrays.toString(stageIds));  // Should print "[1, 2]"

    }
    

    

}



    
