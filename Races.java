
package cycling;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.ArrayList;

public class Races {
    private Team[] teamsArray;
    private String racename;
    private Stages[] stagesArray;
    private int id;
    private String description;

    public Races(String name, String description) {
        this.racename = name;
        this.description = description;
        
    }


    public Team[] getTeams() {
        return teamsArray;
    }

    public void setTeams(Team[] teamsArray) {
        this.teamsArray = teamsArray;
    }

    public String getRacename() {
        return racename;
    }

    public void setRacename(String racename) {
        this.racename = racename;
    }

    public Stages[] getStages() {
        return stagesArray;
    }

    public void setStages(Stages[] stages) {
        this.stagesArray = stages; 
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getTotalStages() {
        if (stagesArray != null) {
            return stagesArray.length;
        } else {
            return 0;
        }
    }


    public void addTeam(Team team) {
        if (teamsArray == null) {
            teamsArray = new Team[] { team };
        } else {
            List<Team> teamList = new ArrayList<>(Arrays.asList(teamsArray));
            teamList.add(team);
            teamsArray = teamList.toArray(new Team[0]);
        }
    }

    public void removeTeam(Team team) {
        if (teamsArray != null) {
            int index = -1;
            for (int i = 0; i < teamsArray.length; i++) {
                if (teamsArray[i].equals(team)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Team[] newTeams = new Team[teamsArray.length - 1];
                for (int i = 0, j = 0; i < teamsArray.length; i++) {
                    if (i != index) {
                        newTeams[j++] = teamsArray[i];
                        
                    }
                }
                teamsArray = newTeams;
            }
        }
    }

    public void addStage(Stages stage) {
        if (this.stagesArray == null) {
            this.stagesArray = new Stages[] { stage };
        }
        else {
            List<Stages> stageList = new ArrayList<>(Arrays.asList(this.stagesArray));
            stageList.add(stage);
            this.stagesArray = stageList.toArray(new Stages[0]);
        }
    }

    public void removeStage(Stages stage) {
        if (this.stagesArray != null) {
            int index = -1;
            for (int i = 0; i < this.stagesArray.length; i++) {
                if (this.stagesArray[i].equals(stage)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Stages[] newStages = new Stages[this.stagesArray.length - 1];
                for (int i = 0, j = 0; i < this.stagesArray.length; i++) {
                    if (i != index) {
                        newStages[j++] = this.stagesArray[i];
                        j++;
                    }
                }
                this.stagesArray = newStages;
            }
        }
    }

    public String getTeamName(int teamid) {
        if (teamsArray != null) {
            for (Team team : teamsArray) {
                if (team.getId() == teamid) {
                    return team.getTeamName();
                }
            }
        }
        return null;
    }
//--------------
    public List<List<Result>> getRaceResults() {
        List<List<Result>> raceResults = new ArrayList<>();
        if (this.stagesArray != null) {
            for (Stages stage : stagesArray) {
                raceResults.add(stage.getResults());
            }
        }
        return raceResults;
    }
    
    public List<Map<Integer,Integer>> GetAllStagesPoints() {
        // Iterating thorugh all the stages, putting all the results into one big List.
        // Create a new list to store all sublists
        List<Map<Integer,Integer>> allStageMaps = new ArrayList<>();
        
        // Iterate through the array of Stages objects
        for (Stages stage : stagesArray) {
            // Assigning the points to the riders.
            stage.AssignPointsToRiders();
            // Access the attribute containing the list of lists
            Map<Integer, Integer> StageMap = stage.getRidersPoints();
            allStageMaps.add(StageMap);
            

        }

        return allStageMaps;
    }

    public List<Map<Integer,Integer>> GetAllStagesMountainPoints() {
        // Iterating thorugh all the stages, putting all the results into one big List.
        // Create a new list to store all sublists
        List<Map<Integer,Integer>> allStageMaps = new ArrayList<>();
        
        // Iterate through the array of Stages objects
        for (Stages stage : stagesArray) {
            // Assigning the points to the riders.
            stage.AssignMountainPointsToRiders();
            // Access the attribute containing the list of lists
            Map<Integer, Integer> StageMap = stage.getRidersPointsMountain();
            allStageMaps.add(StageMap);
            
        }

        return allStageMaps;
    }

    public List<List<Object>> GetAllStagesData() {
        // Iterating thorugh all the stages, putting all the results into one big List.
        // Create a new list to store all sublists
        List<List<Object>> allStageSublists = new ArrayList<>();

        // Iterate through the array of Stages objects
        for (Stages stage : stagesArray) {
            // Access the attribute containing the list of lists
            List<List<Object>> StageData = stage.getResultsList();
            
            // Iterating through the sublists of the Stage
            for (List<Object> StageSublists : StageData) {
                // Adding sublists to main data List
                allStageSublists.add(StageSublists);
            }
        }

        return allStageSublists;
    }

    public LocalTime[] getGeneralClassificationTimesInRacemethod() {
        // Creating a List of all the sublists from each stage
        List<List<Object>> RankingList = GetAllStagesData();

        Map<Integer, LocalTime> racerTotalTime = new HashMap<>();
        
        // Calculate total time for each racer
        for (List<Object> data : RankingList) {
            int racerID = (int) data.get(1);
            LocalTime Time = (LocalTime) data.get(4);
            racerTotalTime.put(racerID, racerTotalTime.getOrDefault(racerID, LocalTime.of(0, 0,0)).plusSeconds(Time.toSecondOfDay()));
        }
        
        List<LocalTime> ListOfElapsedTimesSummed = new ArrayList<>();

        // Add all values from the dictionary to the ArrayList
        for (Map.Entry<Integer, LocalTime> entry : racerTotalTime.entrySet()) {
            ListOfElapsedTimesSummed.add(entry.getValue());
        }

        Collections.sort(ListOfElapsedTimesSummed);

        // Converting the list to int array.
        LocalTime[] SortedTimesArray = new LocalTime[ListOfElapsedTimesSummed.size()];
        
        for (int i = 0; i < ListOfElapsedTimesSummed.size(); i++) {
            SortedTimesArray[i] = ListOfElapsedTimesSummed.get(i);
        }

        if (SortedTimesArray.length == 0) {
            return null;
        } else {
            return SortedTimesArray;
        }
    } 

    public int[] getRidersGeneralClassificationRankMethod() {
        // Creating a List of all the sublists from each stage
        List<List<Object>> RankingList = GetAllStagesData();

        Map<Integer, LocalTime> racerTotalTime = new HashMap<>();
        
        // Calculate total time for each racer
        for (List<Object> data : RankingList) {
            int racerID = (int) data.get(1);
            LocalTime time = (LocalTime) data.get(4);
            racerTotalTime.put(racerID, racerTotalTime.getOrDefault(racerID, LocalTime.of(0, 0)).plusSeconds(time.toSecondOfDay()));
        }
        
        // Sort racers based on total time
        List<Integer> sortedRacerIDs = new ArrayList<>(racerTotalTime.keySet());
        sortedRacerIDs.sort(Comparator.comparing(racerTotalTime::get));

        // Converting the list to int array.
        int[] SortedRiderIdArray = new int[sortedRacerIDs.size()];
        
        for (int i = 0; i < sortedRacerIDs.size(); i++) {
            SortedRiderIdArray[i] = sortedRacerIDs.get(i);
        }

        if (SortedRiderIdArray.length == 0) {
            return null;
        } else {
            return SortedRiderIdArray;
        }
        
    }
    
    public int[] getRidersPointsInRace() {
        // Creating a List of all the Maps of points, from each stage
        List<Map<Integer,Integer>> AllPointsToRiders = GetAllStagesPoints();

        if (AllPointsToRiders.size() == 0){
            return null;
        }

        // Getting the rank of the riders, sorted by total adjusted elapsed time
        int[] RankOfRiders = getRidersGeneralClassificationRankMethod();

        //List For storing the ranked points for riders.
        List<Integer> ListOfPointsRanked = new ArrayList<>();

        for (int RiderId: RankOfRiders) {
            int SumOfPoints = 0;
            // Iterating through the maps inthe list, to gather all the points for that rider
            for (Map<Integer,Integer> SublistMap : AllPointsToRiders) {
                for (Map.Entry<Integer, Integer> entry : SublistMap.entrySet()) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue(); 
                    if (key == RiderId) {
                        SumOfPoints += value; 
                    }
                }
            }
            ListOfPointsRanked.add(SumOfPoints);
        }

        // Converting the list to int array.
        int[] PointsArray = new int[ListOfPointsRanked.size()];
        
        for (int i = 0; i < ListOfPointsRanked.size(); i++) {
            PointsArray[i] = ListOfPointsRanked.get(i);
        }         
        
        return PointsArray; 

    }

    public int[] getRidersPointClassificationRank() {
        // Creating a List of all the Maps of points, from each stage
        List<Map<Integer,Integer>> AllPointsToRiders = GetAllStagesPoints();

        if (AllPointsToRiders.size() == 0){
            return null;
        }

        // Getting the rank of the riders, sorted by total adjusted elapsed time
        int[] RankOfRiders = getRidersGeneralClassificationRankMethod();

        //Map for storing rider Ids to points
        Map<Integer,Integer> MapOfAllSumOfPoints = new HashMap<Integer, Integer>();

        for (int RiderId: RankOfRiders) {
            int SumOfPoints = 0;
            // Iterating through the maps inthe list, to gather all the points for that rider
            for (Map<Integer,Integer> SublistMap : AllPointsToRiders) {
                for (Map.Entry<Integer, Integer> entry : SublistMap.entrySet()) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue(); 
                    if (key == RiderId) {
                        SumOfPoints += value; 
                    }
                }
            }
            MapOfAllSumOfPoints.put(RiderId, SumOfPoints);
        }

        // Sort the entries based on the values
        List<Map.Entry<Integer, Integer>> MapSortedPoints = new ArrayList<>(MapOfAllSumOfPoints.entrySet());
        MapSortedPoints.sort(Map.Entry.comparingByValue());

        // Create a list of keys in the new order
        List<Integer> ListOfIdsSortedOfPoints = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : MapSortedPoints) {
            ListOfIdsSortedOfPoints.add(entry.getKey());
        }
        //reversing the list, so that the rider with most points is now first
        Collections.reverse(ListOfIdsSortedOfPoints);
        // Converting the list to int array.
        int[] ArrayOfIdsSortedOfPoints = new int[ListOfIdsSortedOfPoints.size()];
        
        for (int i = 0; i < ListOfIdsSortedOfPoints.size(); i++) {
            ArrayOfIdsSortedOfPoints[i] = ListOfIdsSortedOfPoints.get(i);
        }         
        
        return ArrayOfIdsSortedOfPoints;

    }

    public int[] getRidersMountainPointsInRace() {
        // Creating a List of all the Maps of points, from each stage
        List<Map<Integer,Integer>> AllMountainPointsToRiders = GetAllStagesMountainPoints();

        if (AllMountainPointsToRiders.size() == 0){
            return null;
        }

        // Getting the rank of the riders, sorted by total adjusted elapsed time
        int[] RankOfRiders = getRidersGeneralClassificationRankMethod();

        //List For storing the ranked points for riders.
        List<Integer> ListOfMountainPointsRanked = new ArrayList<>();

        for (int RiderId: RankOfRiders) {
            int SumOfPoints = 0;
            // Iterating through the maps inthe list, to gather all the points for that rider
            for (Map<Integer,Integer> SublistMap : AllMountainPointsToRiders) {
                for (Map.Entry<Integer, Integer> entry : SublistMap.entrySet()) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue(); 
                    if (key == RiderId) {
                        SumOfPoints += value; 
                    }
                }
            }
            ListOfMountainPointsRanked.add(SumOfPoints);
        }

        // Converting the list to int array.
        int[] PointsArray = new int[ListOfMountainPointsRanked.size()];
        
        for (int i = 0; i < ListOfMountainPointsRanked.size(); i++) {
            PointsArray[i] = ListOfMountainPointsRanked.get(i);
        }         
        
        return PointsArray; 

    }
    
    public int[] getRidersMountainPointClassificationRank() {
        // Creating a List of all the Maps of points, from each stage
        List<Map<Integer,Integer>> AllPointsToRiders = GetAllStagesMountainPoints();

        if (AllPointsToRiders.size() == 0){
            return null;
        }

        // Getting the rank of the riders, sorted by total adjusted elapsed time
        int[] RankOfRiders = getRidersGeneralClassificationRankMethod();

        //Map for storing rider Ids to points
        Map<Integer,Integer> MapOfAllSumOfMountainPoints = new HashMap<Integer, Integer>();

        for (int RiderId: RankOfRiders) {
            int SumOfMountainPoints = 0;
            // Iterating through the maps inthe list, to gather all the points for that rider
            for (Map<Integer,Integer> SublistMap : AllPointsToRiders) {
                for (Map.Entry<Integer, Integer> entry : SublistMap.entrySet()) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue(); 
                    if (key == RiderId) {
                        SumOfMountainPoints += value; 
                    }
                }
            }
            MapOfAllSumOfMountainPoints.put(RiderId, SumOfMountainPoints);
        }

        // Sort the entries based on the values
        List<Map.Entry<Integer, Integer>> MapSortedPoints = new ArrayList<>(MapOfAllSumOfMountainPoints.entrySet());
        MapSortedPoints.sort(Map.Entry.comparingByValue());

        // Create a list of keys in the new order
        List<Integer> ListOfIdsSortedOfMountainPoints = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : MapSortedPoints) {
            ListOfIdsSortedOfMountainPoints.add(entry.getKey());
        }
        //reversing the list, so that the rider with most points is now first

        Collections.reverse(ListOfIdsSortedOfMountainPoints);

        // Converting the list to int array.
        int[] ArrayOfIdsSortedOfMountainPoints = new int[ListOfIdsSortedOfMountainPoints.size()];
        
        for (int i = 0; i < ListOfIdsSortedOfMountainPoints.size(); i++) {
            ArrayOfIdsSortedOfMountainPoints[i] = ListOfIdsSortedOfMountainPoints.get(i);
        }         
        
        return ArrayOfIdsSortedOfMountainPoints;

    }
    
    
    // public List<List<Object>> registerRiderResultsInStage(int stageId, int riderId, LocalTime[] checkpoints) {

    //     List<List<Object>> RankingList = generalClassification.GetRanking();

    //     RankingList = generalClassification.AddRidersToList(RankingList, stageId, riderId, checkpoints);

    //     return RankingList;

    // }

    

//--------------
//Sum of riders times



//Calculate the sum of the points of the riders at the end of the race for points classification
//Im going to assume I want to sum all of the riders points up and store them in an array that is unsorted

}