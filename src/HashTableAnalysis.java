import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashTableAnalysis {

	private static List<Integer> accesses = new ArrayList<>();
	private static List<Float> loadFactors = new ArrayList<>();

	public static void runInsertAnalysis() {

		accesses.add(0);// for the graph to start at 0
		loadFactors.add((float) 0.0);// for the graph to start at 0
		Random random = new Random();// Generate random keys
		int numberOfTablesToTest = 10;// for test multiple tables
		int accessTotalCountForAvg = 0;// Running sum of accesses, for averaging
		int graphingPointLocations;// to index to different load factors
		int count = 0; // count of records inserted
		float currentLoadFactor;// current load factor
		ArrayList<HashDictionary<Integer, Integer>> arrayOfHDl = new ArrayList<>();// array of different HashDictionaries
		List<Float> arrayOfLoadFactors = new ArrayList<>();// to store different size load factors
		List<Integer> arrayOfAccesses = new ArrayList<>(); // to store average of accesses

		// Fill array of HD for multiple tables
		for (int i = 0; i < numberOfTablesToTest; i++) {
			arrayOfHDl.add(new HashDictionary<Integer, Integer>());
		}

		// loop through different tables and insert records and count all the accesses performed
		for (int j = 0; j < HashDictionary.getMaxSize(); j++) {
			accessTotalCountForAvg = 0;
			for (int i = 0; i < numberOfTablesToTest; i++) {
				int newRandomKey = random.nextInt((20000000 - 0) + 1);
				int newRandomRecord = random.nextInt((20000000 - 0) + 1);
				arrayOfHDl.get(i).insert(newRandomKey, newRandomRecord);
				arrayOfHDl.get(i);
				accessTotalCountForAvg += HashTable.getInsertionCount();
			}
			count++;// record count + 1
			int averageAccesses = accessTotalCountForAvg / numberOfTablesToTest;// get avg access
			currentLoadFactor = (float) count / HashDictionary.getMaxSize();// get current load factor
			arrayOfAccesses.add(averageAccesses);// insert results into array
			arrayOfLoadFactors.add(currentLoadFactor);// insert results into array

		}
		for (int i = 1; i < 10; i++) {
			graphingPointLocations = (int) (HashDictionary.getMaxSize() * (i * .1));// get graphing locations points
			accesses.add(arrayOfAccesses.get(graphingPointLocations));// insert avg accesses for graphing
			loadFactors.add(arrayOfLoadFactors.get(graphingPointLocations));// insert load factor for graphing
		}

	}

	public static void runDeletionAnalysis() {

		accesses.add(0);// for the graph to start at 0
		loadFactors.add((float) 0.0);// for the graph to start at 0
		Random random = new Random();// Generate random keys
		int numberOfTablesToTest = 10;// for test multiple tables
		int accessTotalCountForAvg = 0;// Running sum of accesses, for averaging
		int graphingPointLocations;// to index to different load factors
		int count = 0; // count of records inserted
		float currentLoadFactor;// current load factor
		ArrayList<HashDictionary<Integer, Integer>> arrayOfHDl = new ArrayList<>();// array of different HashDictionaries
		List<Float> arrayOfLoadFactors = new ArrayList<>();// to store different size load factors
		List<Integer> arrayOfAccesses = new ArrayList<>();
		List<Integer> arrayOfKeys = new ArrayList<>(); // to store average of accesses

		// Fill array of HD for multiple tables
		for (int i = 0; i < numberOfTablesToTest; i++) {
			arrayOfHDl.add(new HashDictionary<Integer, Integer>());
		}

		// loop through different tables and insert records and count all the accesses performed
		for (int j = 0; j < HashDictionary.getMaxSize(); j++) {
			accessTotalCountForAvg = 0;
			for (int i = 0; i < numberOfTablesToTest; i++) {

				int newRandomKey = random.nextInt((20000000 - 0) + 1);
				int newRandomRecord = random.nextInt((20000000 - 0) + 1);
				arrayOfHDl.get(i).insert(newRandomKey, newRandomRecord);
				arrayOfKeys.add(newRandomKey);
			}
			count++;// record count + 1
		}

		// loop through and delete at the load factors that are plotted and get count of accesses
		for (int i = 0; i < numberOfTablesToTest; i++) {
			Integer findKey = arrayOfKeys.get((int) (HashDictionary.getMaxSize() * (i + 1 * .1)));
			arrayOfHDl.get(i).remove(findKey);
			accessTotalCountForAvg += HashTable.getDeletionCount();
		}

		int averageAccesses = accessTotalCountForAvg / numberOfTablesToTest;// get avg access
		currentLoadFactor = (float) count / HashDictionary.getMaxSize();// get current load factor
		arrayOfAccesses.add(averageAccesses);// insert results into array
		arrayOfLoadFactors.add(currentLoadFactor);// insert results into array
		for (int i = 1; i < numberOfTablesToTest; i++) {
			graphingPointLocations = (int) (HashDictionary.getMaxSize() * (i * .1));// get graphing locations points
			accesses.add(arrayOfAccesses.get(graphingPointLocations));// insert avg accesses for graphing
			loadFactors.add(arrayOfLoadFactors.get(graphingPointLocations));// insert load factor for graphing
		}

	}

	public static List<Integer> getAccesses() {
		return accesses;
	}

	public static List<Float> getLoadFactors() {
		return loadFactors;
	}

}
