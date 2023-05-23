import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Reverse {
    public static void main(String[] args) throws IOException {
        MyScanner firstScanner = new MyScanner(new BufferedReader(new InputStreamReader(System.in)));
        List<int[]> mainArray = new ArrayList<>();
        while (firstScanner.hasNextLine()) {
            String curr = firstScanner.readLine();
            int[] tempArr = new int[10];
            int i = 0;
            String[] split = curr.split("\\s+");
            for (String s: split) {
                if (!s.isEmpty()) {
                    tempArr[i] = Integer.parseInt(s);
                    i++;
                    if (i == tempArr.length - 1) {
                        tempArr = Arrays.copyOf(tempArr, tempArr.length * 2);
                    }
                }
            }
            tempArr[tempArr.length - 1] = i - 1;
            mainArray.add(tempArr);
        }

        for (int j = mainArray.size() - 1; j >= 0; j--) {
            for (int k = mainArray.get(j)[mainArray.get(j).length - 1]; k >= 0; k--) {
                System.out.print(mainArray.get(j)[k] + " ");
            }
            System.out.print(System.lineSeparator());
        }
    }
}
