package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//Added packages (if we are allowed)
import java.util.Arrays;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Comparator;

/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class BadCyclingPortalImpl implements CyclingPortal {

	// Creates an empty Array of Team objects
	private Team[] teamsArray = new Team[0];
	//Creates an empty Array of Stages objects
	private Stages[] stagesArray = new Stages[0];
	//Creates an empty Array of Rider objects
	private Rider[] ridersArray = new Rider[0]; 


	//Team counter for team id
	private int generateteamId() {
		return teamsArray.length + 1;
	
	}

	

	private List<Races> racesList = new ArrayList<>();
	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[racesList.size()];
		for (int i = 0; i < racesList.size(); i++) {
			raceIds[i] = racesList.get(i).getId();
		}
		return raceIds;
	}


	@Override
	// I need name, teams, id, description, totalstages
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// Validate name and description
		if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
			throw new InvalidNameException("Name and description must not be null or empty");
		}

		// Check if a race with the same name already exists
		for (Races race : racesList) {
			if (race.getRacename().equals(name)) {
				throw new IllegalNameException("A race with this name already exists");
			}
		}

		// Create a new race and add it to the list
		Races newRace = new Races(name, description);
		//newRace.setId(generateraceId());
		racesList.add(newRace);

		// Return ID of new race
		return newRace.getId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				// Format details
				String details = "Name: " + race.getRacename() +
				                 "\nDescription" + race.getDescription() +
								 "\n Total Stages: " + race.getTotalStages();
				return details;
			}
		}
		
		//If race withgiven ID is not found, throw exception
		throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		for (int i = 0; i < racesList.size(); i++) {
			if (racesList.get(i).getId() == raceId) {
				racesList.remove(i);
				return;
			}
		}

		//If the race with given Id is not found
		throw new IDNotRecognisedException("No race with ID" + raceId + "was found");

	}


	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				return race.getTotalStages();
			}
		}
		

		//If race is not found with given Id, throw exception
		throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
	}

	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public int addStageToRace(int raceId, String name, String description, double length, LocalDateTime startTime, StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {

		// Validate stageName
		if (name == null || name.isEmpty() || name.length() > 50 || name.trim().isEmpty()) {
			throw new InvalidNameException("Name must not be null or empty, must not exceed character limit of 50 and characters must contain not only whitespace");
		}

		// Validate length
		if (length < 5) {
			throw new InvalidLengthException("Length must not be less than 5 km");
		}

		for (Races race : racesList) {
			if (race.getId() == raceId) {
				// Check if stageName is already in use
				for (Stages stage : race.getStages()) {
					if (stage.getStageName().equals(name)) {
						throw new IllegalNameException("Name is already in use");
					}
				}

				// Create a new stage and add it to the race
				Stages newStage = new Stages(name, description, length, startTime, type);
				race.addStage(newStage);

				// Return ID of new stage
				return newStage.getId();
			}
		}

		// If the race with the given ID is not found, throw an exception
		throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
	}
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				Stages[] stages = race.getStages();
				int[] stageIds = new int[stages.length];
				for (int i = 0; i < stages.length; i++) {
					stageIds[i] = stages[i].getId();
				}
				return stageIds;
			}
		}
		throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
		
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		for (Races race : racesList) {
			for (Stages stage : race.getStages()) {
				if (stage.getId() == stageId) {
					return stage.getlength();
				}
			}
		}
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		boolean stageFound = false;
		for (Races race : racesList) {
			Stages[] stages = race.getStages();
			for (int i = 0; i < stages.length; i++) {
				if (stages[i].getId() == stageId) {
					Stages[] newStages = new Stages[stages.length - 1];
					for (int j = 0, k = 0; j < stages.length; j++) {
						if (j != i) {
							newStages[k++] = stages[j];
					}
				}
				race.setStages(newStages);
				stageFound = true;
				break;
			
			}
		}
		if (stageFound) {
			break;
		}
	}
	if (!stageFound) {
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
	}

	}


	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Checkpoint type
	// Preparation stage state
	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		for (Races race : racesList){
			for (Stages stage : race.getStages()) {
				// Check if stage type is suitable for adding a categorized climb
				if (stage.getCheckpointType() != CheckpointType.C1 && stage.getCheckpointType() != CheckpointType.C2 && stage.getCheckpointType() != CheckpointType.C3 && stage.getCheckpointType() != CheckpointType.C4 && stage.getCheckpointType() != CheckpointType.HC){
					throw new InvalidStageTypeException("The stage type must be C1, C2, C3, C4, HC to add a categorized climb");
				}
				//Check if the stage is in a state that allows for a categorized climb to be added
				if (stage.getState() != StageState.PREPARATION) {
					throw new InvalidStageStateException("The stage must be in the PREPARATION state to add a categorized climb");
				}
				//Check if the location is valid
				if (location < 0 || location > stage.getlength()) {
					throw new InvalidLocationException("The location must be between 0 and the stage length");
				}
				// If all checks are successful, add the categorized climb
				CategorizedClimb climb = new CategorizedClimb(location, type, averageGradient, length);
				stage.addCategorizedClimb(climb);
				return climb.getId();
			}
		}
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
		
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		for (Races race : racesList) {
			for (Stages stage : race.getStages()) {
				// Check if stage type is suitable for adding an intermediate sprint
				if (stage.getCheckpointType() != CheckpointType.SPRINT) {
    				throw new InvalidStageTypeException("The stage type must be a SPRINT to add an intermediate sprint");
}
				//Check if the stage is in a state that allows for an intermediate sprint to be added
				if (stage.getState() != StageState.PREPARATION) {
					throw new InvalidStageStateException("The stage must be in the PREPARATION state to add an intermediate sprint");
				}
				//Check if the location is valid
				if (location < 0 || location > stage.getlength()) {
					throw new InvalidLocationException("The location must be between 0 and the stage length");
				}
				// If all checks are successful, add the intermediate sprint
				IntermediateSprint sprint = new IntermediateSprint(location);
				stage.addIntermediateSprint(sprint);
				return sprint.getId();
			}
		}
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
	}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Races race : racesList) {
			for (Stages stage : race.getStages()) {
				Checkpoint[] checkpoints = stage.getCheckpoints();
				for (int i = 0; i < checkpoints.length; i++) {
					if (checkpoints[i].getCheckpointId() == checkpointId) {
						//Check if the stage is in a state that allows for a checkpoint to be removed
						if (stage.getState() != StageState.PREPARATION) {
							throw new InvalidStageStateException("The stage must be in the PREPARATION state to remove a checkpoint");
						}
						// Create new array, without checkpoint to be removed
						Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length - 1];
						for (int j = 0, k = 0; j < checkpoints.length; j++) {
							if (j != i) continue;
								newCheckpoints[k++] = checkpoints[j];
						}
						stage.setCheckpoints(newCheckpoints);
						return;
						
					}
				}
			}
		}

		
		throw new IDNotRecognisedException("No checkpoint with ID" + checkpointId + "was found");
	}

	


	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		boolean stageFound = false;
		for (Races race : racesList) {
			for (Stages stage : race.getStages()) {
				if (stage.getId() == stageId) {
					//Check if the stage is in the PREPARATION state
					if (stage.getState() != StageState.PREPARATION) {
						throw new InvalidStageStateException("The stage must be in the PREPARATION state to conclude stage preparation");
					}
					//Change the state of the stage to ONGOING
					stage.setState(StageState.ONGOING);
					stageFound = true;
					break;
				}
			}
			if (stageFound) {
				break;
			}
		}
		if (!stageFound) {
			throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
		}

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// Iterate through the racesList for the stage with the given ID, if found return the checkpoint IDs
		for (Races race : racesList) {
			for (Stages stage : race.getStages()) {
				if (stage.getId() == stageId) {
					Checkpoint[] checkpoints = stage.getCheckpoints();
					int[] checkpointIds = new int[checkpoints.length];
					for (int i = 0; i < checkpoints.length; i++) {
						checkpointIds[i] = checkpoints[i].getCheckpointId();
					}
					return checkpointIds;
				}
			}
		}
		throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
	}

	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// Check if name is valid
		if (name == null || name.isEmpty() || name.length() > 50 || name.trim().isEmpty()) {
			throw new InvalidNameException("Name must not be null or empty, must not exceed character limit of 50 and characters must contain not only whitespace");
		}

		// Check if name is legal
		for (Team team : teamsArray) {
			if (team.getTeamName().equals(name)) {
				throw new IllegalNameException("A team with this name already exists");
			}
		}
		//generate ID for new team
		int id = generateteamId();
		// Create a new team and add it to the list
		Team newTeam = new Team(name, id, description);
		//Add the new team to the array
		teamsArray = Arrays.copyOf(teamsArray, teamsArray.length + 1);
		teamsArray[teamsArray.length-1] = newTeam;
		return newTeam.getId();
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	
	@Override
	public int[] getTeams() {
		int[] teamIds = new int[teamsArray.length];
		for (int i = 0; i < teamsArray.length; i++) {
			teamIds[i] = teamsArray[i].getId();
		}
		return teamIds;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		for (Team team : teamsArray) {
			if (team.getId() == teamId) {
				Rider[] riders = team.getRiders();
				int[] riderIds = new int[riders.length];
				for (int i = 0; i < riders.length; i++) {
					riderIds[i] = riders[i].getId();
				}
				return riderIds;
			}

		}
		throw new IDNotRecognisedException("No team with ID" + teamId + "was found");
		
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// Check if team exists
		Team team = null;
		for (int i = 0; i < teamsArray.length; i++) {
			if (teamsArray[i].getId() == teamID) {
				team = teamsArray[i];
				break;
			}
		}
		if (team == null) {
			throw new IDNotRecognisedException("No team with ID " + teamID + " was found");
		}
		// Check if the name is valid
		if (name == null || name.isEmpty() || name.length() > 50 || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name must not be null or empty, must not exceed character limit of 50 and characters must contain not only whitespace");
		}
		// Check if the year of birth is valid, i think you have to be at least 18 to be a professional cyclist
		// Of course you cant have someone who is really old
		if (yearOfBirth <= 1900 || yearOfBirth >= 2005) {
			throw new IllegalArgumentException("Year of birth must be between 1900 and 2005");
		}
		// Create a new Rider and add it to the team
		Rider newRider = new Rider(name, yearOfBirth, teamID);
		team.addRider(newRider);
		return newRider.getId();
	}
	

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		boolean riderfound = false;
		for (Team team : teamsArray) {
			Rider[] riders = team.getRiders();
			for (int i = 0; i < riders.length; i++) {
				if (riders[i].getId() == riderId) {
					Rider[] newRiders = new Rider[riders.length - 1];
					for (int j = 0, k = 0; j < riders.length; j++) {
						if (j == i) continue;
						newRiders[k++] = riders[j];
					}
					team.setRiders(newRiders);
					riderfound = true;
					break;
				}
			}
			if (riderfound) {
				break;
			}
		}
		if (!riderfound) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found");
		}

	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override//DONE
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {

			
			
			for (Races race : racesList) {
				boolean StageFound = false;
				for (Stages stage : race.getStages()) {
					if (stage.getId() == stageId) {
						//Setting variable to true, as stage id found
						StageFound = true;
						//Check if the stage is in a state that allows for an intermediate sprint to be added
						if (stage.getState() != StageState.PREPARATION) {
							throw new InvalidStageStateException("The stage must be in the PREPARATION state to add a new rider results");
						}

						stage.AddRidersToList(stageId, riderId, checkpoints);

					}
				}
				if (StageFound == false) {
					throw new IDNotRecognisedException("No Stage with ID " + stageId + " was found");
				}
			}
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override// DONE
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Declare variable to store the stage
		Stages foundStage = null;

		for (Stages stage : stagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;				
			}
		}
		// As if foundStage is still null, that means no stage with the given stageId was found
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get results of the rider
		LocalTime[] results = foundStage.getRiderResultsInStageMethod(stageId,riderId);
		if (results == null) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}
		return results;
	}

	@Override// DONE
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//Find stage
		Stages foundStage = null;
		for (Stages stage : stagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the results of the rider
		LocalTime RiderAdjustedElapsedTime = foundStage.getRiderAdjustedElapsedTimeInStageMethod(stageId,riderId);
		
		if (RiderAdjustedElapsedTime == null) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}
		
		return RiderAdjustedElapsedTime;
	}

	@Override// DONE
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Find stage
		Stages foundStage = null;
		for (Stages stage : stagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		// Throwing excpetion as no stage with Id of parameter stageId found
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		
		}
		// Delete the results of the rider
		boolean deleted = foundStage.DeleteRider( stageId, riderId);
		if (!deleted) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}

	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override// DONE
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// Find the stage
		Stages foundStage = null;
		for (Stages stage : stagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		int[] RankedRiderIds = foundStage.getRidersRankInStageMethod(stageId);
		if (RankedRiderIds == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RankedRiderIds;
		}
		
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override//DONE
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {

		// Finds the stage
		Stages foundStage = null;
		for (Stages stage : stagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the results of the stage
		LocalTime[] getRankedAdjustedElapsedTimes = foundStage.getRankedAdjustedElapsedTimesInStageMethod(stageId);
		// If result is null, returns an empty LocalTime array
		if (getRankedAdjustedElapsedTimes == null) {
			LocalTime[] EmptyArray = {};
			return EmptyArray;
		} else {
			return getRankedAdjustedElapsedTimes;
		}

	}


//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
//This is is points classification.

	@Override// DONE
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// Finds the stage
		Stages foundStage = null;
		for (Stages stage : stagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}

		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		
		}
		//Get results of the stage
		int[] riderPoints = foundStage.getRidersPointsInStageMethod();
		if (riderPoints == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		}else {
			return riderPoints;
		}
	}

	@Override// DONE
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// Finds the stage
		Stages foundStage = null;
		for (Stages stage : stagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}

		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		
		}
		//Get results of the stage
		int[] riderPoints = foundStage.getRidersMountainPointsInStage();
		if (riderPoints == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		}else {
			return riderPoints;
		}
	}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Override
	public void eraseCyclingPortal() {
		// Clears all data from cycling portal
		//As we are using arrays, I will just create new empty arrays and assign them to the existing arrays
		stagesArray = new Stages[0];
		ridersArray = new Rider[0];
		teamsArray = new Team[0];

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		//saves the current state of the cycling portal to a file
		//Object output stream is used to write objects to a file
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(stagesArray);
			out.writeObject(ridersArray);
			out.writeObject(teamsArray);
		}

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		//Loads the state of the cycling portal from a file
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			stagesArray = ((List<Stages>) in.readObject()).toArray(new Stages[0]);
			ridersArray = ((List<Rider>) in.readObject()).toArray(new Rider[0]);
			teamsArray = ((List<Team>) in.readObject()).toArray(new Team[0]);
		}

	}



	// The next few methods are not in minicycling portal
	//An error occurs for every single one of these before I have edited them

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// Removes a race from the races list based on a name
		Races raceToRemove = null;
		for (Races race : racesList) {
			if (race.getRacename().equals(name)) {
				raceToRemove = race;
				break;
			}
		}
		if (raceToRemove == null) {
			throw new NameNotRecognisedException("No race with name" + name + "was found");
		}
		racesList.remove(raceToRemove);

	}


//----------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override// DONE
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// Returns an array of general classification times of riders in the given race
		// Find the race
		Races foundRace = null;
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				foundRace = race;
				break;
			}
		}
		if (foundRace == null) {
			throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
		}
		// get the summed times of all stages for the race
		LocalTime[] RankedSumOfAdjustedElapasedTimes = foundRace.getGeneralClassificationTimesInRacemethod();
		// Checking if it is null then, return empty array.
		if (RankedSumOfAdjustedElapasedTimes == null) {
			LocalTime[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RankedSumOfAdjustedElapasedTimes;
		}
	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
//This is is points classification.

	@Override// DONE
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// Find the race
		Races race = null;
		for (Races r : racesList) {
			if (r.getId() == raceId) {
				race = r;
				break;
			}
		}
		if (race == null) {
			throw new IDNotRecognisedException("No race with ID" + raceId + "was found");

		}
		
		int[] RidersPointsRanked = race.getRidersPointsInRace();

		if (RidersPointsRanked == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RidersPointsRanked;
		} 

	}

	@Override//DONE
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// Find the race
		Races race = null;
		for (Races r : racesList) {
			if (r.getId() == raceId) {
				race = r;
				break;
			}
		}
		if (race == null) {
			throw new IDNotRecognisedException("No race with ID" + raceId + "was found");

		}
		
		int[] RidersPointsRanked = race.getRidersMountainPointsInRace();

		if (RidersPointsRanked == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RidersPointsRanked;
		}
	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=


	@Override// DONE
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// find the race
		Races foundRace = null;
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				foundRace = race;
				break;
			}
		}
		if (foundRace == null) {
			throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
		}
		// get the ranked Id's based off of their sum of adjusted elapsed times in all stages
		int[] RankedRiderIds = foundRace.getRidersGeneralClassificationRankMethod();
		// Checking if it is null then, return empty array.
		if (RankedRiderIds == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RankedRiderIds;
		}

	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
//This is is points classification.

	@Override// DONE
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// Find the race
		Races foundRace = null;
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				foundRace = race;
				break;
			}
		}
		if (foundRace == null) {
			throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
		}
		
		int[] RankedListOfIds = foundRace.getRidersPointClassificationRank();
		if (RankedListOfIds == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RankedListOfIds;
		} 

	}

	@Override//DONE
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// Find the race
		Races foundRace = null;
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				foundRace = race;
				break;
			}
		}
		if (foundRace == null) {
			throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
		}
		
		int[] RankedListOfIds = foundRace.getRidersMountainPointClassificationRank();
		if (RankedListOfIds == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RankedListOfIds;
		}
	}

}
