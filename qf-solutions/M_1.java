import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class M_1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numberOfTestCases = sc.nextInt();
        for (int currentTestCase = 0; currentTestCase < numberOfTestCases; currentTestCase++) {
            int maxDifficulty = 0;
            int numberOfDays = sc.nextInt();
            int[] difficulties = new int[numberOfDays];
            for (int currentDayIndex = 0; currentDayIndex < numberOfDays; currentDayIndex++) {
                int newDifficulty = sc.nextInt();
                difficulties[currentDayIndex] = newDifficulty;
                maxDifficulty = Math.max(newDifficulty, maxDifficulty);
            }
            System.out.println(countCombinations(difficulties));
        }
    }
    private static int countCombinations(int[] difficulties) {
        int n = difficulties.length;
        int numberOfCombinations = 0;
        for (int j = n - 2; j > 0; j--) {
            Map<Integer, Integer> complexities = new HashMap<>();
            for (int k = j + 1; k < n; k++) {
                if (complexities.containsKey(difficulties[k])) {
                    complexities.put(difficulties[k], complexities.get(difficulties[k]) + 1);
                } else {
                    complexities.put(difficulties[k], 1);
                }
            }
            for (int i = 0; i < j; i++) {
                numberOfCombinations += complexities.getOrDefault(2 * difficulties[j] - difficulties[i], 0);
            }
        }
        return numberOfCombinations;
    }
}