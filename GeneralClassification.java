package cycling;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class GeneralClassification {

    private List<List<Object>> Ranking;
    //private List<Object> lowestTimeList;

    public GeneralClassification(List<List<Object>> Ranking) {
        this.Ranking = AddElapsedTime(Ranking);
        this.Ranking = GetAdjustedElapsedList(this.Ranking);
        //this.Ranking = Ranking;
        

    }

    public List<List<Object>> GetRanking() {
        return Ranking;
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


    public void PrintOut(List<List<Object>> RankingList){
        for (List<Object> innerList : RankingList) {
            for (Object obj : innerList) {
                if (obj instanceof LocalTime[]) {
                    LocalTime[] times = (LocalTime[]) obj;
                    
                    for (LocalTime time : times) {
                        System.out.print(time + " ");
                    }
                    System.out.println();
                } else {
                    System.out.print(obj + " ");
                }
            }
            System.out.println();
        }
    }

    

    // Method to find the maximum LocalTime in an array of LocalTime objects
    public static LocalTime findMaxLocalTime(LocalTime[] times) {
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
                
                // Converts LocalTime to the nano seconds
                
                long firstValSec = firstVal.toNanoOfDay();
                long secondValSec = secondVal.toNanoOfDay();

                // comparing times, by removing second from one, so we can see if within one second
                if (((firstValSec - secondValSec) < 1000000000) && ((firstValSec - secondValSec) != 0)) {
                    reversedDictionary.put(firstKey, reversedDictionary.get(secondKey));
                    CompleteRecursion = true;
                } else if (((secondValSec - firstValSec) < 1000000000 ) && ((secondValSec -firstValSec ) != 0)) {
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

    public LocalTime getRiderAdjustedElapsedTimeInStageMethod(List<List<Object>> Ranking, int stageId, int riderId) {
        //Calls the function to add adjusted time to List

        // Iterates through the list to see if the stageId and RankId match, If so, returns the Elapsed time.
        for (List<Object> innerList : Ranking) {
            if ((innerList.get(0).equals(stageId)) && (innerList.get(1).equals(riderId))) {
                LocalTime AdjustedElapsedTime = (LocalTime) innerList.get(4);
                return AdjustedElapsedTime;
            }
        }
        return null;
    }

    public List<List<Object>> DeleteRider(List<List<Object>> Ranking, int stageId, int riderId) {
        // Making a copy of rider that is mutable, so it can be changed.
        List<List<Object>> RankingCopy = new ArrayList<>();

        for (List<Object> innerList : Ranking) {
            List<Object> copiedInnerList = new ArrayList<>(innerList);
            RankingCopy.add(copiedInnerList);
        }
        
        // Iterating through the Copy of Ranking to see if the stageid and riderid match.
        // If so deletes that sublist.
        int CounterForDeletionIndex = 0;
        for (List<Object> innerList : RankingCopy) { 
            if ((innerList.get(0).equals(stageId)) && (innerList.get(1).equals(riderId))) {
                RankingCopy.remove(CounterForDeletionIndex);
                return RankingCopy;
            }
            CounterForDeletionIndex++;
        }
        return RankingCopy;
    }
        
    public int[] getRidersRankInStageMethod(List<List<Object>> Ranking, int stageId) {

        // Sorting the Ranking List by the elapsed time.

        Collections.sort(Ranking, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> sublist1, List<Object> sublist2) {
                LocalTime maxTime1 = (LocalTime) sublist1.get(3);
                LocalTime maxTime2 = (LocalTime) sublist2.get(3);
                return maxTime1.compareTo(maxTime2);
            }
        });

        // Iterate through the Ranking list, see if the stage id is correct, if so add the rider id
        //to the new list

        List<Integer> resultList = new ArrayList<>();
        for (List<Object> sublist : Ranking) {
            int CurrentStageId = (int) sublist.get(0);

            if (CurrentStageId == stageId) {
                int CurrentRiderId = (int) sublist.get(1);
                resultList.add(CurrentRiderId);
            }
        }

        // Converting the list to int array.
        int[] RiderIdArray = new int[resultList.size()];
        
        for (int i = 0; i < resultList.size(); i++) {
            RiderIdArray[i] = resultList.get(i);
        }

        return RiderIdArray;
    }

    public LocalTime[] getRankedAdjustedElapsedTimesInStageMethod(List<List<Object>> RankingList2, int stageId) {//NameCHange??
        // Make a copy so that is mutable, so objects can be replaced.
        List<List<Object>> RankingCopySortedByFinishTime = new ArrayList<>();

        for (List<Object> innerList : RankingList2) {
            List<Object> copiedInnerList = new ArrayList<>(innerList);
            RankingCopySortedByFinishTime.add(copiedInnerList);
        }

        // Sort the sublists based off of the Final Time. 
        RankingCopySortedByFinishTime = SortListbasedOfFinalTime(RankingCopySortedByFinishTime);

        List<LocalTime> resultList = new ArrayList<>();
        for (List<Object> sublist : RankingCopySortedByFinishTime) {
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

        return RiderAdjustedElapsedTimes;

    }

    public int[] getRidersGeneralClassificationRankMethod(List<List<Object>> RankingList3) {
        
        Map<Integer, LocalTime> racerTotalTime = new HashMap<>();
        
        // Calculate total time for each racer
        for (List<Object> data : RankingList3) {
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

        return SortedRiderIdArray;
        
    }

    public LocalTime[] getGeneralClassificationTimesInRacemethod(List<List<Object>> RankingList4) {
                
        Map<Integer, LocalTime> racerTotalTime = new HashMap<>();
        
        // Calculate total time for each racer
        for (List<Object> data : RankingList4) {
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

        return SortedTimesArray;
    }

    public LocalTime[] getRiderResultsInStageMethod(List<List<Object>> Ranking,int stageId, int riderId) {
        List<LocalTime> RiderResultsList = new ArrayList<>();

        boolean NotInList = false;
        for (List<Object> innerList : Ranking) { 
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
            // Returning null
            return null;
        }

    }

    public List<List<Object>> AddRidersToList(List<List<Object>> Ranking,int stageId, int riderId, LocalTime[] checkpoints, String TypeOfStage) {

        List<Object> NewEntry = List.of(stageId, riderId, checkpoints);

        List<List<Object>> RankingCopyAdjustedTimesRemoved = new ArrayList<>();

        for (List<Object> SubList : Ranking) {
            SubList.remove(4);
            SubList.remove(3);
            RankingCopyAdjustedTimesRemoved.add(SubList);
        }

        RankingCopyAdjustedTimesRemoved.add(NewEntry);




        RankingCopyAdjustedTimesRemoved = AddElapsedTime(RankingCopyAdjustedTimesRemoved);
        
        RankingCopyAdjustedTimesRemoved = GetAdjustedElapsedList(RankingCopyAdjustedTimesRemoved);

        if (TypeOfStage == "TT") {
            for (List<Object> SubList : RankingCopyAdjustedTimesRemoved) {
                int CurrentRiderId = (int) SubList.get(1);
                if (CurrentRiderId == riderId) {
                    LocalTime ElapsedTime = (LocalTime) SubList.get(3);
                    SubList.set(4, ElapsedTime);
                }
            } 
        }

        return RankingCopyAdjustedTimesRemoved;

    }

    public static void main(String[] args) {
        // Example data
        // registerRiderResultsInStage
        LocalTime[] ltObject1 = {LocalTime.of(1, 1, 0), LocalTime.of(2, 0, 0),LocalTime.of(3, 50, 5)};
        LocalTime[] ltObject2 = {LocalTime.of(1, 1, 0), LocalTime.of(2, 5, 0),LocalTime.of(3, 50, 5,86)};
        LocalTime[] ltObject3 = {LocalTime.of(1, 1, 0), LocalTime.of(2, 10, 0),LocalTime.of(3, 50, 6,85)};
        LocalTime[] ltObject4 = {LocalTime.of(1, 1, 0), LocalTime.of(2, 10, 0),LocalTime.of(3, 50, 7)};
        LocalTime[] ltObject5 = {LocalTime.of(1, 1, 0), LocalTime.of(2, 10, 0),LocalTime.of(3, 50, 8)};


        List<List<Object>> listOfLists = List.of(
                //<[stageId,riderId,[1:05,1:07],elapsedTime]>
                List.of(1, 12, ltObject1),
                List.of(1, 15, ltObject2),
                List.of(1, 13, ltObject3),
                List.of(2, 15, ltObject4),
                List.of(2, 13, ltObject5)
        );

        List<List<Object>> BlankList = new ArrayList<>();

        GeneralClassification myObj = new GeneralClassification(listOfLists);
        
        myObj.PrintOut(myObj.Ranking);
        System.out.println();


        //call the (myObj.Ranking), it will have pre adjusted elapsed times.
        // myObj.PrintOut(myObj.Ranking);
        //myObj.Ranking = myObj.GetAdjustedElapsedList(myObj.Ranking);
        
        //Add Rider
        System.out.println();
        for (List<Object> Sublist : listOfLists) {   

            myObj.Ranking = myObj.AddRidersToList(myObj.Ranking, ( int)Sublist.get(0), (int) Sublist.get(1), (LocalTime[]) Sublist.get(2),"TT");
            break;
        }
        myObj.PrintOut(myObj.Ranking);


        //getRiderResultsInStage
        // LocalTime[] ar = (myObj.getRiderResultsInStageMethod(myObj.Ranking,1,15));
        // System.out.println(Arrays.toString(ar));

        // System.out.println();

        //getRiderAdjustedElapsedTimeInStage
        //System.out.println(myObj.getRiderAdjustedElapsedTimeInStageMethod(myObj.Ranking,2,13));
        
        // deleteRiderResultsInStage
        //myObj.Ranking = myObj.DeleteRider(myObj.Ranking, 1, 12);

        // getRidersRankInStage
        // int[] ar = (myObj.getRidersRankInStageMethod(myObj.Ranking,1));
        // System.out.println(Arrays.toString(ar));

        //getRankedAdjustedElapsedTimesInStage
        // LocalTime[] ar2 = (myObj.getRankedAdjustedElapsedTimesInStageMethod(myObj.Ranking,1));
        // System.out.println(Arrays.toString(ar2));

        //getRidersGeneralClassificationRank
        // int[] ar3 = (myObj.getRidersGeneralClassificationRankMethod(myObj.Ranking));
        // System.out.println(Arrays.toString(ar3));

        //getGeneralClassificationTimesInRace
        // LocalTime[] ar2 = (myObj.getGeneralClassificationTimesInRacemethod(myObj.Ranking));
        // System.out.println(Arrays.toString(ar2));
        
        


    }
}
