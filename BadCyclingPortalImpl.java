package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//Added packages (if we are allowed)
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;

/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class BadCyclingPortalImpl implements CyclingPortal {

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
	public int addStageToRace(int raceId, String stageName, double length, LocalDateTime startTime, StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {

		// Validate stageName
		if (stageName == null || stageName.isEmpty() || stageName.length() > 50 || stageName.trim().isEmpty()) {
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
					if (stage.getStageName().equals(stageName)) {
						throw new IllegalNameException("Name is already in use");
					}
				}

				// Create a new stage and add it to the race
				Stages newStage = new Stages(stageName, length, startTime, type);
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
				List<Stages> stages = race.getStages();
				int[] stageIds = new int[stages.size()];
				for (int i = 0; i < stages.size(); i++) {
					stageIds[i] = stages.get(i).getId();
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
					return stage.getDistance();
				}
			}
		}
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		boolean stageFound = false;
		for (Races race : racesList) {
			List<Stages> stages = race.getStages();
			for (int i = 0; i < stages.size(); i++) {
				if (stages.get(i).getId() == stageId) {
					stages.remove(i);
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
				if (stage.getType() != StageType.C1) {
					throw new InvalidStageTypeException("The stage type must be MOUNTAIN to add a categorized climb");
				}
				//Check if the stage is in a state that allows for a categorized climb to be added
				if (stage.getState() != StageState.PREPARATION) {
					throw new InvalidStageStateException("The stage must be in the PREPARATION state to add a categorized climb");
				}
				//Check if the location is valid
				if (location < 0 || location > stage.getLength()) {
					throw new InvalidLocationExeption("The location must be between 0 and the stage length");
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
				if (stage.getType() != StageType.SPRINT) {
					throw new InvalidStageTypeException("The stage type must be a SPRINT to add an intermediate sprint");
				}
				//Check if the stage is in a state that allows for an intermediate sprint to be added
				if (stage.getState() != StageState.PREPARATION) {
					throw new InvalidStageStateException("The stage must be in the PREPARATION state to add an intermediate sprint");
				}
				//Check if the location is valid
				if (location < 0 || location > stage.getLength()) {
					throw new InvalidLocationExeption("The location must be between 0 and the stage length");
				}
				// If all checks are successful, add the intermediate sprint
				IntermediateSprint sprint = new IntermediateSprint(location);
				stage.addIntermediateSprint(sprint);
				return sprint.getId();
			}
		}
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
	}


	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		boolean checkpointFound = false;
		for (Races race : racesList) {
			for (Stages stage : race.getStages()) {
				List<Checkpoint> checkpoints = stage.getCheckpoints():
				for (int i = 0; i < checkpoints.size(); i++) {
					if (checkpoints.get(i).getId() == checkpointId) {
						//Check if the stage is in a state that allows for a checkpoint to be removed
						if (stage.getState() != StageState.PREPARATION) {
							throw new InvalidStageStateException("The stage must be in the PREPARATION state to remove a checkpoint");
						}
						checkpoints.remove(i);
						checkpointFound = true;
						break;
					}
				}
				if (checkpointFound) {
					break;
				}
			}
			if (checkpointFound) {
				break;
			}
		}
		if (!checkpointFound) {
			throw new IDNotRecognisedException("No checkpoint with ID" + checkpointId + "was found");
		}

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
					state.setStage(StageState.ONGOING);
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
		//Iterate throw the racesList for the stage with the given ID, if found return the checkpoint IDs
		for (Races race : racesList) {
			for (Stages stage : race.getStages()) {
				if (stage.getId() == stageId) {
					List<Checkpoint> checkpoints = stage.getCheckpoints();
					int[] checkpointIds = new int[checkpoints.size()];
					for (int i = 0; i < checkpoints.size(); i++) {
						checkpointIds[i] = checkpoints.get(i).getId();
					}
					return checkpointIds;
				}
			}
		}
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
	}

	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// Check if name is valid
		if (name == null || name.isEmpty() || name.length() > 50 || name.trim().isEmpty()) {
			throw new InvalidNameException("Name must not be null or empty, must not exceed character limit of 50 and characters must contain not only whitespace");
		}
		// Check if name is legal
		for (Team team : teamsList) {
			if (team.getName().equals(name)) {
				throw new IllegalNameException("A team with this name already exists");
			}
		}
		//generate ID for new team
		int id = generateId();
		// Create a new team and add it to the list
		Team newTeam = new Team(id, name, description);
		teamsList.add(newTeam);
		return newTeam.getId();
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	
	@Override
	public int[] getTeams() {
		int[] teamIds = new int[teamsList.size()];
		for (int i = 0; i < teamsList.size(); i++) {
			teamIds[i] = teamsList.get(i).getId();
		}
		return teamIds;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		for (Team team : teamList) {
			if (team.getId() == teamId) {
				List<Riders> riders = team.getRiders();
				int[] riderIds = new int[riders.size()];
				for (int i = 0; i < riders.size(); i++) {
					riderIds[i] = riders.get(i).getId();
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
		for (Team t : teamsList) {
			if (t.getId() == teamID) {
				team = t;
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
		if (yearOfBirth < 1900 || yearOfBirth > 2005) {
			throw new IllegalArgumentException("Year of birth must be between 1900 and 2021");
		}
		// Create a new Rider and add it to the team
		Rider newRider = new Rider(name, yearOfBirth, teamID);
		team.addRider(newRider);
		return newRider.getId();
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		boolean riderfound = false;
		for (Team team : teamsList) {
			List<Rider> riders = team.getRiders();
			for (int i = 0; i < riders.size(); i++) {
				if (riders.get(i).getId() == riderId) {
					riders.remove(i);
					riderfound = true;
					break;
				}
			}
			if (riderfound) {
				break;
			}
		}
		if (!riderfound) {
			throw new IDNotRecognisedException("No rider with ID" + riderId + "was found");
		}

	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		

	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Find stage
		Stages stage = null;
		for (Stages s : stagesList) {
			if (s.getId() == stageId) {
				stage = s;
				break;
			}
		}
		if (stage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get results of the rider
		LocalTime[] results = stage.getRiderResults(riderId);
		if (results == null) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}
		return results;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//Find stage
		Stages stage = null;
		for (Stages s : stagesList) {
			if (s.getId() == stageId) {
				stage = s;
				break;
			}
		}
		if (stage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the results of the rider
		LocalTime[] results = stage.getRiderResults(riderId);
		if (results == null) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}
		//Calculate the adjusted elapsed time
		LocalTime adjustedTime = calculateAdjustedTime(results);
		return adjustedTime;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Find stage
		Stages stage = null;
		for (Stages s : stagesList) {
			if (s.getId() == stageId) {
				stage = s;
				break;
			}
		}
		if (stage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		
		}
		// Delete the results of the rider
		boolean deleted = stage.deleteRiderResults(riderId);
		if (!deleted) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}

	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// Find the stage
		Stages stage = null;
		for (Stages s : stagesList) {
			if (s.getId() == stageId) {
				stage = s;
				break;
			}
		}
		if (stage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the results of the stage
		List<Result> results = stage.getResults();
		//Sort results by time
		Collections.sort(results, new Comparator<Result>() {
			@Override
			public int compare(Result r1, Result r2) {
				return r1.getTime().compareTo(r2.getTime());
			}
		});
		//Create an array of rider IDs
		int[] riderIds = new int[results.size()];
		for (int i = 0; i < results.size(); i++) {
			riderIds[i] = results.get(i).getRider().getId();
		}
		return riderIds;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// Returns an array of adjusted elapsed times of riders in the given stage
		// Find the stage
		Stages stage = null;
		for (Stages s : stagesList) {
			if (s.getId() == stageId) {
				stage = s;
				break;
			}
		}
		if (stage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the results of the stage
		List<Result> results = stage.getResults();
		//Sort results by adjusted elapsed time
		Collections.sort(results, new Comparator<Result>() {
			@Override
			public int compare(Result r1, Result r2) {
				return r1.getAdjustedTime().compareTo(r2.getAdjustedTime());
			}
		});
		//Create an array of adjusted elapsed times
		LocalTime[] adjustedTimes = new LocalTime[results.size()];
		for (int i = 0; i < results.size(); i++) {
			adjustedTimes[i] = results.get(i).getAdjustedTime();
		}
		return adjustedTimes;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// Return an array of points earned by riders in the given stage
		//find the stage
		Stages stage = null;
		for (Stages s : stagesList) {
			if (s.getId() == stageId) {
				stage = s;
				break;
			}
		}
		if (stage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		
		}
		//Get results of the stage
		List<Result> results = stage.getResults();
		//Create an array of rider points
		int[] riderPoints = new int[results.size()];
		for (int i = 0; i < results.size(); i++) {
			riderPoints[i] = results.get(i).getPoints();
		}
		return riderPoints;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// Return an array of mountain points earned by riders in the given stage
		//find the stage
		Stages stage = null;
		for (Stages s : stagesList) {
			if (s.getId() == stageId) {
				stage = s;
				break;
			}
		}
		if (stage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the results of the stage
		List<Result> results = stage.getResults();
		//Create an array of rider mountain points
		int[] riderMountainPoints = new int[results.size()];
		for (int i = 0; i < results.size(); i++) {
			riderMountainPoints[i] = results.get(i).getMountainPoints();
		}
	}

	@Override
	public void eraseCyclingPortal() {
		// Clears all data from cycling portal
		//Adjust accordingly
		stagesList.clear();
		ridersList.clear();
		teamsList.clear();

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		//saves the current state of the cycling portal to a file
		//Object output stream is used to write objects to a file
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(stagesList);
			out.writeObject(ridersList);
			out.writeObject(teamsList);
		}

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		//Loads the state of the cycling portal from a file
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			stagesList = (List<Stages>) in.readObject();
			ridersList = (List<Rider>) in.readObject();
			teamsList = (List<Team>) in.readObject();
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

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// Returns an array of general classification times of riders in the given race
		// Find the race
		Races race = null;
		for (Races r : racesList) {
			if (r.getId() == raceId) {
				race = r;
				break;
			}
		}
		if (race == null) {
			throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
		}
		// Get the results of the race
		List<Result> results = race.getResults();
		// Create an array of general classification times
		LocalTime[] generalClassificationTimes = new LocalTime[results.size()];
		for (int i = 0; i < results.size(); i++) {
			generalClassificationTimes[i] = results.get(i).getGeneralClassificationTime();

		}
		return generalClassificationTimes;
	}

	@Override
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
		// Get the results of the race
		List<Result> results = race.getResults();
		// Create an array of rider points
		int[] riderPoints = new int[results.size()];
		for (int i = 0; i < results.size(); i++) {
			riderPoints[i] = results.get(i).getPoints();
		}
		return riderPoints;
	}

	@Override
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
		// Get the results of the race
		List<Result> results = race.getResults();
		// Create an array of rider mountain points
		int[] riderMountainPoints = new int[results.size()];
		for (int i = 0; i < results.size(); i++) {
			riderMountainPoints[i] = results.get(i).getMountainPoints();
		}
		return riderMountainPoints;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// find the race
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
		//Get the results of the race
		List<Result> results = race.getResults();
		//Create an array of rider general classification ranks
		int[] riderGeneralClassificationRanks = new int[results.size()];
		for (int i = 0; i < results.size(); i++) {
			riderGeneralClassificationRanks[i] = results.get(i).getGeneralClassificationRank();
		}
		return riderGeneralClassificationRanks;
	}



	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
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
		// Get the results of the race
		List<Result> results = race.getResults();
		// Create an array of rider point classification ranks
		int[] riderPointClassificationRanks = new int[results.size()];
		for (int i = 0; i < results.size(); i++) {
			riderPointClassificationRanks[i] = results.get(i).getPointClassificationRank();
		}
		return riderPointClassificationRanks;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// Find the race
		Races race = null;
		for (Race r : racesList) {
			if (r.getId() == raceId) { 
				race = r;
				break;
			}
		}
		if (race == null) {
			throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
		}
		// Get the results of the race
		List<Result> results = race.getResults();
		// Create an array of rider mountain point classification ranks
		int[] riderMountainPointClassificationRanks = new int[results.size()];
		for (int i = 0; i < results.size(); i++) {
			riderMountainPointClassificationRanks[i] = results.get(i).getMountainPointClassificationRank();
		}
		return riderMountainPointClassificationRanks;
	}

}
