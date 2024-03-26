package cycling;

import java.util.Arrays;
import java.util.List;

public class MountainClassification extends PlacementClassification{
    
    
    public MountainClassification(List<List<Object>> Ranking) {
        super(Ranking);
    }

    public static void main(String[] args) {
        // Example data
        List<List<Object>> listOfLists = List.of(
                List.of("Sky", "Bob", 2),
                List.of("mclaren", "Ben", 3),
                List.of("Ferrari", "Peter", 1)
        );
        

        MountainClassification myObj = new MountainClassification(listOfLists);
        System.out.println(myObj.FirstPlaceList);
        System.out.println(myObj.GetRiderPosition("Ben"));
        System.out.println(myObj.GetNumberOneRider());
        String[] resultInStringTeam = myObj.GetResultsInStringTeam();
        System.out.println(Arrays.toString(resultInStringTeam));

    }

}
