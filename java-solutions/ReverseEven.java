import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReverseEven {
    public static void main(String[] args) {
        Scanner firstScanner = new Scanner(System.in);
        List<int[]> mainArray = new ArrayList<>();
        while (firstScanner.hasNextLine()) {
            String curr = firstScanner.nextLine();
            int[] tempArr = new int[100];
            int lineLength = 0;
            int i = 0;
            Scanner secondScanner = new Scanner(curr);
            while (secondScanner.hasNextInt()) {
                int check = secondScanner.nextInt();
                if (check % 2 == 0) {
                    tempArr[i] = check;
                    i++;
                    lineLength++;
                }
                if (lineLength == tempArr.length - 1) {
                    tempArr = Arrays.copyOf(tempArr, tempArr.length * 2);
                }
            }

            tempArr[tempArr.length - 1] = lineLength;
            mainArray.add(tempArr);
        }

        for (int i = mainArray.size() - 1; i >= 0; i--) {
            int[] secondaryArray = new int[mainArray.get(i)[mainArray.get(i).length - 1]];
            System.arraycopy(mainArray.get(i), 0, secondaryArray, 0, mainArray.get(i)[mainArray.get(i).length - 1]);
            for (int k = secondaryArray.length - 1; k >= 0; k--) {
                System.out.print(secondaryArray[k] + " ");
            }
            System.out.print(System.lineSeparator());
        }
    }
}
