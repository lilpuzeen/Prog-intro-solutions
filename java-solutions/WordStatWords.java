import java.io.*;
import java.util.*;


public class WordStatWords {

    public static void main(String[] args) {
        Map<String, Integer> map = new TreeMap<>();
        try {
            MyScanner reader = new MyScanner(new BufferedReader(
                    new InputStreamReader(new FileInputStream(args[0]), "utf8"))
            );
            try {
                StringBuilder sb = new StringBuilder();
                while (reader.hasNextLine()) {
                    char[] readString = reader.readLine().toCharArray();
                    for (int i = 0; i < readString.length; i++) {
                        int ch = Character.toLowerCase(readString[i]);
                        if (Character.isLetter((char) ch)
                                || "'".contains((char) ch + "")
                                || Character.getType(ch) == Character.DASH_PUNCTUATION) {
                            sb.append((char) ch);
                        } else if (!sb.isEmpty()) {
                            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
                            sb = new StringBuilder();
                        }
                        if (i == readString.length - 1 && !sb.isEmpty()) {
                            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
                            sb = new StringBuilder();
                        }
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(args[1]), "utf8")
            );
            try {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    writer.append(entry.getKey()).append(" ").append(String.valueOf(entry.getValue())).append("\n");
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
