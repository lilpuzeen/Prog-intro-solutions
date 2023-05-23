import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseOct {
    public static void main(String[] args) throws IOException {
        MyScanner firstScanner = new MyScanner(new BufferedReader(new InputStreamReader(System.in)));
        List<String[]> mainArray = new ArrayList<>();
        while (firstScanner.hasNextLine()) {
            String curr = firstScanner.readLine();
            String[] tempArr = new String[5];
            int lineLength = 0;
            int i = 0;
            while (firstScanner.hasNextOct()) {
                tempArr[i] = firstScanner.nextOct();
                i++;
                lineLength++;
                if (lineLength == tempArr.length - 1) {
                    tempArr = Arrays.copyOf(tempArr, tempArr.length * 2);
                }
            }

            tempArr[tempArr.length - 1] = String.valueOf(lineLength);
            mainArray.add(tempArr);
        }

        for (int j = mainArray.size() - 1; j >= 0; j--) {
            int currentArrayLength = Integer.parseInt(mainArray.get(j)[mainArray.get(j).length - 1]);
            String[] secondaryArray = new String[currentArrayLength];
            System.arraycopy(mainArray.get(j), 0, secondaryArray, 0, currentArrayLength);
            for (int k = secondaryArray.length - 1; k >= 0; k--) {
                System.out.print(secondaryArray[k] + " ");
            }
            System.out.print(System.lineSeparator());
        }
    }
}
