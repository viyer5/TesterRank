import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

import static javax.swing.UIManager.get;

public class TesterRank {
    ArrayList<ArrayList<String>> covMapFile;
    ArrayList<ArrayList<String>> killMapDemo;
    ArrayList<ArrayList<String>> killed;
    ArrayList<ArrayList<String>> summary;


    public TesterRank() {
        covMapFile = readCSV("covMapFile.csv");
        killMapDemo = readCSV("killMapDemo.csv");
        killed = readCSV("killed.csv");
        summary = readCSV("summary.csv");
    }

    public ArrayList<ArrayList<String>> readCSV (String filePathName) {
        ArrayList<ArrayList<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePathName));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }

    private static ArrayList<String> getRecordFromLine(String line) {
        ArrayList<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public HashMap<Integer, Integer> countCovMap() {
        HashMap<Integer, Integer> countCovMap = new HashMap<Integer, Integer>();
        ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
        temp = this.covMapFile;
        int totalNumTestcases = Integer.parseInt(temp.get(temp.size()-1).get(0));
        for (int i = 1; i <= totalNumTestcases; i++) {
            int count = 0;
            for (int j = 1; j < temp.size(); j++) {
                int key = Integer.parseInt(temp.get(j).get(0));
                if (key == i) {
                    //System.out.println("reached and key is "+i);
                    //System.out.println("count is "+count);
                    count++;
                }
            }
            //System.out.println("key = "+i+" value is "+count);
            countCovMap.put(i, count);
        }
        return countCovMap;
    }

    public HashMap<Integer, ArrayList<Integer>> countKillMap() {
        HashMap<Integer, ArrayList<Integer>> output = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
        temp = this.killMapDemo;
        int totalNumTestCases = Integer.parseInt(temp.get(temp.size()-1).get(0));
        //System.out.println(totalNumTestCases);
        //System.out.println("++++++++++===========+++++++++++++++============++++++++++++=");
        for (int i = 1; i <= totalNumTestCases; i++) {
            ArrayList<Integer> count = new ArrayList<Integer>();
            for (int j = 1; j < temp.size(); j++) {
                int key = Integer.parseInt(temp.get(j).get(0));
                if (key == i) {
                    count.add(Integer.parseInt(temp.get(j).get(1)));
                }
            }
            output.put(i, count);
        }
        for (int k = 1; k <= output.size(); k++) {
            //System.out.println("Key is "+k+ " value is "+ output.get(k));
        }
        return output;
    }

    public double overallMutantsKilled() {
        double output = 0.0;
        double MutantsKilled = Double.parseDouble((this.summary.get(1).get(2)));
        double MutantsGenerated = Double.parseDouble(this.summary.get(1).get(0));
        output = MutantsKilled/MutantsGenerated;
        return output;
    }

    public void print(ArrayList<ArrayList<String>> input) {
        for (int i = 0; i < input.size(); i++) {
            //System.out.println(input.get(i));
        }
    }

    public HashMap<Integer, Float> efficiencyMap() {
        HashMap<Integer, Float> output = new HashMap<Integer, Float>();
        HashMap<Integer, Integer> countCoveredMap = this.countCovMap();
        HashMap<Integer, ArrayList<Integer>> countKilledMap = this.countKillMap();

        for (int i = 1; i <= countCoveredMap.size(); i++) {
            /*System.out.println("size is " + countKilledMap.get(i).size() + " covered map " + countCoveredMap.get(i) );
            System.out.println("+++++++++++============+++++++++++++++++");*/
            Float val = (Float.valueOf(countKilledMap.get(i).size()))/Float.valueOf(countCoveredMap.get(i));
            output.put(i, val);
        }

//        for (int k = 1; k <= output.size(); k++) {
//            System.out.println("key is "+k+" value is "+output.get(k));
//        }
        return output;
    }

    public HashMap<Integer, Integer> NumMutantsKilled() {
        HashMap<Integer, Integer> output = new HashMap<Integer, Integer>();
        HashMap<Integer, ArrayList<Integer>> countedKillMap = this.countKillMap();
//        System.out.println(countedKillMap.size());
        for (int i = 1; i <= countedKillMap.size(); i++) {
            output.put(i, countedKillMap.get(i).size());
        }

        for (int j = 1; j <= output.size(); j++) {
            //System.out.println("key is "+j+" value is "+output.get(j));
        }
        return output;
    }

    public HashMap<Integer, Double> DistinctNess() {
        HashMap<Integer, Double> distinctNess = new HashMap<Integer, Double>();
        HashMap<Integer, ArrayList<Integer>> countedKillMap = this.countKillMap();
        for (int i = 1; i <= countedKillMap.size(); i++) {
            ArrayList<Integer> killedMutantsList = new ArrayList<Integer>();
            for (int m = 0; m < countedKillMap.get(i).size(); m++) {
                killedMutantsList.add(countedKillMap.get(i).get(m));
            }
            //killedMutantsList = countedKillMap.get(i);
            //int originalSize = killedMutantsList.size();
            for (int j = 1; j <= countedKillMap.size(); j++) {
                if (j == i) { continue; }
                else {
                    ArrayList<Integer> MutantKilledList = countedKillMap.get(j);
                    for (int k = 0; k < MutantKilledList.size(); k++) {
                        int element = MutantKilledList.get(k);
                        if (killedMutantsList.contains(element)) {
                            int index = killedMutantsList.lastIndexOf(element);
                            killedMutantsList.remove(index);
                        }
                    }
                }

            }
            double distinct = Double.valueOf(killedMutantsList.size()) / Double.valueOf(countedKillMap.get(i).size());
            distinctNess.put(i, distinct);
        }

        for (int l = 1; l <= distinctNess.size(); l++) {
//            System.out.println("key is "+l+" value is "+distinctNess.get(l));
        }

        return distinctNess;
    }

    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Double> hm)
    {
        // Create a list from elements of HashMap
        ArrayList<ArrayList<Double>> list = new ArrayList<>();
        for (int l = 1; l <= hm.size(); l++) {
            ArrayList<Double> entryList= new ArrayList<Double>();
            entryList.add((double) l);
            entryList.add(hm.get(l));
            list.add(entryList);
        }


        // Sort the list
        Collections.sort(list, new Comparator<ArrayList<Double> >() {
            public int compare(ArrayList<Double> o1,
                               ArrayList<Double> o2)
            {
                return (o1.get(1)).compareTo(o2.get(1))*(-1);
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> RankMap = new LinkedHashMap<>();
        for (int i=0; i<list.size();i++) {
            //System.out.println(i);
            //System.out.println(list.get(i).get(0));
            //System.out.println(list.get(i).get(1));
            double value = list.get(i).get(0);
            int valInInt = (int) value;

            RankMap.put(valInInt, (i+1));
        }
        return RankMap;
    }


    public HashMap<Integer, Integer> rank() {
        HashMap<Integer, Integer> rankOutput = new HashMap<Integer, Integer>();
        HashMap<Integer, Float> efficiency = this.efficiencyMap();
        HashMap<Integer, Integer> MutantsKilled = this.NumMutantsKilled();
        HashMap<Integer, Double> distinctNess = this.DistinctNess();

        HashMap<Integer, Double> temp = new HashMap<Integer, Double>();

        for (int i = 1; i <= efficiency.size(); i++) {
            int numOfMutKilled = MutantsKilled.get(i);
            float efficiencyVal = efficiency.get(i);
            double distinctNessVal = distinctNess.get(i);

            double finalValue = (0.5*numOfMutKilled) + (0.25 * efficiencyVal) + (0.25 * distinctNessVal);
            temp.put(i, finalValue);
        }
        for (int j = 1; j <= temp.size(); j++) {
            System.out.println("TestcaseNum is "+j+" score of this test case is "+temp.get(j));
        }

        HashMap<Integer, Integer> finalRanking = sortByValue(temp);

        for (int k = 1; k <= finalRanking.size(); k++) {
            System.out.println("TestcaseNum is "+k+" FinalRank is "+finalRanking.get(k));
        }
        return rankOutput;
    }

    public static void combinationUtil(ArrayList<ArrayList<Integer>> allCombinations, int arr[], int data[], int start,
                                                   int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            ArrayList<Integer> temp= new ArrayList<Integer>();
            for (int j=0; j<r; j++) {
                temp.add(data[j]);
//                System.out.print(data[j] + " ");
            }
//            System.out.println("");
//            System.out.println("Current Combination is "+temp);
            allCombinations.add(temp);

            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(allCombinations,arr, data, i+1, end, index+1, r);
        }
    }



    public static void printCombination(ArrayList<ArrayList<Integer>> allCombinations,int arr[], int n, int r)
    {
        // A temporary array to store all combination one by one
        int data[]=new int[r];

        // Print all combination using temprary array 'data[]'
        combinationUtil(allCombinations,arr, data, 0, n-1, 0, r);
    }

    public HashSet<Integer> setOfAllMutantsKilled () {
        HashSet<Integer> setOfMutantsKilled = new HashSet<Integer>();
        HashMap<Integer, ArrayList<Integer>> killMap = this.countKillMap();
        for (int i = 1; i <= killMap.size(); i++) {
            ArrayList<Integer> listofMutantsKilledByThisTC = killMap.get(i);
            for (int j = 0; j < listofMutantsKilledByThisTC.size(); j++) {
                setOfMutantsKilled.add(listofMutantsKilledByThisTC.get(j));
            }
        }
        return setOfMutantsKilled;
    }

    public ArrayList<ArrayList<Integer>> combinations() {
        ArrayList<ArrayList<String>> temp = this.covMapFile;
        int totalNumTestcases = Integer.parseInt(temp.get(temp.size()-1).get(0));
        ArrayList<Integer> ArraylistOfTestCase = new ArrayList<Integer>();
        int[] listOfTestCase= new int[totalNumTestcases];
        for (int i = 0; i < totalNumTestcases; i++) {
                listOfTestCase[i]=i+1;
            }
        //System.out.println("Reached 1");
        ArrayList<ArrayList<Integer>> AllCombinations= new ArrayList<ArrayList<Integer>>();
        for(int i=1; i<=totalNumTestcases; i++){
            printCombination(AllCombinations,listOfTestCase,totalNumTestcases,i);
        }
        //System.out.println("size of all combinations is "+AllCombinations.size());
        //System.out.println("Reached 2");
        for(ArrayList<Integer> i:AllCombinations){
            //System.out.print("Combination is ");
            for(int j:i){
                //System.out.print(j);
            }
            //System.out.println(" ");
        }

        ArrayList<ArrayList<Integer>> combinationsThatKillAllMutants = new ArrayList<ArrayList<Integer>>();


        for (int k = 0; k < AllCombinations.size(); k++) {
            ArrayList<Integer> tempo = AllCombinations.get(k);
            HashSet<Integer> setOfMutantsKilledByThisCombination = new HashSet<Integer>();

            for (int testCaseNum : tempo) {
                ArrayList<Integer> listOfMutantsKilledByThisTC = this.countKillMap().get(testCaseNum);
                for (int mutNum : listOfMutantsKilledByThisTC) {
                    setOfMutantsKilledByThisCombination.add(mutNum);
                }
            }


            if(setOfMutantsKilledByThisCombination.equals(this.setOfAllMutantsKilled())) {
                //System.out.println(combinationsThatKillAllMutants.size()+"reachedcheckpoint 3");
                for (int x : setOfMutantsKilledByThisCombination) {
                    //System.out.print(x + " ");
                }
                //System.out.println(setOfMutantsKilledByThisCombination.size());
                combinationsThatKillAllMutants.add(tempo);
                //System.out.println(combinationsThatKillAllMutants.size()+"reachedcheckpoint 4");
            }

        }
        //System.out.println(combinationsThatKillAllMutants.size());

        for (int n = 0; n < combinationsThatKillAllMutants.size(); n++) {
            ArrayList<Integer> combi = combinationsThatKillAllMutants.get(n);
            for (int x : combi) {
               // System.out.print(x + " ");
            }
           // System.out.println();
        }
        //System.out.println(this.setOfAllMutantsKilled().size());
    return combinationsThatKillAllMutants;

    }

    public ArrayList<ArrayList<Integer>> redundencyOfTestCases() {
        ArrayList<ArrayList<String>> temp = this.covMapFile;
        ArrayList<ArrayList<Integer>> redundencyMapList = new ArrayList<ArrayList<Integer>>();
        int totalNumTestcases = Integer.parseInt(temp.get(temp.size() - 1).get(0));
        ArrayList<Integer> ArraylistOfTestCase = new ArrayList<Integer>();
        int[] listOfTestCase = new int[totalNumTestcases];
        for (int i = 0; i < totalNumTestcases; i++) {
            listOfTestCase[i] = i + 1;
        }
        //System.out.println("Reached 1");
        ArrayList<ArrayList<Integer>> AllCombinations = new ArrayList<ArrayList<Integer>>();
        for (int i = 1; i <= totalNumTestcases; i++) {
            printCombination(AllCombinations, listOfTestCase, totalNumTestcases, i);
        }


        ArrayList<ArrayList<Integer>> combinationsThatKillAllMutants = new ArrayList<ArrayList<Integer>>();


        for (int k = 0; k < AllCombinations.size(); k++) {
            ArrayList<Integer> tempo = AllCombinations.get(k);
            HashSet<Integer> setOfMutantsKilledByThisCombination = new HashSet<Integer>();
            int redundency = 0;

            for (int testCaseNum : tempo) {
                ArrayList<Integer> listOfMutantsKilledByThisTC = this.countKillMap().get(testCaseNum);
                for (int mutNum : listOfMutantsKilledByThisTC) {
                    if (setOfMutantsKilledByThisCombination.contains(mutNum)) {
                        redundency++;
                    }
                    setOfMutantsKilledByThisCombination.add(mutNum);

                }
            }

            if (setOfMutantsKilledByThisCombination.equals(this.setOfAllMutantsKilled())) {
                tempo.add(redundency);
                redundencyMapList.add(tempo);
            }


        }
        for (ArrayList<Integer> i : redundencyMapList) {
            for(int j:i) {
                //System.out.print(j);
            }
            //System.out.println("*****");

        }
        return redundencyMapList;
    }

    public ArrayList<Integer> leastRedundentTestCasesToKillAllMutants(){
        ArrayList<ArrayList<Integer>> redundentMapList=this.redundencyOfTestCases();
        int large=Integer.MAX_VALUE;
        int largeIndex=0;
        ArrayList<Integer> leastRedundentTestCasesToKillAllMutantsCombination= new ArrayList<Integer>();
        for(int i=0;i<redundentMapList.size();i++) {
            if (redundentMapList.get(i).get(redundentMapList.get(i).size() - 1) < large) {
//                System.out.println(redundentMapList.get(i).get(redundentMapList.get(i).size() - 1));
                largeIndex = i;
                large=redundentMapList.get(i).get(redundentMapList.get(i).size() - 1);
//                System.out.println(largeIndex);
            }
        }
        for(int j=0; j<redundentMapList.get(largeIndex).size()-1;j++){
        leastRedundentTestCasesToKillAllMutantsCombination.add(redundentMapList.get(largeIndex).get(j));
        }
        for(int i:leastRedundentTestCasesToKillAllMutantsCombination){
            System.out.print(i + " ");

        }

        return leastRedundentTestCasesToKillAllMutantsCombination;
    }

    public ArrayList<Integer> leastNumOfTestCasesToKillAllMutants() {
        ArrayList<ArrayList<Integer>> allCombinations = this.combinations();
        int small = 0;
        int i = 0;
        for (i = 1; i < allCombinations.size(); i++) {
            int smallestSize = allCombinations.get(small).size();
            int currentSize = allCombinations.get(i).size();
            if (currentSize < smallestSize) {
                small = i;
            }
        }
        for (int j = 0; j < allCombinations.get(small).size(); j++) {
            System.out.print(allCombinations.get(small).get(j) + " ");
        }
        return allCombinations.get(small);
    }

   /* public ArrayList<Integer> leastRedundantTestCasesToKillAllMutants() {
        ArrayList<ArrayList<Integer>> allCombinations = this.combinations();
    }*/


    public static void main (String args[]) {
      TesterRank tr = new TesterRank();
      //tr.print(tr.killed);
      //tr.print(tr.killMapDemo);
      //tr.countKillMap();
      //System.out.println("++++++++++++++++================+++++++++++++++++==============");
      //tr.countCovMap();
      //System.out.println("++++++++++++++==============+++++++++++++++++=============+++++++++++++++");
      //tr.print(tr.summary);
      //System.out.println(tr.overallMutantsKilled());
      //tr.efficiencyMap();
      //tr.NumMutantsKilled();
      //tr.DistinctNess();
      //tr.rank();
        //tr.leastNumOfTestCasesToKillAllMutants();
        //tr.combinations();
        //tr.leastRedundentTestCasesToKillAllMutants();
        //tr.redundencyOfTestCases();
        //tr.leastRedundentTestCasesToKillAllMutants();
        System.out.println("Ranking is as follows");
        tr.rank();
        System.out.println("++++++++++============+++++++++++============++++++++++++==========");
        System.out.println("The combination with the least number of test cases to kill all mutants is ");
        tr.leastNumOfTestCasesToKillAllMutants();
        System.out.println();
        System.out.println("+++++++++++++++==============+++++++++++++++=============++++++++++++++++");
        System.out.println("The combination with the least number of redundant test cases to kill all mutants is ");
        tr.combinations();
        tr.leastRedundentTestCasesToKillAllMutants();
        tr.redundencyOfTestCases();
        System.out.println();
    }

}
