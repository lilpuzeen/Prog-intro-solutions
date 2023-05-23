import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class H_7 {
    private static int maxTransactionSize;
    private static Map<Integer, Integer> indexesOfLastQueriesInTransactions = new HashMap<>();
    private static int totalQueriesNumber = 0;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Integer> resultsHistory = new HashMap<>();
        int numberOfTransactions = sc.nextInt();
        int[] transactionsSizes = new int[numberOfTransactions];
        int[] accumulatedTransactionIndexes = new int[numberOfTransactions];
        for (int i = 0; i < numberOfTransactions; i++) {
            int currentTransactionSize = sc.nextInt();
            transactionsSizes[i] = currentTransactionSize;
            if (i == 0) {
                accumulatedTransactionIndexes[0] = currentTransactionSize - 1;
            } else {
                accumulatedTransactionIndexes[i] = transactionsSizes[i] + accumulatedTransactionIndexes[i-1];
            }
            maxTransactionSize = Math.max(maxTransactionSize, currentTransactionSize);
        }
        totalQueriesNumber = accumulatedTransactionIndexes[numberOfTransactions - 1] + 1;
        int[] queriesToTransaction = new int[totalQueriesNumber];
        int lastReadIndex = 0;
        for (int currentTransactionIndex = 0; currentTransactionIndex < numberOfTransactions; currentTransactionIndex++) {
            for (int queryIndex = lastReadIndex; queryIndex < accumulatedTransactionIndexes[currentTransactionIndex] + 1; queryIndex++) {
                queriesToTransaction[queryIndex] = currentTransactionIndex;
            }
            lastReadIndex = accumulatedTransactionIndexes[currentTransactionIndex] + 1;
        }
        int q = sc.nextInt();
        int[] batchSizeCandidates = new int[q];
        for (int i = 0; i < q; i++) {
            batchSizeCandidates[i] = sc.nextInt();
        }
        for (int item : batchSizeCandidates) {
            if (resultsHistory.containsKey(item)) {
                int savedResult = resultsHistory.get(item);
                if (savedResult == -1) {
                    System.out.println("Impossible");
                } else {
                    System.out.println(savedResult);
                }
            } else {
                if (maxTransactionSize > item) {
                    resultsHistory.put(item, -1);
                    System.out.println("Impossible");
                } else {
                    int result = getNumberOfBatches(item, accumulatedTransactionIndexes, queriesToTransaction);
                    resultsHistory.put(item, result);
                    System.out.println(result);
                }
            }
        }
    }
    private static int getNumberOfBatches(int proposedBatchSize, int[] accumulatedTransactionIndexes, int[] queriesToTransaction) {
        int lastReadQueryIndex = -1;
        int count = 1;
        while (lastReadQueryIndex + proposedBatchSize + 1 <= totalQueriesNumber - 1) {
            int w = queriesToTransaction[lastReadQueryIndex + proposedBatchSize + 1] - 1;
            lastReadQueryIndex = accumulatedTransactionIndexes[w];
            count++;
        }
        return count;
    }
}