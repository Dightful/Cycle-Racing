/** 
This class is used to represent the stages of the race, with the total distance required to be gained by each one
*/

package cycling;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Stages {
    private StageType stageType; //
    private double length;
    private Checkpoint[] checkpoints;
    private int id;
    private LocalDateTime startTime;
    private String name;
    private StageState state;
    private CheckpointType checkpointtype;
    private List<List<Object>> ResultsList = new ArrayList<>();
    private Map<Integer, Integer> RidersPoints = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> RidersPointsMountain = new HashMap<Integer, Integer>();


    public Stages(String name, String description, double length, LocalDateTime startTime, StageType stageType) {
        this.stageType = stageType;
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
        return stageType;
    }

    public void setStageType(StageType stageType) {
        this.stageType = stageType;
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
    public List<List<Object>> getResultsList() {
        return ResultsList;
    }
    public void setResultsList(List<List<Object>> NewResultsList) {
        this.ResultsList = NewResultsList;
    } 
    public Map<Integer, Integer> getRidersPointsMountain() {
        return RidersPointsMountain;
    }
    public void setRidersPointsMountain(Map<Integer, Integer> NewRidersPointsMountain) {
        this.RidersPointsMountain = NewRidersPointsMountain;
    } 

    public Map<Integer, Integer> getRidersPoints() {
        return RidersPoints;
    }
    public void setRidersPoints(Map<Integer, Integer> GivenListOfPoints) {
        this.RidersPoints = GivenListOfPoints;

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

    public List<List<Object>> deepCopyList(List<List<Object>> originalList) {
        // Create a new ArrayList to store the copied 
        // elements 
        List<List<Object>> copiedList = new ArrayList<>(); 
  
        // Iterate over each element in the original list 
        for (List<Object> item : originalList) { 
            // Creating a new instance of each element
            List<Object> newItem =  new ArrayList<>(item); 
            copiedList.add(newItem); 
        } 
  
        // Return the deep copied list 
        return copiedList; 
    }

    public Map<Integer, LocalTime> DealWithDict(Map<Integer, LocalTime> dictionary) { 

        // Convert the dictionary into a list of map entries
        List<Map.Entry<Integer, LocalTime>> entryList = new ArrayList<>(dictionary.entrySet());

        // Sort the list based on the item (LocalTime)
        Collections.sort(entryList, Comparator.comparing(Map.Entry::getValue));

        // Create a LinkedHashMap to preserve the order after sorting
        Map<Integer, LocalTime> sortedDictionary = new LinkedHashMap<>();
        for (Map.Entry<Integer, LocalTime> entry : entryList) {
            sortedDictionary.put(entry.getKey(), entry.getValue());
        }

        // Reverse the sorted dictionary
        Map<Integer, LocalTime> reversedDictionary = new LinkedHashMap<>();
        List<Integer> keys = new ArrayList<>(sortedDictionary.keySet());
        Collections.reverse(keys);
        for (Integer key : keys) {
            reversedDictionary.put(key, sortedDictionary.get(key));
        }

        boolean CompleteRecursion = false;

        for (int i = 0; i < reversedDictionary.size() - 1; i++) {
            try {
                // This gets the values from the array list
                LocalTime firstVal = new ArrayList<>(reversedDictionary.values()).get(i);
                LocalTime secondVal = new ArrayList<>(reversedDictionary.values()).get(i + 1);
                int firstKey = new ArrayList<>(reversedDictionary.keySet()).get(i);
                int secondKey = new ArrayList<>(reversedDictionary.keySet()).get(i + 1);
                
                // Converts LocalTime to the seconds
                long firstValSec = firstVal.toSecondOfDay();
                long secondValSec = secondVal.toSecondOfDay();

                if (firstValSec-1 == (secondValSec)) {
                    reversedDictionary.put(firstKey, reversedDictionary.get(secondKey));
                    CompleteRecursion = true;
                } else if (secondValSec-1 == (firstValSec)) {
                    reversedDictionary.put(secondKey, reversedDictionary.get(firstKey));
                    CompleteRecursion = true;

                }
            } catch (Exception e) {
                // Ignore and continue to the next iteration
            }
        }

        // Reverse the dictionary
        Map<Integer, LocalTime> ReReversedDictionary = new LinkedHashMap<>();
        List<Integer> keys2 = new ArrayList<>(reversedDictionary.keySet());
        Collections.reverse(keys2);
        for (Integer key1 : keys2) {
            ReReversedDictionary.put(key1, reversedDictionary.get(key1));
        }

        if (CompleteRecursion){
            ReReversedDictionary = DealWithDict(ReReversedDictionary);
        }

        return ReReversedDictionary;

        
    }

    public List<List<Object>> GetAdjustedFinishTime(List<List<Object>> Ranking) {
        // Make a copy so that is mutable, so objects can be replaced.
        List<List<Object>> RankingCopy = new ArrayList<>();

        for (List<Object> innerList : Ranking) {
            List<Object> copiedInnerList = new ArrayList<>(innerList);
            RankingCopy.add(copiedInnerList);
        }



        Map<Integer, LocalTime> RankingDict = new HashMap<>();

        for (int i = 0; i < RankingCopy.size(); i++) {
            List<Object> sublist = Ranking.get(i);
            LocalTime[] obj = (LocalTime[]) sublist.get(2);
            LocalTime FinishTime = obj[obj.length - 1];
            
            RankingDict.put(i, FinishTime);
            
        }

        RankingDict = DealWithDict(RankingDict);

        int CounterForCheckingIfIndexIsCorrect = 0;

        for (List<Object> innerList : RankingCopy) { 

            LocalTime[] TimesAsArray = (LocalTime[]) innerList.get(2);

            List<LocalTime> CopyOfTimes = new ArrayList<>();

            for (int i = 0; i < TimesAsArray.length; i++) {
                CopyOfTimes.add(TimesAsArray[i]);
            }

            for ( int key : RankingDict.keySet() ) {
                if (key == CounterForCheckingIfIndexIsCorrect) {
                    LocalTime AdjustedTimeFromDict = RankingDict.get(key);
                    CopyOfTimes.add(AdjustedTimeFromDict);
                }
            }

            // Converting the list to LocalTime array.
            LocalTime[] CopyOfTimesArray = new LocalTime[CopyOfTimes.size()];
            
            for (int i = 0; i < CopyOfTimes.size(); i++) {
                CopyOfTimesArray[i] = CopyOfTimes.get(i);
            }

            innerList.set(2, CopyOfTimesArray);
            RankingCopy.set(CounterForCheckingIfIndexIsCorrect, innerList);

            CounterForCheckingIfIndexIsCorrect++;

        }

        return RankingCopy;

    }

    private List<List<Object>> AddElapsedTime(List<List<Object>> OriginalRanking){

        List<List<Object>> RankingCopy1 = new ArrayList<>();

        for (List<Object> innerList : OriginalRanking) {
            List<Object> copiedInnerList = new ArrayList<>(innerList);
            RankingCopy1.add(copiedInnerList);
        }


        int CounterForIndexInRanking = 0;
        for (List<Object> RankingSublist : RankingCopy1) {
            // Get the Array of times for the checkpoints
            LocalTime[] ArrayOfLocalTimes = (LocalTime[]) RankingSublist.get(2);
            // Getting start and finish times
            LocalTime StartTime = (LocalTime) ArrayOfLocalTimes[0];
            int ArrayOfLocalTimeslength = ArrayOfLocalTimes.length;
            LocalTime FinishTime = (LocalTime) ArrayOfLocalTimes[ArrayOfLocalTimeslength-1];
            
            
            // Find the difference in seconds between the two LocalDateTime objects
            long secondsDifference = ChronoUnit.SECONDS.between(StartTime, FinishTime);

            // Convert the difference in seconds to a LocalTime object
            LocalTime timeDifference = LocalTime.ofSecondOfDay(secondsDifference);

            RankingSublist.add(timeDifference);
            RankingCopy1.set(CounterForIndexInRanking,RankingSublist);
            CounterForIndexInRanking ++;
        }

        return RankingCopy1;
    }

    public List<List<Object>> GetAdjustedElapsedList(List<List<Object>> RankingChangedName) {

        List<List<Object>> RankingDeepCopy = deepCopyList(RankingChangedName);

        List<List<List<Object>>> ContainerForStageIdLists = new ArrayList<>();

        // Adding all the stage ids to a list to iterate through
        List<Integer> ListOfStageIDs = new ArrayList<>();
        for (List<Object> Sublist : RankingDeepCopy) {
            int StageId = (int) Sublist.get(0);
            if (ListOfStageIDs.contains(StageId) == false) {
                ListOfStageIDs.add(StageId);
            }
        }

        // Iterating through StageId and RankingCopy, if stage Id equal.
        //if so that sublist is appended to that StageId list.
        for (int StageId : ListOfStageIDs) {
            List<List<Object>> StageIdLists = new ArrayList<>();
            for (List<Object> Sublist : RankingDeepCopy) {
                int CurrentStageId = (int) Sublist.get(0);
                if (CurrentStageId == StageId)
                StageIdLists.add(Sublist);
            }
            ContainerForStageIdLists.add(StageIdLists);

        }       

        List<List<List<Object>>> ContainerForAdjustedStageIdLists = new ArrayList<>();

        for (List<List<Object>> RankingCopySortedStageId : ContainerForStageIdLists) {
            RankingCopySortedStageId = GetAdjustedFinishTime(RankingCopySortedStageId);

            ContainerForAdjustedStageIdLists.add(RankingCopySortedStageId);
        }

        List<List<Object>> RankingAdjustedTimes = new ArrayList<>();

        for (List<List<Object>> SubListOfSameRank : ContainerForAdjustedStageIdLists) {
            for (List<Object> ListOfAdjustedDetails : SubListOfSameRank) {
                RankingAdjustedTimes.add(ListOfAdjustedDetails);
            }
        }


        List<List<Object>> RankingCopy3 = AddElapsedTime(RankingAdjustedTimes);



        int CounterForReferencingIndexOfSublist = 0;
        for (List<Object> InnerList : RankingCopy3) { 

            LocalTime[] TimesAsArray = (LocalTime[]) InnerList.get(2);

            List<LocalTime> CopyOfTimes = new ArrayList<>();

            for (int i = 0; i < TimesAsArray.length; i++) {
                CopyOfTimes.add(TimesAsArray[i]);
            }

            CopyOfTimes.remove(CopyOfTimes.size() - 1);

            // Converting the list to LocalTime array.
            LocalTime[] CopyOfTimesArray = new LocalTime[CopyOfTimes.size()];
            
            for (int i = 0; i < CopyOfTimes.size(); i++) {
                CopyOfTimesArray[i] = CopyOfTimes.get(i);
            }

            InnerList.set(2, CopyOfTimesArray);
            RankingCopy3.set(CounterForReferencingIndexOfSublist, InnerList);
            CounterForReferencingIndexOfSublist ++ ;

        }
        
        return RankingCopy3;
    }
    
    public List<List<Object>> AddRidersToList(int stageId, int riderId, LocalTime[] checkpointTimes) throws DuplicatedResultException, InvalidCheckpointTimesException{      
        // checking to see if invalid checkpoint times
        if (checkpointTimes.length != (checkpoints.length + 2)) {
            throw new InvalidCheckpointTimesException("Incorrect amount of checkpoint times");
        }

        //Creating a new list
        List<Object> NewEntry = List.of(stageId, riderId, checkpoints);

        List<List<Object>> ResultsListAdjustedTimesRemoved = new ArrayList<>();

        for (List<Object> SubList : ResultsList) {
            // Checking to see if the rider already has a result in the result List
            if ((int) SubList.get(1) == riderId) {
                throw new DuplicatedResultException("Rider id of " + riderId + " already found");
            }
            SubList.remove(4);
            SubList.remove(3);
            ResultsListAdjustedTimesRemoved.add(SubList);
        }


        ResultsListAdjustedTimesRemoved.add(NewEntry);

        ResultsListAdjustedTimesRemoved = AddElapsedTime(ResultsListAdjustedTimesRemoved);

        ResultsListAdjustedTimesRemoved = GetAdjustedElapsedList(ResultsListAdjustedTimesRemoved);

        return ResultsListAdjustedTimesRemoved;

    }

    public LocalTime[] getRiderResultsInStageMethod(int stageId, int riderId) {
        List<LocalTime> RiderResultsList = new ArrayList<>();

        boolean NotInList = false;
        for (List<Object> innerList : ResultsList) { 
            if ((innerList.get(0).equals(stageId)) && (innerList.get(1).equals(riderId))) {
                
                LocalTime[] RiderCheckPointTimes = (LocalTime[]) (innerList.get(2));
                int CounterForStartFinish = 0;
                for (LocalTime Time: RiderCheckPointTimes) {
                    if ((CounterForStartFinish != 0)&&(CounterForStartFinish != (RiderCheckPointTimes.length - 1))){
                        RiderResultsList.add(Time);
                    }
                    CounterForStartFinish ++ ;
                    
                }
                LocalTime ElapsedTime = (LocalTime) innerList.get(3);
                RiderResultsList.add(ElapsedTime);
                NotInList = true;
            }

        } 

        // Converting the list to LocalTime array.
        LocalTime[] RiderResultsArray = new LocalTime[RiderResultsList.size()];
        for (int i = 0; i < RiderResultsList.size(); i++) {
            RiderResultsArray[i] = RiderResultsList.get(i);
        }
        if (NotInList) {
            return RiderResultsArray;
        } else {
            // Returning null as no rider id found.
            return null;
        }

    }

    public LocalTime getRiderAdjustedElapsedTimeInStageMethod(int stageId, int riderId) {
        //Calls the function to add adjusted time to List

        // Iterates through the list to see if the stageId and RankId match, If so, returns the Elapsed time.
        for (List<Object> innerList : ResultsList) {
            if ((innerList.get(0).equals(stageId)) && (innerList.get(1).equals(riderId))) {
                LocalTime AdjustedElapsedTime = (LocalTime) innerList.get(4);
                return AdjustedElapsedTime;
            }
        }
        // As no rider id matches any in the list of riders 
        return null;
    }

    public boolean DeleteRider(int stageId, int riderId) {
        // Iterating through the Copy of Ranking to see if the stageid and riderid match.
        // If so deletes that sublist.
        boolean riderIdFound = false;
        int CounterForDeletionIndex = 0;
        for (List<Object> innerList : ResultsList) { 
            if ((innerList.get(0).equals(stageId)) && (innerList.get(1).equals(riderId))) {
                ResultsList.remove(CounterForDeletionIndex);
                riderIdFound = true;
            }
            CounterForDeletionIndex++;
        }
        return riderIdFound;
    }

    public int[] getRidersRankInStageMethod(int stageId) {

        // Sorting the ResultsList by the elapsed time.

        Collections.sort(ResultsList, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> sublist1, List<Object> sublist2) {
                LocalTime maxTime1 = (LocalTime) sublist1.get(3);
                LocalTime maxTime2 = (LocalTime) sublist2.get(3);
                return maxTime1.compareTo(maxTime2);
            }
        });

        // Iterate through the ResultsList list, ading the rider id to the new list

        List<Integer> resultList = new ArrayList<>();
        for (List<Object> sublist : ResultsList) {
            
            int CurrentRiderId = (int) sublist.get(1);
            resultList.add(CurrentRiderId);
            
        }

        // Converting the list to int array.
        int[] RiderIdArray = new int[resultList.size()];
        
        for (int i = 0; i < resultList.size(); i++) {
            RiderIdArray[i] = resultList.get(i);
        }

        if (RiderIdArray.length == 0) {
            return null;
        }else {
            return RiderIdArray;
        }

    }
    
    public static LocalTime findMaxLocalTime(LocalTime[] times) {
        //Finds the maximum LocalTime in an array of LocalTime objects        
        LocalTime maxTime = null;
        for (LocalTime time : times) {
            if (maxTime == (null) || time.isAfter(maxTime)) {
                maxTime = time;
            }
        }
        return maxTime;
    }
    
    public List<List<Object>> SortListbasedOfFinalTime(List<List<Object>> Ranking) {
        // Sort the sublists based on the maximum LocalTime in each sublist's array
        Collections.sort(Ranking, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> sublist1, List<Object> sublist2) {
                LocalTime maxTime1 = findMaxLocalTime((LocalTime[]) sublist1.get(2));
                LocalTime maxTime2 = findMaxLocalTime((LocalTime[]) sublist2.get(2));
                return maxTime1.compareTo(maxTime2);
            }
        });

        return Ranking;
    }
    
    public List<List<Object>> SortListbasedOfElapsedTime(List<List<Object>> Ranking) {
        // Sort the sublists based on the maximum LocalTime in each sublist's array
        Collections.sort(Ranking, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> sublist1, List<Object> sublist2) {
                LocalTime maxTime1 = (LocalTime) sublist1.get(3);
                LocalTime maxTime2 = (LocalTime) sublist1.get(3);
                return maxTime1.compareTo(maxTime2);
            }
        });

        return Ranking;
    }

    public LocalTime[] getRankedAdjustedElapsedTimesInStageMethod(int stageId) {
        List<List<Object>> ResultsSortedByFinishTime = new ArrayList<>();

        // Sort the sublists based off of the Final Time. 
        ResultsSortedByFinishTime = SortListbasedOfFinalTime(ResultsList);

        List<LocalTime> resultList = new ArrayList<>();
        for (List<Object> sublist : ResultsSortedByFinishTime) {
            int CurrentStageId = (int) sublist.get(0);

            if (CurrentStageId == stageId) {
                LocalTime AdjustedElapsedTime = (LocalTime) sublist.get(4);
                resultList.add(AdjustedElapsedTime);
            }
            
        }

        // Converting the list to LocalTime array.
        LocalTime[] RiderAdjustedElapsedTimes = new LocalTime[resultList.size()];
        
        for (int i = 0; i < resultList.size(); i++) {
            RiderAdjustedElapsedTimes[i] = resultList.get(i);
        }

        if (RiderAdjustedElapsedTimes.length == 0) {
            return null;
        } else {
            return RiderAdjustedElapsedTimes;
        }

        
    }
 
    public List<Integer> GetPointsForStageType() {

        List<Integer> ListOfPoints = new ArrayList<>();
        // Creates an array of the points, in order, given to 1st -> 15th place.
        if (stageType == StageType.MEDIUM_MOUNTAIN) {
            int[] ArrayOfPoints = {30,25,22,19,17,15,13,11,9,7,6,5,4,3,2};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPoints.add(point);
            }
        } else if (stageType == StageType.HIGH_MOUNTAIN) {
            int[] ArrayOfPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPoints.add(point);
            }
        } else if (stageType == StageType.FLAT) {
            int[] ArrayOfPoints = {50,30,20,18,16,14,12,10,8,7,6,5,4,3,2};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPoints.add(point);
            }
        } else if (stageType == StageType.TT) {
            int[] ArrayOfPoints = {50,30,20,18,16,14,12,10,8,7,6,5,4,3,2};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPoints.add(point);
            }
        }
        return ListOfPoints;
    }

    public void AssignPointsToRiders() {
        List<Integer> ListOfPoints = GetPointsForStageType();
        // Getting the list of riderIds for the stage
        List<Integer> SortedListOfRiderIds = new ArrayList<>();
        // Getting the Sorted Result List
        List<List<Object>> SortedResultList = SortListbasedOfElapsedTime(ResultsList);
        for (List<Object> Sublist : SortedResultList) {
            int RiderId = (int) Sublist.get(1);
            SortedListOfRiderIds.add(RiderId);
        }
        // Adds the riders ID and their given points to the dictionary
        int CounterToReferenceRiderIds = 0;
        for (int Points : ListOfPoints) {
            RidersPoints.put(SortedListOfRiderIds.get(CounterToReferenceRiderIds),Points);
            CounterToReferenceRiderIds++;
        }
        
    }
    
    public int[] getRidersPointsInStageMethod() {
        // Checking that if there are no results for that stage, then just returns null
        if (ResultsList.size() == 0) {
            return null;
        }  
        // Assigning the points to the riders.
        AssignPointsToRiders();
        
        List<Integer> RankedListOfPoints = new ArrayList<>();
        // Iterating through the Sorted By Elapsed Time Dictioanry of riders and points.
        // Creating a list of the points
        for (Map.Entry<Integer, Integer> entry : RidersPoints.entrySet()) {
            Integer value = entry.getValue(); 
            RankedListOfPoints.add(value);
        }
        // Converting the list to int array.
        int[] PointsArray = new int[RankedListOfPoints.size()];
        
        for (int i = 0; i < RankedListOfPoints.size(); i++) {
            PointsArray[i] = RankedListOfPoints.get(i);
        }
              
        return PointsArray;
         

    }

    public List<Integer> GetPointsForCheckpointTypeMountain(CheckpointType CurrentCheckpointType) {

        List<Integer> ListOfPointsMountain = new ArrayList<>();
        // Creates an array of the points, in order, given to 1st -> 15th place.
        if (CurrentCheckpointType == CheckpointType.C4) {
            int[] ArrayOfPoints = {1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        } else if (CurrentCheckpointType == CheckpointType.C3) {
            int[] ArrayOfPoints = {2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        } else if (CurrentCheckpointType == CheckpointType.C2) {
            int[] ArrayOfPoints = {5,3,2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        } else if (CurrentCheckpointType == CheckpointType.C1) {
            int[] ArrayOfPoints = {10,8,6,4,2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        }
        else if (CurrentCheckpointType == CheckpointType.HC) {
            int[] ArrayOfPoints = {20,15,12,10,8,6,4,2};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        }
        return ListOfPointsMountain;
    } 

    public List<List<Object>> SortListAtIndex(List<List<Object>> Ranking, int Index) {
        // Sort the sublists based on the maximum LocalTime in each sublist's array
        Collections.sort(Ranking, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> sublist1, List<Object> sublist2) {
                LocalTime[] Checkpoints1 = (LocalTime[]) sublist1.get(2);
                LocalTime[] Checkpoints2 = (LocalTime[]) sublist2.get(2);
                LocalTime CurrentCheckpointTime1 = (LocalTime) Checkpoints1[Index];
                LocalTime CurrentCheckpointTime2 = (LocalTime) Checkpoints2[Index];
                return CurrentCheckpointTime1.compareTo(CurrentCheckpointTime2);
            }
        });

        return Ranking;
    }
    
    public void AssignMountainPointsToRiders() {
        
        int CounterForCheckpoints = 1;
        int CounterForAssigningPoints = 0;
        for (Checkpoint CurrentCheckpoint : checkpoints) {
            CheckpointType CurrentCheckpointType = CurrentCheckpoint.getCheckpointType();
            //Getting the list of points for that checkpoint type
            List<Integer> ListOfMountainPoints = GetPointsForCheckpointTypeMountain(CurrentCheckpointType);
            
            //Sorting the List of Results based upon Checkpoint times
            List<List<Object>> SortedResultsList = SortListAtIndex(ResultsList, CounterForCheckpoints);
            
            // Adding the points to the dict, if the rider id already there it updates the points
            for (List<Object> Sublist : SortedResultsList) {
                int RiderId = (int) Sublist.get(1);
                try {
                    RidersPointsMountain.put(RiderId,RidersPointsMountain.get(RiderId) + ListOfMountainPoints.get(CounterForAssigningPoints));
                }
                catch (Exception e) {

                }
                finally {
                    RidersPointsMountain.put(RiderId,ListOfMountainPoints.get(CounterForAssigningPoints));
                }
                
            }   
         
            CounterForAssigningPoints ++;
            CounterForCheckpoints ++;

        }

    }
    
    public int[] getRidersMountainPointsInStage() {
        
        // Checking that if there are no results for that stage, then just returns null
        if (ResultsList.size() == 0) {
            return null;
        }  
        // Assigning the mountain points to the riders.
        AssignMountainPointsToRiders();

        //Sorted list of points by finish time
        List<Integer> RankedListOfPoints = new ArrayList<>();

        List<List<Object>> ResultsSortedByFinishTime = SortListbasedOfFinalTime(ResultsList);
        for (List<Object> sublist : ResultsSortedByFinishTime) {
            int riderid = (int) sublist.get(1);
            // Adding that riders points from the Dictionary to the list
            RankedListOfPoints.add(RidersPointsMountain.get(riderid));
        }
        
        
        // Converting the list to int array.
        int[] PointsArray = new int[RankedListOfPoints.size()];
        
        for (int i = 0; i < RankedListOfPoints.size(); i++) {
            PointsArray[i] = RankedListOfPoints.get(i);
        }
              
        return PointsArray;
    }


     

}

//set stage method

//for each rider
//get rider id
